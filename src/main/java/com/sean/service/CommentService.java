package com.sean.service;

import com.sean.bean.Comment;

import java.util.List;

/**
 * 评论服务
 */
public interface CommentService {

    /**
     *  添加评论
     * @param fid 文件id
     * @param msg  评论内容
     * @param isIncognito 是否匿名
     * @return
     */
    boolean addComment(String fid,String msg,boolean isIncognito);

    /**
     *  获取文件评论
     * @param fileid
     * @return
     */
    List<Comment> getComment(String fileid);
}
