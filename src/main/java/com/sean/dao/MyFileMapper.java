package com.sean.dao;

import com.sean.bean.MyFile;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MyFileMapper {
    int deleteByPrimaryKey(String id);

    int deleteFileByPath(String path);

    int insert(MyFile record);

    int insertSelective(MyFile record);

    MyFile selectByPrimaryKey(String id);

    String selectFidByPath(String path);

    int updateByPrimaryKeySelective(MyFile record);

    int updateByPrimaryKey(MyFile record);

    List<MyFile> selectMyfilelist(String uid, List<String> types);

    /**
     * 模糊搜索
     * @param uid
     * @param types
     * @param keywords
     * @return
     */
    List<MyFile> fuzzySearchMyfilelist(String uid, List<String> types, String keywords);

    List<MyFile> selectFileAll();
}