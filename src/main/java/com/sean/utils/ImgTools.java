package com.sean.utils;

import com.aliyuncs.utils.StringUtils;
import org.apache.commons.lang3.ArrayUtils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.*;
import java.net.URL;

public class ImgTools {

    private static final String FILE_CONNECTOR_POINT = "\\.";

    /**
     * 判断文件后缀是否为图片文件格式,bmp|gif|jpg|jpeg|png 返回true
     * @param imageFileSuffix 图片文件后缀名
     * @return
     */
    public static boolean isImageBySuffix(String imageFileSuffix) {
        if (StringUtils.isNotEmpty(imageFileSuffix)) {
            //[JPG, jpg, bmp, BMP, gif, GIF, WBMP, png, PNG, wbmp, jpeg, JPEG]
            String[] formatNames = ImageIO.getReaderFormatNames();
            if (ArrayUtils.isNotEmpty(formatNames)) {
                for (String formatName : formatNames) {
                    if (imageFileSuffix.toLowerCase().equals(formatName)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }


    /**
     * 判断文件是否为图片文件格式, bmp|gif|jpg|jpeg|png 后缀的图片文件返回true
     * @param imageName 图片文件名
     * @return
     */
    public static boolean isImageByFileName(String imageName) {
        if (StringUtils.isNotEmpty(imageName)) {
            String[] imageNames = imageName.split(FILE_CONNECTOR_POINT);
            return isImageBySuffix(imageNames[imageNames.length - 1]);
        }
        return false;
    }

    /**
     * 判断文件是否为合法图片
     * @param srcPath 图片文件绝对路径
     * @param checkImageName 是否校验图片文件名
     * @return
     */
    public static boolean isImage(String srcPath, boolean checkImageName) {
        if (checkImageName) {
            return isImageByFileName(srcPath);
        } else {
            if (StringUtils.isNotEmpty(srcPath)) {
                File imageFile = new File(srcPath);
                if (imageFile.isFile() && imageFile.length() > 0) {
                    return isImage(imageFile);
                }
            }
        }
        return false;
    }

    /**
     * 判断文件流是否为合法图片
     * @param file 图片文件
     * @return
     */
    public static boolean isImage(File file) {
        return isImage((Object)file);
    }

    /**
     * 判断文件流是否为合法图片
     * @param srcInputStream 图片文件的流
     * @return
     */
    public static boolean isImage(InputStream srcInputStream) {
        return isImage((Object)srcInputStream);
    }

    /**
     * 判断文件流是否为合法图片
     * @return
     */
    public static boolean isImage(URL url) {
        return isImage((Object)url);
    }

    /**
     * 图片文件读取
     * @param obj (URL|InputStream|File)
     * @return
     */
    private static boolean isImage(Object obj) {
        Image image = null;
        if (obj != null) {
            try {
                if (obj instanceof URL) {
                    image = ImageIO.read((URL)obj);
                } else if (obj instanceof InputStream) {
                    image = ImageIO.read((InputStream)obj);
                } else if (obj instanceof File) {
                    image = ImageIO.read((File)obj);
                } else {
                    throw new IllegalArgumentException("不支持这种类型["+obj.getClass().getCanonicalName()+"]");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (image != null && image.getWidth(null) > 0 && image.getHeight(null) > 0) {
                return true;
            }
        }
        return false;
    }

//    /**
//     * 获取图片类型
//     * JPG图片头信息:FFD8FF
//     * PNG图片头信息:89504E47
//     * GIF图片头信息:47494638
//     * BMP图片头信息:424D
//     *
//     * @param is 图片文件流
//     * @return 图片类型:jpg|png|gif|bmp
//     */
//    public static String getImageType(InputStream is) {
//        String type = null;
//        if (is != null) {
//            byte[] b = new byte[4];
//            try {
//                is.read(b, 0, b.length);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            String hexStr = HexConverter.byteArrayToHexString(b, true);//图片文件流前4个字节的头信息（子文字母）
//            if (hexStr != null) {
//                if (hexStr.startsWith(JPG_HEX)) {
//                    type = JPG;
//                } else if (hexStr.startsWith(PNG_HEX)) {
//                    type = PNG;
//                } else if (hexStr.startsWith(GIF_HEX)) {
//                    type = GIF;
//                } else if (hexStr.startsWith(BMP_HEX)) {
//                    type = BMP;
//                }
//            }
//        }
//        return type;
//    }

//    /**
//     * 获取图片类型
//     * JPG图片头信息:FFD8FF
//     * PNG图片头信息:89504E47
//     * GIF图片头信息:47494638
//     * BMP图片头信息:424D
//     *
//     * @param file 图片文件
//     * @return 图片类型:jpg|png|gif|bmp
//     * @throws FileNotFoundException 未找到文件
//     */
//    public static String getImageType(File file) throws FileNotFoundException {
//        return getImageType(new FileInputStream(file));
//    }

//    /**
//     * 根据文件头，判断文件流是否为合法图片
//     * @param is 文件流
//     * @return
//     */
//    public static boolean isImageByHeader(InputStream is) {
//        return isImageBySuffix(getImageType(is));
//    }
//
//    /**
//     * 根据文件头，判断文件流是否为合法图片
//     * @param file 图片文件
//     * @return
//     * @throws FileNotFoundException
//     */
//    public static boolean isImageByHeader(File file) throws FileNotFoundException {
//        return isImageBySuffix(getImageType(file));
//    }

    public static void main(String[] args) {
        String  pathAndthumbImage = "http://xiaojs.vip:8888/group1/M00/00/00/rBCgn13p2iCAc5BUAAWiWRtBGCM777.jpg#@#http://xiaojs.vip:8888/group1/M00/00/00/rBCgn13p2iCAc5BUAAWiWRtBGCM777_337x190.jpg";
        String fileUrl="";
        String thumbImage="";
        System.out.println("pathAndthumbImage:"+pathAndthumbImage);
        String imgpath[] =  pathAndthumbImage.split("#@#");
        if(imgpath.length>1 ){
            fileUrl = imgpath[0];
            thumbImage = imgpath[1];
        }else if(imgpath.length>0){
            fileUrl = imgpath[0];
        }
        System.out.println("fileUrl:"+fileUrl);
        System.out.println("thumbImage:"+thumbImage);
    }
}
