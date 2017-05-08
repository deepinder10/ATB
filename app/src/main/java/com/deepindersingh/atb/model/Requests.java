package com.deepindersingh.atb.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by deepindersingh on 06/05/17.
 */

public class Requests {
    @SerializedName("req_id")
    private Integer req_id;
    @SerializedName("name")
    private String name;
    @SerializedName("gender")
    private String gender;
    @SerializedName("age")
    private Integer age;
    @SerializedName("mobile")
    private String mobile;
    @SerializedName("email")
    private String email;
    @SerializedName("bgroup")
    private String blood_group;
    @SerializedName("reqdate")
    private String req_date;

    public Integer getReq_id() {
        return req_id;
    }

    public void setReq_id(Integer req_id) {
        this.req_id = req_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBlood_group() {
        return blood_group;
    }

    public void setBlood_group(String blood_group) {
        this.blood_group = blood_group;
    }

    public String getReq_date() {
        return req_date;
    }

    public void setReq_date(String req_date) {
        this.req_date = req_date;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getHospital_name() {
        return hospital_name;
    }

    public void setHospital_name(String hospital_name) {
        this.hospital_name = hospital_name;
    }

    @SerializedName("detail")

    private String detail;
    @SerializedName("hospital_name")
    private String hospital_name;

    public Requests(Integer req_id, String name, String gender, Integer age, String mobile, String email,
                 String blood_group, String req_date, String detail, String hospital_name) {
        this.req_id = req_id;
        this.name = name;
        this.gender = gender;
        this.age = age;
        this.mobile = mobile;
        this.email = email;
        this.blood_group = blood_group;
        this.req_date = req_date;
        this.detail = detail;
        this.hospital_name = hospital_name;
    }

}
