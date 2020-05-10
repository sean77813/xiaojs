package com.sean.bean;

import lombok.ToString;

@ToString
public class MessageFlag {
    private String pkId;

    private String mid;

    private String uid;

    private Integer status;

    private Integer star;

    public String getPkId() {
        return pkId;
    }

    public void setPkId(String pkId) {
        this.pkId = pkId == null ? null : pkId.trim();
    }

    public String getMid() {
        return mid;
    }

    public void setMid(String mid) {
        this.mid = mid == null ? null : mid.trim();
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid == null ? null : uid.trim();
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getStar() {
        return star;
    }

    public void setStar(Integer star) {
        this.star = star;
    }

    public MessageFlag() {
    }

    public MessageFlag(String pkId, String mid, String uid, Integer status, Integer star) {
        this.pkId = pkId;
        this.mid = mid;
        this.uid = uid;
        this.status = status;
        this.star = star;
    }
}