package com.sean.web.file;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONObject;
import com.sean.bean.MyFile;
import com.sean.bean.User;
import com.sean.fastdfs.FastDFSClientUtil;
import com.sean.service.FileService;
import com.sean.service.OperationLogService;
import com.sean.utils.Constants;
import com.sean.utils.FileTools;
import com.sean.utils.ImgTools;
import com.sean.utils.SeanTools;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.EnumUtils;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class FileBaseController {

    @Autowired
    private FastDFSClientUtil dfsClient;
    @Autowired
    private FileService fileService;
    @Autowired
    private OperationLogService logService;

    @RequestMapping("/")
    public String index() {
        return "index";
    }

    @PostMapping("/upload")
    public Map<String,Object> fdfsUpload(@RequestParam("file") MultipartFile file, HttpServletRequest request) {
        Map<String,Object> result = new HashMap<>();
        try {
            User user = (User) SecurityUtils.getSubject().getPrincipal();
            boolean isImgFile = ImgTools.isImageByFileName(file.getOriginalFilename());
            String fileUrl = "";
            String thumbImage = null;
            if(!isImgFile){
                fileUrl = dfsClient.uploadFile(file);
            }else{
             String  pathAndthumbImage = dfsClient.uploadImgAndThumbImage(file);
             String imgpath[] =  pathAndthumbImage.split("#@#");
                if(imgpath.length>1 ){
                    fileUrl = imgpath[0];
                    thumbImage = imgpath[1];
                }else if(imgpath.length>0){
                    fileUrl = imgpath[0];
                }
            }
            //获取类型代码
            String filetype = FileTools.getFileTypeCode(fileUrl);
            //获取文件大小
            String size = FileTools.FormetFileSize(file.getSize());
            boolean flag = fileService.saveFile(new MyFile(SeanTools.getUUID32(), fileUrl, user.getUid(), filetype, thumbImage,size,file.getOriginalFilename()));
            System.out.println("flag:"+flag);
            System.out.println("path:"+fileUrl);
            result.put("result",true);
            result.put("msg","成功上传文件，  '" + fileUrl + "'");
        } catch (IOException e) {
            result.put("result",false);
            result.put("msg","上传文件失败");
            e.printStackTrace();
        }
        return result;
    }

    @PostMapping("/upload_test")
    public Map<String,Object> fdfsUpload2(@RequestParam("file") MultipartFile file, HttpServletRequest request) {
        Map<String,Object> result = new HashMap<>();
        try {
            System.out.println("file:"+file.getOriginalFilename());
            result.put("result",true);
            result.put("msg","成功上传文件");
        } catch (Exception e) {
            result.put("result",false);
            result.put("msg","上传文件失败");
            e.printStackTrace();
        }
        return result;
    }

    /*
     * http://localhost/download?filePath=group1/M00/00/00/wKgIZVzZEF2ATP08ABC9j8AnNSs744.jpg
     */
    @PostMapping("/download")
    public void download(@RequestParam("filePath") String filePath ,HttpServletRequest request ,HttpServletResponse response) throws Exception {
        String fid = fileService.queryFileIdByPath(filePath);
        if("".equals(fid)){
            System.out.println("文件不存在,不支持下载...");
            return;
        }
        //http://101.37.77.138:8888/group1/M00/00/00/rBCgn13TgxWAJ8HWAAwSJo9DHRY860.jpg
        //    group1/M00/00/00/wKgIZVzZEF2ATP08ABC9j8AnNSs744.jpg
        String regular = "[a-zA-z]+://[^\\s]*8888/";
        filePath = filePath.replaceAll(regular,"");
        String[] paths = filePath.split("/");
        String groupName = null ;
        for (String item : paths) {
            if (item.indexOf("group") != -1) {
                groupName = item;
                break ;
            }
        }
        String path = filePath.substring(filePath.indexOf(groupName) + groupName.length() + 1, filePath.length());
        InputStream input = dfsClient.download(groupName, path);

        if (input!=null)
        logService.recordLog(fid,2); //添加[下载日志]

        //根据文件名获取 MIME 类型
        String fileName = paths[paths.length-1] ;
        System.out.println("fileName :" + fileName); // wKgIZVzZEF2ATP08ABC9j8AnNSs744.jpg
        String contentType = request.getServletContext().getMimeType(fileName);
        String contentDisposition = "attachment;filename=" + fileName;
        // 设置头
        response.setHeader("Content-Type",contentType);
        response.setHeader("Content-Disposition",contentDisposition);

        // 获取绑定了客户端的流
        ServletOutputStream output = response.getOutputStream();

        // 把输入流中的数据写入到输出流中
        IOUtils.copy(input,output);
        input.close();
        System.out.println("下载完成...");
    }



    /**
     * http://localhost/deleteFile?filePath=group1/M00/00/00/wKgIZVzZaRiAZemtAARpYjHP9j4930.jpg
     * @param fileName  group1/M00/00/00/wKgIZVzZaRiAZemtAARpYjHP9j4930.jpg
     * @param request
     * @param response
     * @return
     */
    @PostMapping("/deleteFile")
    public Map<String,Object> delFile(@RequestParam("fileName") String fileName,
                          @RequestParam("filePath")String filePath,
                          HttpServletRequest request ,HttpServletResponse response)  {
        System.out.println(">>>deleteFile");
        System.out.println("filePath:"+filePath);
        Map<String,Object> map = new HashMap<>();
        try {
            String fileId = fileService.queryFileIdByPath(filePath);
            if("".equals(fileId) || null == fileId ){
                map.put("msg","删除失败！");
                return map;
            }
            MyFile myFile = fileService.selectFileByFileId(fileId);
            String thumbimage= myFile.getThumbimage();
            boolean isDeled = fileService.deleteFile(filePath);
            System.out.println("isDeled=>"+isDeled);
            if(isDeled){
                dfsClient.delFile(null,filePath);
                if("".equals(thumbimage) && null == thumbimage){ //如果有缩略图，删除缩略图
                    dfsClient.delFile(null,thumbimage);
                }
                map.put("msg","删除成功！");
            }else{
                map.put("msg","删除失败！");
            }
        } catch(Exception e) {
            map.put("msg","删除失败！");
            // 文件不存在报异常 ： com.github.tobato.fastdfs.exception.FdfsServerException: 错误码：2，错误信息：找不到节点或文件
             e.printStackTrace();
        }
        return map;
    }


}