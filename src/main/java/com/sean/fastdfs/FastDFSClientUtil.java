package com.sean.fastdfs;

import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.FilenameUtils;
import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.ProtoCommon;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.github.tobato.fastdfs.domain.fdfs.StorePath;
import com.github.tobato.fastdfs.domain.fdfs.ThumbImageConfig;
import com.github.tobato.fastdfs.domain.proto.storage.DownloadCallback;
import com.github.tobato.fastdfs.service.FastFileStorageClient;


@Component
public class FastDFSClientUtil {

    @Value("${fdfs.reqHost}")
    private String reqHost;

    @Value("${fdfs.reqPort}")
    private String reqPort;

    @Autowired
    private FastFileStorageClient storageClient;

    @Autowired
    private ThumbImageConfig thumbImageConfig; //创建缩略图   ， 缩略图访问有问题，暂未解决

    /**
     * 上传文件
     * @param file
     * @return
     * @throws IOException
     */
    public String uploadFile(MultipartFile file) throws IOException {
        StorePath storePath = storageClient.uploadFile((InputStream)file.getInputStream(),file.getSize(), FilenameUtils.getExtension(file.getOriginalFilename()),null);
        return getResAccessUrl(storePath);
    }

    /**
     * 上传图片及缩略图
     * @param file
     * @return
     * @throws IOException
     */
    public String uploadImgAndThumbImage(MultipartFile file) throws IOException {
        //上传图片的缩略图
        StorePath storePath = storageClient.uploadImageAndCrtThumbImage((InputStream)file.getInputStream(),file.getSize(), FilenameUtils.getExtension(file.getOriginalFilename()),null);
        String imgPath = storePath.getFullPath();
        String thumbImagePath = thumbImageConfig.getThumbImagePath(imgPath);
        System.out.println("【原图路径】:"+getResAccessUrl(imgPath));
        System.out.println("【缩略图路径】:" + getResAccessUrl(thumbImagePath));
        StringBuffer path = new StringBuffer(getResAccessUrl(storePath));
        path.append("#@#");
        path.append(getResAccessUrl(thumbImagePath));
        return path.toString();
    }

    public void delFile(String groupName , String path) {
        storageClient.deleteFile(path);
    }

    public InputStream download(String groupName, String path ) {
        InputStream ins =  storageClient.downloadFile(groupName, path, new DownloadCallback<InputStream>(){
            @Override
            public InputStream recv(InputStream ins) throws IOException {
                // 将此ins返回给上面的ins
                return ins;
            }}) ;
        return ins;
    }

    /**
     * 带有防盗链的下载
     *
     * @param fileGroup       文件组名
     * @param remoteFileName  远程文件名称
//     * @param clientIpAddress 客户端IP地址
     * @return 完整的URL地址
     */
    public String autoDownloadWithToken(String fileGroup, String remoteFileName) throws Exception {
        int ts = (int) (System.currentTimeMillis() / 1000);
        System.out.println(ClientGlobal.getG_secret_key());
        String token = ProtoCommon.getToken(remoteFileName, ts, ClientGlobal.getG_secret_key());
        return "http://" + reqHost + "/" + fileGroup + "/" + remoteFileName + "?token=" + token + "&ts=" + ts;
    }

//    public static String getToken(String fileGroup, String remoteFileName){
//
//        String substring = fid.substring(fid.indexOf("/")+1);
//        //unix时间戳 以秒为单位
//        int ts = (int) (System.currentTimeMillis() / 1000);
//        String token=new String();
//        try {
//            token= ProtoCommon.getToken(substring, ts, secret_key);
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        } catch (NoSuchAlgorithmException e) {
//            e.printStackTrace();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        StringBuilder sb = new StringBuilder();
//        sb.append(IP);
//        sb.append(fid);
//        sb.append("?token=").append(token);
//        sb.append("&ts=").append(ts);
//        //System.out.println(sb.toString());
//        return sb.toString();
//    }


    /**
     * 封装文件完整URL地址
     * @param storePath
     * @return
     */
    private String getResAccessUrl(StorePath storePath) {
        String fileUrl = "http://" + reqHost + ":" + reqPort + "/" + storePath.getFullPath();
        return fileUrl;
    }

    private String getResAccessUrl(String path) {
        String fileUrl = "http://" + reqHost + ":" + reqPort + "/" + path;
        return fileUrl;
    }
}