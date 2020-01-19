package com.sean.bean;

import javax.security.auth.Subject;
import java.security.Principal;
import java.util.Set;

public class User implements Principal {
    private String uid;

    private String username;

    private String password;

    private String phonenamber;

    private String actualname;

    private Integer age;

    private Integer sex;

    private String avatar;

    private Set<Role> roleSet;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid == null ? null : uid.trim();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public Set<Role> getRoles() {
        return roleSet;
    }

    public String getPhonenamber() {
        return phonenamber;
    }

    public void setPhonenamber(String phonenamber) {
        this.phonenamber = phonenamber == null ? null : phonenamber.trim();
    }

    public String getActualname() {
        return actualname;
    }

    public void setActualname(String actualname) {
        this.actualname = actualname == null ? null : actualname.trim();
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public String getAvatar() { return avatar; }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public void setRoleSet(Set<Role> roleSet) {
        this.roleSet = roleSet;
    }

    @Override
    public String toString() {
        return "User{" +
                "uid='" + uid + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", phonenamber='" + phonenamber + '\'' +
                ", actualname='" + actualname + '\'' +
                ", age=" + age +
                ", sex=" + sex +
                ", avatar='" + avatar + '\'' +
                '}';
    }

    @Override
    public String getName() {
        return username;
    }

    @Override
    public boolean implies(Subject subject) {
        return false;
    }
}