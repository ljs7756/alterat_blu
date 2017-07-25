/**
 * CommonPortType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.cha.transcoder.nps.soapinterface;

public interface CommonPortType extends java.rmi.Remote {

    /**
     * GetIngestSchedule
     */
    public java.lang.String getIngestSchedule(java.lang.String request) throws java.rmi.RemoteException;

    /**
     * SetQueuedIngestScheduleItem
     */
    public java.lang.String setQueuedIngestScheduleItem(java.lang.String request) throws java.rmi.RemoteException;

    /**
     * login
     */
    public java.lang.String login(java.lang.String request) throws java.rmi.RemoteException;

    /**
     * insertMetadata
     */
    public java.lang.String insertMetadata(java.lang.String request) throws java.rmi.RemoteException;

    /**
     * getTaskJob
     */
    public java.lang.String getTaskJob(java.lang.String request) throws java.rmi.RemoteException;

    /**
     * getMetadata
     */
    public java.lang.String getMetadata(java.lang.String request) throws java.rmi.RemoteException;

    /**
     * downloadInfoContent
     */
    public java.lang.String downloadInfoContent(java.lang.String request) throws java.rmi.RemoteException;

    /**
     * getContentList
     */
    public java.lang.String getContentList(java.lang.String request) throws java.rmi.RemoteException;

    /**
     * getContentInfo
     */
    public java.lang.String getContentInfo(java.lang.String request) throws java.rmi.RemoteException;

    /**
     * getRundownlist
     */
    public java.lang.String getRundownlist(java.lang.String request) throws java.rmi.RemoteException;

    /**
     * getRundowndownload
     */
    public java.lang.String getRundowndownload(java.lang.String request) throws java.rmi.RemoteException;

    /**
     * CueSheet
     */
    public java.lang.String cueSheet(java.lang.String request) throws java.rmi.RemoteException;

    /**
     * getCueSheetList
     */
    public java.lang.String getCueSheetList(java.lang.String request) throws java.rmi.RemoteException;

    /**
     * getCueSheetItemList
     */
    public java.lang.String getCueSheetItemList(java.lang.String request) throws java.rmi.RemoteException;

    /**
     * insertCueSheet
     */
    public java.lang.String insertCueSheet(java.lang.String request) throws java.rmi.RemoteException;

    /**
     * updateStatus
     */
    public java.lang.String updateStatus(java.lang.String request) throws java.rmi.RemoteException;

    /**
     * getCode
     */
    public java.lang.String getCode(java.lang.String request) throws java.rmi.RemoteException;

    /**
     * getProgramList
     */
    public java.lang.String getProgramList(java.lang.String request) throws java.rmi.RemoteException;

    /**
     * getIngestScheduleAll
     */
    public java.lang.String getIngestScheduleAll(java.lang.String request) throws java.rmi.RemoteException;

    /**
     * insertIngestSchedule
     */
    public java.lang.String insertIngestSchedule(java.lang.String request) throws java.rmi.RemoteException;

    /**
     * getIngestProgramList
     */
    public java.lang.String getIngestProgramList(java.lang.String request) throws java.rmi.RemoteException;

    /**
     * getIngestEpisodeList
     */
    public java.lang.String getIngestEpisodeList(java.lang.String request) throws java.rmi.RemoteException;

    /**
     * RequestNPStoCMS
     */
    public java.lang.String requestNPStoCMS(java.lang.String request) throws java.rmi.RemoteException;

    /**
     * getCheckTime
     */
    public java.lang.String getCheckTime(java.lang.String request) throws java.rmi.RemoteException;

    /**
     * getGhStorage
     */
    public java.lang.String getGhStorage(java.lang.String request) throws java.rmi.RemoteException;

    /**
     * ListKeyframeNPS
     */
    public java.lang.String listKeyframeNPS(java.lang.String request) throws java.rmi.RemoteException;

    /**
     * GetPgmInfoNPS
     */
    public java.lang.String getPgmInfoNPS(java.lang.String request) throws java.rmi.RemoteException;

    /**
     * getContentInfoByMaterialID
     */
    public java.lang.String getContentInfoByMaterialID(java.lang.String request) throws java.rmi.RemoteException;

    /**
     * getLocationByMaterialID
     */
    public java.lang.String getLocationByMaterialID(java.lang.String request) throws java.rmi.RemoteException;

    /**
     * getProgramForDAS
     */
    public java.lang.String getProgramForDAS(java.lang.String request) throws java.rmi.RemoteException;

    /**
     * genNewMaterialID
     */
    public java.lang.String genNewMaterialID(java.lang.String request) throws java.rmi.RemoteException;

    /**
     * UserListSync
     */
    public java.lang.String userListSync(java.lang.String request) throws java.rmi.RemoteException;

    /**
     * updateArchiveStatus
     */
    public java.lang.String updateArchiveStatus(java.lang.String request) throws java.rmi.RemoteException;

    /**
     * registiCMS
     */
    public java.lang.String registiCMS(java.lang.String request) throws java.rmi.RemoteException;

    /**
     * getSysMeta
     */
    public java.lang.String getSysMeta(java.lang.String request) throws java.rmi.RemoteException;

    /**
     * getSearchMetasysinfo
     */
    public java.lang.String getSearchMetasysinfo(java.lang.String request) throws java.rmi.RemoteException;

    /**
     * getRequestCaptionInfo
     */
    public java.lang.String getRequestCaptionInfo(java.lang.String request) throws java.rmi.RemoteException;
}
