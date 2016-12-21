package com.entity;

import java.util.Date;

import com.annotation.TableName;

/**
 * Created by jim on 2016/12/20.
 */
@TableName("user")
public class User {
    private String id;
    private String username;
    private String password;
    private String salt;
    private Date created_on;

    public String getId() {
        return id;
    }

    public void setId(final String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(final String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(final String password) {
        this.password = password;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(final String salt) {
        this.salt = salt;
    }

    public Date getCreated_on() {
        return created_on;
    }
}
