package com.sean.utils;

/**
 * 常量类（枚举）
 *
 * @author GaoJinShan
 */
public class Constants {

    /**
     * 接口状态码
     *
     * @author GaoJinShan
     */
    public enum ResultCode {
        UNKNOWN_ERROR("-1", "未知错误"),
        SUCCESS("200", "成功"),
        PARAM_ERROR("201", "参数不合法"),
        DATABASE_ERROR("202", "数据库异常"),
        EXIST("403", "已存在");

        private String code;
        private String message;

        ResultCode(String code, String message) {
            this.code = code;
            this.message = message;
        }
        public String getCode() {
            return code;
        }
        public String getMessage() {
            return message;
        }
    }


    /**
     * 网络类型
     *
     * @author GaoJinShan
     */
    public enum NetworkType {
        WIRED(1, "有线网"), WIRELESS(2, "无线网");

        private final Integer value;
        private final String name;

        NetworkType(Integer value, String name) {
            this.value = value;
            this.name = name;
        }
        public Integer getValue() {
            return value;
        }
        public String getName() {
            return name;
        }
    }

    public enum imgFile {
        JPG("001", "jpg"),
        JPEG("002", "jpeg"),
        GIF("003", "gif"),
        PNG("004", "png");

        private String code;
        private String message;

        imgFile(String code, String message) {
            this.code = code;
            this.message = message;
        }
        public String getCode() {
            return code;
        }
        public String getMessage() {
            return message;
        }

        public String[] getVals(){
            String vals[] = new String[imgFile.values().length];
            Constants.imgFile[] s =  imgFile.values();
            int i = 0;
            for(Constants.imgFile c:s){
                vals[i] = c.getCode();
                i++;
            }
            return vals;
        }
    }

    public enum docFile{
        DOC("101", "doc"),
        DOCX("102", "docx"),
        XLS("103", "xls"),
        XLSX("104", "xlsx"),
        PDF("105", "pdf");

        private String code;
        private String message;

        docFile(String code, String message) {
            this.code = code;
            this.message = message;
        }
        public String getCode() {
            return code;
        }
        public String getMessage() {
            return message;
        }

        public String[] getVals(){
            String vals[] = new String[docFile.values().length];
            Constants.docFile[] s =  docFile.values();
            int i = 0;
            for(Constants.docFile c:s){
                vals[i] = c.getCode();
                i++;
            }
            return vals;
        }
    }

}
