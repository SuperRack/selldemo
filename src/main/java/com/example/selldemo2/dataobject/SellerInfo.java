package com.example.selldemo2.dataobject;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class SellerInfo {
    @Id
    private String id;

    private String username;

    private String password;

    private String openid;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }
}
