package com.qinhan.videoblog.web.modelvo;

import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.NumberFormat;

import javax.validation.constraints.NotBlank;

public class BlogBodyForm {
    private String username;

    @NotBlank(message = "博客标题不能为空")
    @Length(max = 50,message = "博客标题不能超过50")
    private String blogTitle;
    @NotBlank(message = "博客简介不能为空")
    @Length(max = 125,message = "博客简介不能超过125")
    private String blogInfo;
    @NotBlank(message = "博客内容不能为空")
    private String blogContent;
    @NumberFormat
    private int blogCategory;
    @NumberFormat
    private int type;
    @NumberFormat
    private int blogId;
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getBlogId() {
        return blogId;
    }

    public void setBlogId(int blogId) {
        this.blogId = blogId;
    }

    public String getBlogTitle() {
        return blogTitle;
    }

    public void setBlogTitle(String blogTitle) {
        this.blogTitle = blogTitle;
    }

    public String getBlogContent() {
        return blogContent;
    }

    public void setBlogContent(String blogContent) {
        this.blogContent = blogContent;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getBlogInfo() {
        return blogInfo;
    }

    public void setBlogInfo(String blogInfo) {
        this.blogInfo = blogInfo;
    }

    public int getBlogCategory() {
        return blogCategory;
    }

    public void setBlogCategory(int blogCategory) {
        this.blogCategory = blogCategory;
    }

    @Override
    public String toString() {
        return "BlogBodyForm{" +
                "username='" + username + '\'' +
                ", blogTitle='" + blogTitle + '\'' +
                ", blogContent='" + blogContent + '\'' +
                ", type=" + type +
                '}';
    }
}
