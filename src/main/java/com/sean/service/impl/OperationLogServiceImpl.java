package com.sean.service.impl;

import com.sean.bean.MyFile;
import com.sean.bean.OperationLog;
import com.sean.bean.User;
import com.sean.dao.MyFileMapper;
import com.sean.dao.OperationLogMapper;
import com.sean.service.OperationLogService;
import com.sean.utils.SeanTools;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class OperationLogServiceImpl implements OperationLogService {

    @Autowired
    private OperationLogMapper logMapper;
    @Autowired
    private MyFileMapper myFileMapper;

    @Override
    public boolean recordLog(String fid, int type) {
        if( null==fid || "".equals(fid) )
            return false;
        try {
            MyFile myFile = myFileMapper.selectByPrimaryKey(fid);
            if(myFile!=null || type<0){
                int flag = 0;
                OperationLog log = new OperationLog();
                log.setId(SeanTools.getUUID32());
                log.setFid(fid);
                User user = (User) SecurityUtils.getSubject().getPrincipal();
                log.setOperator(user.getUsername());
                log.setType(type);
                log.setTime(new Date());
                logMapper.insert(log);
                if(flag>0){
                    return true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public List<OperationLog> queryLog(String fileid, int type, boolean isLike) {
        if("".equals(fileid) || null == fileid)
            return null;
        List<OperationLog> logs = new ArrayList<>();
        try {
            if(isLike){ //模糊查
                logs = logMapper.selectLogByLike(fileid,type);
            }else{ //精准查
                logs = logMapper.selectLog(fileid,type);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return logs;
    }

}
