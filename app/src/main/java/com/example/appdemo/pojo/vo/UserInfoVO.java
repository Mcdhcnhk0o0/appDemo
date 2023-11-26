package com.example.appdemo.pojo.vo;

import com.example.appdemo.pojo.dao.User;
import com.example.appdemo.pojo.dao.UserDetail;

public class UserInfoVO {

    private User user;
    private UserDetail detail;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public UserDetail getDetail() {
        return detail;
    }

    public void setDetail(UserDetail detail) {
        this.detail = detail;
    }
}
