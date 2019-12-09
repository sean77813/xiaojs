package com.sean.bean;

import java.util.Date;

/**
 * 文件基础信息展示
 */
public class MyFileInfo {

    private String fid;

    private String fileName;

    private Date uploadTime;

    private Date updateTime;

    private String fileSize;

    private int imgHight;

    private int imgWidth;

    public String getFid() {
        return fid;
    }

    public void setFid(String fid) {
        this.fid = fid;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public Date getUploadTime() {
        return uploadTime;
    }

    public void setUploadTime(Date uploadTime) {
        this.uploadTime = uploadTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getFileSize() {
        return fileSize;
    }

    public void setFileSize(String fileSize) {
        this.fileSize = fileSize;
    }

    public int getImgHight() {
        return imgHight;
    }

    public void setImgHight(int imgHight) {
        this.imgHight = imgHight;
    }

    public int getImgWidth() {
        return imgWidth;
    }

    public void setImgWidth(int imgWidth) {
        this.imgWidth = imgWidth;
    }
}
