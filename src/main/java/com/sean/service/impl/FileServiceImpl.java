package com.sean.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.sean.bean.MyFile;
import com.sean.bean.MyFileInfo;
import com.sean.bean.MyfileProperty;
import com.sean.bean.OperationLog;
import com.sean.bean.PublicPic;
import com.sean.bean.User;
import com.sean.dao.MyFileMapper;
import com.sean.dao.MyfilePropertyMapper;
import com.sean.dao.OperationLogMapper;
import com.sean.dao.PublicPicMapper;
import com.sean.service.FileService;
import com.sean.service.OperationLogService;
import com.sean.utils.Constants;
import com.sean.utils.SeanTools;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class FileServiceImpl implements FileService {

    @Autowired
    private MyFileMapper fileMapper;
    @Autowired
    private MyfilePropertyMapper propMapper;
    @Autowired
    private PublicPicMapper publicMapper;
    @Autowired
    private OperationLogService logService;
    @Autowired
    private OperationLogMapper logMapper;

    @Override
    public boolean saveFile(MyFile file) {
        int flag = 0;
        try {
            flag = fileMapper.insertSelective(file);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (flag > 0) {
            logService.recordLog(file.getId(),1); //添加[删除日志]
            return true;
        }
        return false;
    }

    @Override
    public List<MyFile> queryMyFiles(String uid, String filetype, String keywords) {
        List<MyFile> files = new ArrayList<>();
        ArrayList<String> list = new ArrayList<>();
        int i = 0;
        if (filetype != null && filetype.length()>0 ) {
            if ("img".equalsIgnoreCase(filetype)) {
                Constants.imgFile[] s = Constants.imgFile.values();
                for (Constants.imgFile c : s) {
                    list.add(c.getCode());
                }
            } else if ("doc".equalsIgnoreCase(filetype)) {
                Constants.docFile[] s = Constants.docFile.values();
                for (Constants.docFile c : s) {
                    list.add(c.getCode());
                }
            } else {
                list.add("999");
            }
        }
        try {
            if( null == keywords || keywords.length()<=0 ){
                files = fileMapper.selectMyfilelist(uid, list);
            }else{
                files = fileMapper. fuzzySearchMyfilelist(uid, list,keywords.trim());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return files;
    }

    @Override
    public String queryFileIdByPath(String path) {
        if("".equals(path) || null == path)
            return "";
        String fid = "";
        try {
            fid =  fileMapper.selectFidByPath(path);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return fid;
    }

    @Override
    public List<MyFile> queryPPics(String keywords) {
        List<MyFile> ppicList = new ArrayList<>();
        try {
            List<String> fidList = new ArrayList<>();
            if( null == keywords || keywords.length()<=0 ){
                fidList = publicMapper.selectFidAll();
            }else{
                fidList = publicMapper.fuzzySearchFid(keywords);
            }
            for(int i = 0; i< fidList.size(); i++){
                if("".equals(fidList.get(i)))
                    continue;
                MyFile ppic = fileMapper.selectByPrimaryKey(fidList.get(i));
                ppicList.add(ppic);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ppicList;
    }

    @Transactional
    @Override
    public boolean deleteFile(String path) {
        int flag = 0;
        try {
            String fid = fileMapper.selectFidByPath(path);
            if(null==fid || "".equals(fid))
                return false;
            flag = fileMapper.deleteFileByPath(path);
            if (flag > 0) {
                //删除分享
                String publicid =  publicMapper.selectIdByFid(fid);
                System.out.println("publicid=>"+publicid);
                if(null != publicid){
                    publicMapper.deleteByPrimaryKey(publicid);
                }
                //删除扩展属性
                String propid = propMapper.selectIdByFid(fid);
                System.out.println("propid=>"+propid);
                if(null != propid){
                    propMapper.deleteByPrimaryKey(propid);
                }
                logService.recordLog(fid,-1); //添加[删除日志]
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return false;
    }

    @Override
    public String addExtProp(String fileid, String newprops) {
        String resultCode = "";
        try {
            int flag = 0;
            if( "".equals(fileid) || "".equals(newprops) )
                //参数为空
                return "400";
            MyFile myFile = new MyFile();
            myFile = fileMapper.selectByPrimaryKey(fileid);
            if( null == myFile )
                return "404";
            MyfileProperty myfileProp = propMapper.selectByPrimaryKey(fileid);
            if(myfileProp==null){
                myfileProp = new MyfileProperty();
                myfileProp.setId(SeanTools.getUUID32());
                myfileProp.setFid(fileid);
                myfileProp.setProperty(newprops);
                flag = propMapper.insert(myfileProp);
            }else{
                myfileProp.setProperty(newprops);
                flag = propMapper.updateByPrimaryKey(myfileProp);
            }
            if(flag>0){
                resultCode =  "200";
            }else{
                resultCode =  "201";
            }
        } catch (Exception e) {
            e.printStackTrace();
            resultCode = "500";
        }
        return resultCode;
    }

    /**
     * @Description: 原添加标签方法，现添加了标签等级
     */
    public String addLabel_Description(String fileid, String labels) {
        String resultCode = "";
        try {
            int flag = 0;
            if( "".equals(fileid) || "".equals(labels) )
                //参数为空
                return "400";
            MyFile myFile = new MyFile();
            myFile = fileMapper.selectByPrimaryKey(fileid);
            if( null == myFile )
                return "404";
            MyfileProperty myfileProp = propMapper.selectByFid(fileid);
            if(myfileProp==null){
                myfileProp = new MyfileProperty();
                myfileProp.setId(SeanTools.getUUID32());
                myfileProp.setFid(fileid);
                myfileProp.setLabel(labels);
                flag = propMapper.insert(myfileProp);
            }else{
                String lodlabel = myfileProp.getLabel();
                StringBuffer tag = new StringBuffer(lodlabel);
                tag.append(",");
                tag.append(labels);
                myfileProp.setLabel(tag.toString());
                flag = propMapper.updateByPrimaryKey(myfileProp);
            }
            if(flag>0){
                resultCode =  "200";
            }else{
                resultCode =  "201";
            }
        } catch (Exception e) {
            e.printStackTrace();
            resultCode = "500";
        }
        return resultCode;
    }

    @Override
    public String addLabel(String fileid, String labels,String level) {
        String resultCode = "";
        try {
            int flag = 0;
            if( "".equals(fileid) || "".equals(labels) )
                //参数为空
                return "400";
            MyFile myFile = new MyFile();
            myFile = fileMapper.selectByPrimaryKey(fileid);
            if( null == myFile )
                return "404";
            MyfileProperty myfileProp = propMapper.selectByFid(fileid);
            if(null==myfileProp){
                //创建标签
                Map<String, String> map = new HashMap<>();
                String label[] = labels.split(",");
                for(String l:label){
                    map.put(l,level);  //新标签转为Map
                }
                String newlabel = JSONObject.toJSONString(map); //标签Map转为JSON字符串
                myfileProp = new MyfileProperty();
                myfileProp.setId(SeanTools.getUUID32());
                myfileProp.setFid(fileid);
                myfileProp.setLabel(newlabel);
                flag = propMapper.insert(myfileProp);
            }else{
                //在原标签基础上添加
                String oldlabel = myfileProp.getLabel(); //原有标签
                Map<String, String> oldmap = new HashMap<>();
                if(!"".equals(oldlabel) && SeanTools.isJSONValid(oldlabel)){
                    oldmap = (Map)JSONObject.parse(oldlabel); //原有标签转为Map
                }
                Map<String, String> map = new HashMap<>();
                String label[] = labels.split(",");
                for(String l :label){
                    map.put(l ,level);  //新标签转为Map
                }
                oldmap.putAll(map); //标签Map合并
                String newlabel = JSONObject.toJSONString(oldmap); //标签Map转为JSON字符串
                myfileProp.setLabel(newlabel);
                flag = propMapper.updateByPrimaryKey(myfileProp);
            }
            if(flag>0){
                resultCode =  "200";
            }else{
                resultCode =  "201";
            }
        } catch (Exception e) {
            e.printStackTrace();
            resultCode = "500";
        }
        return resultCode;
    }

    /**
     * 分享图片
     * @param fileid
     * @return
     */
    @Override
    public String sharePicToPublic(String fileid) {
        String result = "000";
        try {
            if("".equals(fileid) || null == fileid)
                return "400";
            MyFile pic = new MyFile();
            pic = fileMapper.selectByPrimaryKey(fileid);
            if(null==pic)
                return "404";
            int count = publicMapper.isExistPicByFid(fileid);
            if(count>0)
                return "401";
            int flag = 0;
            Subject subject = SecurityUtils.getSubject();
            User user = (User)subject.getPrincipal();
            PublicPic ppic = new PublicPic();
            ppic.setId(SeanTools.getUUID32());
            ppic.setFid(fileid);
            ppic.setAuthor(user.getUsername());
            ppic.setReleasetime(new Date());
            flag = publicMapper.insert(ppic);
            if(flag>0){
                result =  "200";
            }
        } catch (Exception e) {
            result = "500";
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public PublicPic queryPicPropByFid(String fileid) {
        PublicPic ppic = new PublicPic();
        try {
            ppic = publicMapper.selectPpicByFid(fileid);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ppic;
    }

    @Override
    public String queryLabelByFid(String fileid) {
        String label = "";
        try {
            label = propMapper.selectLabelsByFid(fileid);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return label;
    }


    @Override
    public MyFileInfo getFileInfo(String fileId) {
        if("".equals(fileId) || null == fileId)
            return null;
        MyFile myFile = fileMapper.selectByPrimaryKey(fileId);
        if (null == myFile)
            return null;
        MyFileInfo fileInfo = new MyFileInfo();
        fileInfo.setFid(fileId);
        //文件名称
        if (null != myFile.getFilename() && !"".equals(myFile.getFilename())){
            fileInfo.setFileName(myFile.getFilename());
        }
        //文件上传时间
        OperationLog filelog = logService.queryLog(fileId,1,false).get(0);
        if(null != filelog && null != filelog.getTime()){
            fileInfo.setUploadTime(filelog.getTime());
        }
        //文件大小
        if(null != myFile.getSize() && !"".equals(myFile.getSize())){
            fileInfo.setFileSize(myFile.getSize());
        }
        return fileInfo;
    }
	
    @Override
    public MyFile selectFileByFileId(String fileId) {
        if("".equals(fileId) || null == fileId)
            return null;
        MyFile myFile = new MyFile();
        try {
            myFile = fileMapper.selectByPrimaryKey(fileId);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return myFile;
    }
}
