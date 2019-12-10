package com.sean.dao;

import com.sean.bean.CommentLike;

public interface CommentLikeMapper {
    int deleteByPrimaryKey(String id);

    int insert(CommentLike record);

    int insertSelective(CommentLike record);

    CommentLike selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(CommentLike record);

    int updateByPrimaryKey(CommentLike record);
}