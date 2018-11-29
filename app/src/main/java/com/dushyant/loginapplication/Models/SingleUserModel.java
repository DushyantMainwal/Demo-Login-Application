package com.dushyant.loginapplication.Models;

import com.google.gson.annotations.SerializedName;

public class SingleUserModel {

    @SerializedName("data")
    private UserModel userModel;

    public UserModel getUserModel() {
        return userModel;
    }

    public void setData(UserModel userModel) {
        this.userModel = userModel;
    }

}

