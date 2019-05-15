package com.qinhan.videoblog.web.modelvo;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.NumberFormat;

import javax.validation.constraints.Email;
import java.util.Date;

public class UserModifyForm {
    //用户邮箱
    @Email(message = "请输入一个正确的邮箱")
    private String email;
    //用户qq
    private String qq;
    //用户手机号码
    private String telephone;
    //用户年龄
    @NumberFormat(style = NumberFormat.Style.NUMBER)
    private int age;
    //用户生日 yyyy-mm-dd格式的字符串转换为Date
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private Date birthday;
    //用户头像url
    private String headingUrl;
    //用户地址
    private String address;
    //用户简介
    private String userAbout;

    @Override
    public String toString() {
        return "UserModifyForm{" +
                "email='" + email + '\'' +
                ", qq='" + qq + '\'' +
                ", telephone='" + telephone + '\'' +
                ", age=" + age +
                ", birthday=" + birthday +
                ", headingUrl='" + headingUrl + '\'' +
                ", address='" + address + '\'' +
                ", userAbout='" + userAbout + '\'' +
                '}';
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getHeadingUrl() {
        return headingUrl;
    }

    public void setHeadingUrl(String headingUrl) {
        this.headingUrl = headingUrl;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getUserAbout() {
        return userAbout;
    }

    public void setUserAbout(String userAbout) {
        this.userAbout = userAbout;
    }
}
