package com.sean.utils;

import org.apache.commons.lang3.EnumUtils;

import java.io.File;
import java.io.FileInputStream;
import java.text.DecimalFormat;

public class FileTools {
    public static final int SIZETYPE_B = 1;//获取文件大小单位为B的double值
    public static final int SIZETYPE_KB = 2;//获取文件大小单位为KB的double值
    public static final int SIZETYPE_MB = 3;//获取文件大小单位为MB的double值
    public static final int SIZETYPE_GB = 4;//获取文件大小单位为GB的double值


    public static String getFileSuffix(final String fileName,final boolean IsUpperCase) {
        if(fileName==null || fileName.length()==0)
            return "";
        String type = getFileExt(fileName);
        String result = "";
        if(isInt(type)){
            String tmpName = fileName.substring(0, fileName.length()-type.length()-1);

            String type2 = getFileExt(tmpName);
            if(type2.length()>0){
                result = type2;
            }else{
                result = type;
            }
        }else{
            result = type;
        }
        if(IsUpperCase){
            return result==null?"":result.toUpperCase();
        }else{
            return result==null?"":result;
        }
    }

    public static String getFileExt(final String fileName) {
        if(fileName==null || fileName.length()==0)
            return "";

        int i=fileName.lastIndexOf(".");
        if(i>=0 && i<fileName.length()-1)
            return fileName.substring(i+1);
        else
            return "";
    }

    public static boolean isInt(final String str){
        try{
            int x = Integer.parseInt(str);
            return true;
        }catch(Exception e){
            return false;
        }
    }

    public static String getFileTypeCode(String fileUrl){
        String filetype = "";
        try {
            String suffix = FileTools.getFileSuffix(fileUrl,true);
            if(suffix == null || "".equals(suffix)){
                return "";
            }
            boolean isImg = EnumUtils.isValidEnum(Constants.imgFile.class, suffix);
            boolean isDoc = EnumUtils.isValidEnum(Constants.docFile.class, suffix);
            if(isImg){
                filetype = Constants.imgFile.valueOf(suffix).getCode();
            }
            if(isDoc){
                filetype = Constants.docFile.valueOf(suffix).getCode();
            }
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            return "";
        }
        return filetype;
    }

    /**
     * 获取文件指定文件的指定单位的大小
     *
     * @param filePath 文件路径
     * @param sizeType 获取大小的类型1为B、2为KB、3为MB、4为GB
     * @return double值的大小
     */
    public static double getFileOrFilesSize(String filePath, int sizeType) {
        File file = new File(filePath);
        long blockSize = 0;
        try {
            if (file.isDirectory()) {
                blockSize = getFileSizes(file);
            } else {
                blockSize = getFileSize(file);
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("获取文件大小获取失败!");
        }
        return FormetFileSize(blockSize, sizeType);
    }

    /**
     * 调用此方法自动计算指定文件或指定文件夹的大小
     *
     * @param filePath 文件路径
     * @return 计算好的带B、KB、MB、GB的字符串
     */
    public static String getAutoFileOrFilesSize(String filePath) {
        File file = new File(filePath);
        long blockSize = 0;
        try {
            if (file.isDirectory()) {
                blockSize = getFileSizes(file);
            } else {
                blockSize = getFileSize(file);
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("获取文件大小获取失败!");
        }
        return FormetFileSize(blockSize);
    }

    /**
     * 获取指定文件大小
     *
     * @throws Exception
     */
    public static long getFileSize(File file) throws Exception {
        long size = 0;
        if (file.exists()) {
            FileInputStream fis = null;
            fis = new FileInputStream(file);
            size = fis.available();
        } else {
            file.createNewFile();
            System.out.println("获取文件大小文件不存在!");
        }
        return size;
    }

    /**
     * 获取指定文件夹
     *
     * @throws Exception
     */
    public static long getFileSizes(File f) throws Exception {
        long size = 0;
        File flist[] = f.listFiles();
        for (int i = 0; i < flist.length; i++) {
            if (flist[i].isDirectory()) {
                size = size + getFileSizes(flist[i]);
            } else {
                size = size + getFileSize(flist[i]);
            }
        }
        return size;
    }

    /**
     * 转换文件大小
     */
    public static String FormetFileSize(long fileS) {
        DecimalFormat df = new DecimalFormat("#.00");
        String fileSizeString = "";
        String wrongSize = "0B";
        if (fileS == 0) {
            return wrongSize;
        }
        if (fileS < 1024) {
            fileSizeString = df.format((double) fileS) + "B";
        } else if (fileS < 1048576) {
            fileSizeString = df.format((double) fileS / 1024) + "KB";
        } else if (fileS < 1073741824) {
            fileSizeString = df.format((double) fileS / 1048576) + "MB";
        } else {
            fileSizeString = df.format((double) fileS / 1073741824) + "GB";
        }
        return fileSizeString;
    }

    /**
     * 转换文件大小,指定转换的类型
     */
    public static double FormetFileSize(long fileS, int sizeType) {
        DecimalFormat df = new DecimalFormat("#.00");
        double fileSizeLong = 0;
        switch (sizeType) {
            case SIZETYPE_B:
                fileSizeLong = Double.valueOf(df.format((double) fileS));
                break;
            case SIZETYPE_KB:
                fileSizeLong = Double.valueOf(df.format((double) fileS / 1024));
                break;
            case SIZETYPE_MB:
                fileSizeLong = Double.valueOf(df.format((double) fileS / 1048576));
                break;
            case SIZETYPE_GB:
                fileSizeLong = Double.valueOf(df.format((double) fileS / 1073741824));
                break;
            default:
                break;
        }
        return fileSizeLong;
    }

    public static void main(String[] args) {
        String filetype = "E://图片/ChMkJl3g4u-IXLYLAAOQBJKADiEAAvffQOpyiMAA5Ac374.jpg";
              //  filetype = "http://xiaojs.vip:8888/group1/M00/00/00/rBCgn13p9baAQpBQAAMAsdgvXZY845_337x190.jpg";
        File file=new File(filetype);
        file.getPath();
        String name = file.getName();
        System.out.println(name);
        long length = file.length();
        System.out.println(file.getPath());
        try{
            String printSize = FormetFileSize(196881);
            System.out.println(printSize);
        }catch(Exception e){

        }
        //String printSize = FileTools.getFileSize(file);

    }
}
