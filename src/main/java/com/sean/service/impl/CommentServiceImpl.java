package com.sean.service.impl;

import com.sean.bean.Comment;
import com.sean.bean.User;
import com.sean.dao.CommentMapper;
import com.sean.dao.UserMapper;
import com.sean.service.CommentService;
import com.sean.utils.SeanTools;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentMapper commentMapper;
    @Autowired
    private UserMapper userMapper;

    @Override
    public boolean addComment(String fid, String msg, boolean isIncognito) {
        System.out.println("fid:"+fid);
        System.out.println("msg:"+msg);
        System.out.println("isIncognito:"+isIncognito);
        if ("".equals(fid) || null == fid )
            return false;
        if ("".equals(msg) || null == msg )
            return false;
        int flag = 0;
        try {
            Comment comment = new Comment();
            comment.setId(SeanTools.getUUID32());
            comment.setFid(fid);
            comment.setMsg(msg);
            comment.setLikes(0);
            comment.setTime(new Date());
            if (!isIncognito){
                Subject subject = SecurityUtils.getSubject();
                User user = (User)subject.getPrincipal();
                comment.setUid(user.getUid());
            }
            flag = commentMapper.insertSelective(comment);
            if(flag>0){
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return false;
    }

    @Override
    public List<Comment> getComment(String fileid) {
        if("".equals(fileid) || null == fileid)
            return null;
        List<Comment> comments = new ArrayList<>();
        try {
            comments = commentMapper.selectCommentsByFid(fileid);
            for (Comment c:comments){
               String username =  userMapper.selectUserNameByUid(c.getUid());
                c.setUsername(username);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return comments;
    }
}
