package com.sean.service;

import com.sean.bean.MyFile;
import com.sean.bean.MyFileInfo;
import com.sean.bean.MyfileProperty;
import com.sean.bean.PublicPic;

import java.util.List;

public interface FileService {

    /**
     * 上传新增文件
     * @param file
     * @return
     */
    boolean saveFile(MyFile file);

    /**
     * 查询个人类型文件
     * @param uid
     * @param filetype
     * @return
     */
    List<MyFile> queryMyFiles(String uid, String filetype);

    /**
     * 查询文件ID根据路径
     * @param path
     * @return
     */
    String queryFileIdByPath(String path);

    /**
     * 查询所有分享文件
     * @return
     */
    List<MyFile> queryPPics();

    /**
     * 删除文件
     * @param path
     * @return
     */
    boolean deleteFile(String path);

    /**
     * 添加文件扩展属性
     * @param fileid
     * @param props
     * @return
     */
    String addExtProp(String fileid,String props);

    /**
     * 添加标签
     * @param fileid
     * @param labels
     * @return
     */
    String addLabel(String fileid,String labels,String level);

    /**
     * 分享个人文件到分享空间
     * @param fileid
     * @return
     */
    String sharePicToPublic(String fileid);

    /**
     * 查询分享文件属性根据文件id
     * @param fileid
     * @return
     */
    PublicPic queryPicPropByFid(String fileid);

    /**
     * 查询分享文件标签
     * @param fileid
     * @return
     */
    String queryLabelByFid(String fileid);

    /**
     * 获取文件基础信息
     * @param fileId
     * @return
     */
    MyFileInfo getFileInfo(String fileId);
	
	/**
     * 查询文件根据id
     * @param fileId
     * @return
     */
    MyFile selectFileByFileId(String fileId);
	
	
}
