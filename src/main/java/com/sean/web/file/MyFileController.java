package com.sean.web.file;

import com.alibaba.fastjson.JSONObject;
import com.sean.bean.MyFile;
import com.sean.bean.MyFileInfo;
import com.sean.bean.PublicPic;
import com.sean.bean.User;
import com.sean.service.FileService;
import com.sean.spring.prop.SeanJson;
import com.sean.utils.SeanTools;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class MyFileController {

    @Autowired
    private FileService fileService;
    @Autowired
    private SeanJson sJson;

    @PostMapping("/getMyfiles")
    public String getMyfiles(@RequestParam(value="filetype",required=false )String filetype){
        System.out.println("filetype:"+filetype);
        JSONObject json = new JSONObject();
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        List<MyFile> list =  fileService.queryMyFiles(user.getUid(),filetype);
        System.out.println("size:"+list.size());
        json.put("files",list);
        String str = json.toJSONString();
//        System.out.println(str);
        return str;
    }

    @PostMapping("/getPPic")
    public String getPPicList(){
        JSONObject json = new JSONObject();
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        List<MyFile> list =  fileService.queryPPics();
        json.put("files",list);
        String str = json.toJSONString();
        return str;
    }

    /**
     * 获取分享图片属性
     * @param fid
     * @return
     */
    @PostMapping("/getPPicPropByFid")
    public Map<String,Object> getPPicPropByFid(@RequestParam("fileid")String fid){
        Map<String,Object> map = new HashMap<>();
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        PublicPic ppic =  fileService.queryPicPropByFid(fid);
        map.put("ppic",ppic);
        return map;
    }

    /**
     * 获取分享文件标签
     * @param fid
     * @return
     */
    @PostMapping("/getPPicLabelByFid")
    public Map<String,Object> getPPicLabelByFid(@RequestParam("fileid")String fid){
        Map<String,Object> map = new HashMap<>();
        String labels =  fileService.queryLabelByFid(fid);
        map.put("labels",labels);
        return map;
    }

    /**
     *  获取扩展属性json
     * @return
     */
    @ResponseBody
    @PostMapping("/getExtPropJson")
    public String getExtPropJson(){
        String json = sJson.jsonRead();
        return json;
    }
    /**
     *  给文件添加扩展属性
     * @param fileid
     * @param props
     * @return
     */
    @ResponseBody
    @PostMapping("/addExtProp")
    public String addExtProp(@RequestParam("fileid")String fileid,
                             @RequestParam("props")String props){
        System.out.println("fileid:"+fileid);
        System.out.println("props:"+props);
        String resultCode = "";
        if(null !=props && !"".equals(props)){
            if(!SeanTools.isJSONValid(props)){ //json是否合法
               return "501"; //json不合法
            }
            resultCode = fileService.addExtProp(fileid,props);
        }
        resultCode = fileService.addExtProp(fileid,"");
        return resultCode;
    }

    /**
     * 添加文件标签
     * @param fileid 文件Id
     * @param labels 标签内容
     * @param level 级别
     * @return
     */
    @ResponseBody
    @PostMapping("/addlabel")
    public String addFileLabel(@RequestParam("fileid")String fileid,
                               @RequestParam("labels")String labels,
                               @RequestParam("level")String level){
        System.out.println("fileid:"+fileid);
       String resultCode =  fileService.addLabel(fileid,labels,level);
       return resultCode;
    }

    /**
     * 分享文件
     * @param fileid
     * @return
     */
    @ResponseBody
    @PostMapping("/sharepic")
    public String sharePic(@RequestParam("fileid")String fileid){
        System.out.println("sharepic  fileid:"+fileid);
        String resultCode = "";
        resultCode = fileService.sharePicToPublic(fileid);
        return resultCode;
    }

    /**
     * 获取文件基础信息
     * @param fileid
     * @return
     */
    @ResponseBody
    @PostMapping("/getFileInfo")
    public Map<String,Object> getFileInfo(@RequestParam("fileid")String fileid){
        MyFileInfo fileInfo = fileService.getFileInfo(fileid);
        Map<String,Object> map = new HashMap<>();
        map.put("fileInfo",fileInfo);
        return map;
    }






}
