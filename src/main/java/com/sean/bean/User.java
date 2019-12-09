package com.sean.bean;

import java.util.Set;

public class User {
    private String uid;

    private String username;

    private String password;

    private String phonenamber;

    private String actualname;

    private Integer age;

    private Integer sex;

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

    public void setRoleSet(Set<Role> roleSet) {
        this.roleSet = roleSet;
    }
}