package com.sean.web.file;

import com.sean.bean.OperationLog;
import com.sean.service.OperationLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 文件操作日志
 */
@RestController
@RequestMapping("/filelog")
public class FileLogController {

    @Autowired
    private OperationLogService logService;

    /**
     * 获取日志信息
     * @param fileid
     * @param type
     * @param Tx_isLike
     * @return
     */
    @PostMapping("/getlogs")
    public Map<String,Object> getLog(@RequestParam("fileid")String fileid,
                                     @RequestParam("type")int type,
                                     @RequestParam("isLike")String Tx_isLike){
        System.out.println("=====>getlogs");
        System.out.println("fileid"+fileid);
        System.out.println("type"+type);
        System.out.println("isLike"+Tx_isLike);
        boolean isLike = "true".equalsIgnoreCase(Tx_isLike)?true:false;
        List<OperationLog> logs = logService.queryLog(fileid,type,isLike);
        Map<String,Object> map = new HashMap<>();
        map.put("logs",logs);
        return map;
    }
}
