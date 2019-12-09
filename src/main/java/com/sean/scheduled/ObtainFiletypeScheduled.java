package com.sean.scheduled;

import com.sean.bean.MyFile;
import com.sean.dao.MyFileMapper;
import com.sean.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 文件类型匹配定时任务
 */
@Component
@Configuration      //1.主要用于标记配置类，兼备Component的效果。
@EnableScheduling   // 2.开启定时任务
public class ObtainFiletypeScheduled {

    @Autowired
    private MyFileMapper fileMapper;

    @Scheduled(cron = "30 10 1 * * ?")
//    @Scheduled(cron = "30 * * * * ?")
    //或直接指定时间间隔，例如：600秒
    //@Scheduled(fixedRate=5000)
    private void configureTasks() {
        System.err.println("执行[文件类型匹配]定时任务开始时间: " + LocalDateTime.now());
        List<MyFile> list =  fileMapper.selectFileAll();
        for(MyFile file:list){
            String filepath = file.getPath();
            String suffix = filepath.substring(filepath.lastIndexOf(".")+1,filepath.length());
            String oldfiletype = file.getFiletype();
            String filetype = "999"; //未匹配类型的文件默认值
            Constants.imgFile[] enumimg = Constants.imgFile.values();
            for (Constants.imgFile e : enumimg) {
                if(e.getMessage().equalsIgnoreCase(suffix)){
                    filetype =  e.getCode();
                }
            }
            Constants.docFile[] enumdoc = Constants.docFile.values();
            for (Constants.docFile e : enumdoc) {
                if(e.getMessage().equalsIgnoreCase(suffix)){
                    filetype =  e.getCode();
                }
            }
            if(oldfiletype != null || !"".equals(oldfiletype))
            {
                if(oldfiletype.equals(filetype)){
                    continue;
                }
            }
            file.setFiletype(filetype);
            fileMapper.updateByPrimaryKey(file);
        }
        System.err.println("执行[文件类型匹配]定时任务结束时间: " + LocalDateTime.now());
    }
}
