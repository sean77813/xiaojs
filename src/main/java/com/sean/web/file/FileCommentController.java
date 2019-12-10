package com.sean.web.file;

import com.sean.bean.Comment;
import com.sean.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 评论
 */
@RestController
@RequestMapping("/comment")
public class FileCommentController {

    @Autowired
    private CommentService commentService;

    /**
     * 添加评论
     * @param fileid 文件id
     * @param msg 评论内容
     * @param Tx_isIncognito 是否匿名
     * @return
     */
    @PostMapping("addComment")
    public Map<String,Object> addComment(@RequestParam("fileid")String fileid,
                                         @RequestParam("msg")String msg,
                                         @RequestParam("Tx_isIncognito")String Tx_isIncognito){
        System.out.println("===>addComment:");
        System.out.println("fileid:"+fileid);
        System.out.println("msg:"+msg);
        System.out.println("Tx_isIncognito:"+Tx_isIncognito);
        Map<String,Object> map = new HashMap<>();
        boolean isIncognito = "true".equalsIgnoreCase(Tx_isIncognito)?true:false;
        boolean result = commentService.addComment(fileid, msg, isIncognito);
        if(result){
            System.out.println("评论成功！");
        }else{
            System.out.println("评论失败！");
        }
        map.put("result",result);
        return map;
    }

    @PostMapping("getComment")
    public Map<String,Object> getComment(@RequestParam("fileid")String fileid){
        System.out.println("===>getComment:");
        System.out.println("fileid:"+fileid);
        Map<String,Object> map = new HashMap<>();
        List<Comment> list = commentService.getComment(fileid);
        map.put("comments",list);
        return map;
    }
}
