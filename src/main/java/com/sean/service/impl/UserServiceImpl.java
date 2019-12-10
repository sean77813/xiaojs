package com.sean.service.impl;

import com.sean.bean.User;
import com.sean.bean.UserAndRole;
import com.sean.dao.UserAndRoleMapper;
import com.sean.dao.UserMapper;
import com.sean.fastdfs.FastDFSClientUtil;
import com.sean.service.UserService;
import com.sean.utils.SeanTools;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserAndRoleMapper urMapper;
    @Autowired
    private FastDFSClientUtil dfsClient;

    @Override
    public User findByUsername(String username) {
        if(username==null || "".equals(username))
            return null;
        User user = userMapper.selectByUsername(username);
        return user;
    }

    @Override
    public User getUserById(String userid) {
        User user = null;
        try {
            if("".equals(userid) || null == userid){
                User obj = (User) SecurityUtils.getSubject().getPrincipal();
                userid = obj.getUid();
            }
            user = new User();
            user = userMapper.selectByPrimaryKey(userid);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return user;
    }

    @Override
    public int doLogin(String username, String password) {
        int flag = 0;
//        User user = userMapper.getUser(username);
//        if(user == null)
//            return flag;
//        String pwdmd5 = user.getPassword();
//        String pagemd5 = MD5.MD5(password);
//        if(pwdmd5.equals(pagemd5)){
//            flag = 1;
//        }else{
//            flag = -1;
//        }
        return flag;
    }

    @Override
    public List<User> getList() {
        List<User> list = new ArrayList<>();
        list = userMapper.getList();
        return list;
    }

    @Override
    public String isExistsUserName(String username) {
        if ("".equals(username) || null == username)
            return "";
        String uid = "";
        try {
            uid =  userMapper.selectUidByUsername(username);
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
        return uid;
    }

    @Override
    public boolean register(User user) {
        System.out.println("姓名:"+user.getActualname());
        int flag = 0;
        try {
            user.setUid(SeanTools.getUUID32());
            flag = userMapper.insertSelective(user);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        if(flag>0){
            urMapper.insert(new UserAndRole(user.getUid(),"2"));
            return true;
        }
        return false;
    }

    @Transactional
    @Override
    public String updateAvatar(MultipartFile avatarfile) {
        User obj = (User) SecurityUtils.getSubject().getPrincipal();
        String uid = obj.getUid();
        if("".equals(uid) || null == uid)
            return "";
        try {
            User user = userMapper.selectByPrimaryKey(uid);
            if(null == user)
                return "";
            int flag = 0;
            String old_avatar = user.getAvatar(); //原头像
            String new_avatar = dfsClient.uploadFile(avatarfile); //上传新头像

            //更新头像
            try {
                user.setAvatar(new_avatar);
                flag = userMapper.updateByPrimaryKeySelective(user);
            } catch (Exception e) {
                //新头像上传成功后出错,删除头像
                e.printStackTrace();
                dfsClient.delFile(null,new_avatar);
            }
            //删除原头像
            if (null == old_avatar && "".equals(old_avatar)){
                dfsClient.delFile(null,old_avatar);
            }
            if(flag>0){
                return new_avatar;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
        return "";
    }

    @Override
    public String getUserAvatar(String uid) {
        String avatar = "";
        try {
            if("".equals(uid) || null == uid){
                User obj = (User) SecurityUtils.getSubject().getPrincipal();
                uid = obj.getUid();
            }
            User user = userMapper.selectByPrimaryKey(uid);
            if (null == user)
                return "";
            avatar = user.getAvatar();
            if ("".equals(avatar) || null == avatar)
                return "";
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
        return avatar;
    }

    @Override
    public boolean editUserInfo(User user) {
        if (null == user)
            return false;
        String uid = user.getUid();
        if ("".equals(uid) || null == uid)
            return false;
        try {
            User old_user = userMapper.selectByPrimaryKey(uid);
            if (null ==old_user)
                return false;
            int flag = 0;
            String actualname = user.getActualname();
            boolean edit_actualname = false; //是否修改姓名
            if (!"".equals(actualname) && null != actualname)
                edit_actualname = true;
            if (edit_actualname){
                if (actualname.equals(old_user.getActualname())){
                    edit_actualname = false;
                }
            }

            String phonenamber = user.getPhonenamber();
            boolean edit_phonenamber = false; //是否修改手机号码
            if (!"".equals(phonenamber) && null != phonenamber)
                edit_phonenamber = true;
            if (edit_phonenamber){
                if (phonenamber.equals(old_user.getPhonenamber())){
                    edit_phonenamber = false;
                }
            }

            int age = user.getAge();
            boolean edit_age = false;
            if (age>0)
                edit_age = true;
            if (edit_age){
                if (null != old_user.getAge() && age == old_user.getAge()){
                    edit_age = false;
                }
            }

            int sex = user.getSex();
            boolean edit_sex = true;
            if (null != old_user.getSex() && sex == old_user.getSex()){
                edit_sex = false;
            }
            //不需修改项置null
            if(!edit_actualname)
                user.setActualname(null);
            if(!edit_phonenamber)
                user.setPhonenamber(null);
            if (!edit_age)
                user.setAge(null);
            if (!edit_sex)
                user.setSex(null);
            //不修改项
            user.setUsername(null);
            user.setPassword(null);
            user.setAvatar(null);

            if(edit_actualname || edit_phonenamber || edit_age ||edit_sex)
            flag = userMapper.updateByPrimaryKeySelective(user);
            if (flag>0){
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return false;
    }
}
