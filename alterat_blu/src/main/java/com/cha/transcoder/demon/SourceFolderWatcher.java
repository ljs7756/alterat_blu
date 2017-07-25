package com.cha.transcoder.demon;

import java.nio.file.*;
import static java.nio.file.StandardWatchEventKinds.*;
import static java.nio.file.LinkOption.*;
import java.nio.file.attribute.*;
import java.io.*;
import java.util.*;

import org.apache.log4j.Logger;

public class SourceFolderWatcher {

	private Logger log = Logger.getLogger(SourceFolderWatcher.class);
	private final WatchService watcherService;
	private final Map<WatchKey, Path> keys;
	private final boolean recursive;
	private boolean trace = true;

	public static Queue listOfEvent = new LinkedList();

	@SuppressWarnings("unchecked")
	static <T> WatchEvent<T> cast(WatchEvent<?> event) {
		return (WatchEvent<T>) event;
	}

	/**
	 * Register the given directory with the WatchService
	 */
	private void register(Path dir) throws IOException {
		WatchKey key = dir.register(watcherService, ENTRY_CREATE, ENTRY_DELETE, ENTRY_MODIFY);

		log.debug("Register watchkey. (ENTRY_CREATE, ENTRY_DELETE, ENTRY_MODIFY)");
		if (trace) {
			Path prev = keys.get(key);
			if (prev == null) {
				System.out.format("register: %s\n", dir);
			} else {
				if (!dir.equals(prev)) {
					System.out.format("update: %s -> %s\n", prev, dir);
				}
			}
		}
		keys.put(key, dir);
	}

	/**
	 * Register the given directory, and all its sub-directories, with the
	 * WatchService.
	 */
	private void registerAll(final Path start) throws IOException {
		log.debug("register directory and sub-directories.");
		Files.walkFileTree(start, new SimpleFileVisitor<Path>() {
			@Override
			public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
				register(dir);
				return FileVisitResult.CONTINUE;
			}
		});
	}

	/**
	 * Creates a WatchService and registers the given directory
	 */
	SourceFolderWatcher(Path dir, boolean recursive) throws IOException {
		this.watcherService = FileSystems.getDefault().newWatchService();
		this.keys = new HashMap<WatchKey, Path>();
		this.recursive = recursive;

		log.debug("Scanning " + dir + " ..., rescursive=" + recursive);
		if (recursive) {
			registerAll(dir);
		} else {
			register(dir);
		}
		log.debug("Down");

		// enable trace after initial registration
		this.trace = true;
	}

	/**
	 * Process all events for keys queued to the watcher
	 */
	void processEvents() {
		for (;;) {

			// wait for key to be signalled
			WatchKey key;
			try {
				log.debug("wait for key to be signalled.");
				key = watcherService.take();
			} catch (InterruptedException x) {
				log.error(x.getMessage());
				return;
			}

			Path dir = keys.get(key);
			if (dir == null) {
				log.debug("WatchKey not recognized!!");
				continue;
			}

			int count = 0;
			for (WatchEvent<?> event : key.pollEvents()) {

				WatchEvent.Kind kind = event.kind();

				// TBD - provide example of how OVERFLOW event is handled
				if (kind == OVERFLOW) {
					log.debug("This kind is OVERFLOW.");
					continue;
				}

				// Context for directory entry event is the file name of
				// entry
				WatchEvent<Path> ev = cast(event);
				Path name = ev.context();
				Path child = dir.resolve(name);

				// print out event
				// System.out.format("%s: %s\n", event.kind().name(),
				// child);
				log.debug("kind=" + event.kind().name() + ", child=" + child);

				listOfEvent.offer(child);

				// if directory is created, and watching recursively, then
				// register it and its sub-directories
				if (recursive && (kind == ENTRY_CREATE)) {
					try {
						if (Files.isDirectory(child, NOFOLLOW_LINKS)) {
							registerAll(child);
						}
					} catch (IOException x) {
						// ignore to keep sample readbale
						log.error(x.getMessage());
					}
				}
			}

			// reset key and remove from set if directory no longer
			// accessible
			boolean valid = key.reset();
			log.debug("Cp, valid=" + valid);

			if (!valid) {
				keys.remove(key);

				// all directories are inaccessible
				if (keys.isEmpty()) {
					log.debug("keys is empty.");
					break;
				}
			}
		}
	}

	// Queue를 채크해서 DB에 넣어주는 부분
	public static void registerJobs() {
		while (listOfEvent.peek() != null) {
			Path child = (Path) listOfEvent.poll();
			TcJobMaker.getInstance().receiveEvent(child);
		}
	}

	// Watch Service를 중지한다.
	public void CloseWatchService() {
		try {
			log.debug("Close WatchService");
			watcherService.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	static void usage() {
		System.err.println("usage: java WatchDir [-r] dir");
		System.exit(-1);
	}

	public static void main(String[] args) throws IOException {
		// parse arguments
		if (args.length == 0 || args.length > 2)
			usage();
		boolean recursive = false;
		int dirArg = 0;
		if (args[0].equals("-r")) {
			if (args.length < 2)
				usage();
			recursive = true;
			dirArg++;
		}

		// register directory and process its events
		Path dir = Paths.get(args[dirArg]);
		new SourceFolderWatcher(dir, recursive).processEvents();
	}

}
