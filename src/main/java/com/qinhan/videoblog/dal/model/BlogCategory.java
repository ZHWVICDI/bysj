package com.qinhan.videoblog.dal.model;

import javax.persistence.*;

@Entity
@Table(name = "videoblog_blog_category")
public class BlogCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int Id;
    @Column(name = "category_id",nullable = false)
    private int categoryId;
    @Column(name="blog_id",nullable = false)
    private int blogId;

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public int getBlogId() {
        return blogId;
    }

    public void setBlogId(int blogId) {
        this.blogId = blogId;
    }

    @Override
    public String toString() {
        return "BlogCategory{" +
                "Id=" + Id +
                ", categoryId=" + categoryId +
                ", blogId=" + blogId +
                '}';
    }
}
