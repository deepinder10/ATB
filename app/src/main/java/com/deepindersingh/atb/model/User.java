package com.deepindersingh.atb.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by deepindersingh on 07/05/17.
 */

public class User {
    @SerializedName("flag")
    Integer flag;

    @SerializedName("message")
    String message;

    @SerializedName("token")
    String token;

    public Integer getFlag() {
        return flag;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
