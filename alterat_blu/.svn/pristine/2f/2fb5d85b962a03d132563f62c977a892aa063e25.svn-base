package com.cha.transcoder.common;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.cha.transcoder.monitor.control.MonitorController;

public class AlteratConfigLoader {

	private Logger log = Logger.getLogger(MonitorController.class);

	private static AlteratConfigLoader instance;
	private AlteratConfig config;
	private AlteratConfigForSOAP configSOAP;

	private AlteratConfigLoader() {
		ApplicationContext context = new ClassPathXmlApplicationContext("/config/spring/context-alterat.xml");
		config = (AlteratConfig) context.getBean("alteratConfig");
		configSOAP = (AlteratConfigForSOAP) context.getBean("alteratConfigForSOAP");
	}

	public static AlteratConfigLoader getInstance() {
		if (instance == null) {
			instance = new AlteratConfigLoader();
		}
		return instance;
	}

	public AlteratConfig getConfig() {
		log.debug(config);
		return config;
	}

	public AlteratConfigForSOAP getConfigSOAP() {
		return configSOAP;
	}

}
