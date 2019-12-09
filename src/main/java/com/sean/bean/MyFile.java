package com.sean.bean;

/**
 * 个人文件
 */
public class MyFile {
    private String id;
    //文件存储路径
    private String path;
    //上传人
    private String uid;
    //文件类型代码
    private String filetype;
    //图片文件缩略图
    private String thumbimage;
    //文件原名
    private String filename;
    //文件大小
    private String size;

    public MyFile() {
    }

    public MyFile(String id, String path, String uid, String filetype,String thumbimage,String size, String filename) {
        this.id = id;
        this.path = path;
        this.uid = uid;
        this.filetype = filetype;
        this.thumbimage = thumbimage;
        this.size = size;
        this.filename = filename;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path == null ? null : path.trim();
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid == null ? null : uid.trim();
    }

    public String getFiletype() {
        return filetype;
    }

    public void setFiletype(String filetype) {
        this.filetype = filetype;
    }

    public String getThumbimage() {
        return thumbimage;
    }

    public void setThumbimage(String thumbimage) { this.thumbimage = thumbimage == null ? null : thumbimage.trim(); }

    public String getFilename() { return filename; }

    public void setFilename(String filename) { this.filename = filename == null ? null : filename.trim(); }

    public String getSize() { return size; }

    public void setSize(String size) { this.size = size == null ? null : size.trim(); }
}