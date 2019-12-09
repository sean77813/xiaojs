package com.sean.bean;

import java.util.HashSet;
import java.util.Set;

public class Role {
    private String rid;

    private String rname;

    private Set<User> userSet = new HashSet<>();

    private Set<Permission> permissionSet = new HashSet<>();

    public String getRid() {
        return rid;
    }

    public void setRid(String rid) {
        this.rid = rid == null ? null : rid.trim();
    }

    public String getRname() {
        return rname;
    }

    public void setRname(String rname) {
        this.rname = rname == null ? null : rname.trim();
    }

    public Set<User> getUsers() {
        return userSet;
    }

    public void setUserSet(Set<User> userSet) {
        this.userSet = userSet;
    }

    public Set<Permission> getPermissions() {
        return permissionSet;
    }

    public void setPermissionSet(Set<Permission> permissionSet) {
        this.permissionSet = permissionSet;
    }
}