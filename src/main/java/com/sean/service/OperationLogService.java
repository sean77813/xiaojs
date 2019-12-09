package com.sean.service;

import com.sean.bean.OperationLog;

import java.util.List;

/**
 * 文件操作日志服务
 */
public interface OperationLogService {

    /**
     * 记录日志
     * @param fid 文件id
     * @param type 操作类型
     * @return
     */
    boolean recordLog(String fid,int type);

    List<OperationLog> queryLog(String fileid,int type,boolean isLike);
}
