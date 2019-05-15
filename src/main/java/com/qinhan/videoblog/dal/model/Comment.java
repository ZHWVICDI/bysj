package com.qinhan.videoblog.dal.model;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "videoblog_comment")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer Id;
    /*被回复人Id*/
    @Column(name = "reply_Id")
    private Integer replyId;
    /*被回复人的用户名*/
    @Column(name = "replynickname")
    private String replyNickname;
    /*发表评论或回复的人Id*/
    @Column(name = "user_Id")
    private Integer userId;
    /*发表评论或回复的人的名字*/
    @Column(name = "nickname")
    private String nickname;
    /*评论的人关于的视频博客的ID*/
    @Column(name = "blog_Id")
    private Integer blogId;
    /*评论或回复的内容*/
    @Column(name = "content")
    private String content;
    /*创建时间ID*/
    @Column(name = "create_Date")
    @CreationTimestamp//由数据库创建时间
    @Temporal(TemporalType.TIMESTAMP)//指定日期精度
    private Date createDate;
    /*回复的评论的Id*/
    @Column(name = "p_id")
    private Integer Pid;
    @Column(name = "level")
    private Integer level;
    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }

    public Integer getReplyId() {
        return replyId;
    }

    public void setReplyId(Integer replyId) {
        this.replyId = replyId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getBlogId() {
        return blogId;
    }

    public void setBlogId(Integer blogId) {
        this.blogId = blogId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Integer getPid() {
        return Pid;
    }

    public void setPid(Integer pid) {
        Pid = pid;
    }

    public String getReplyNickname() {
        return replyNickname;
    }

    public void setReplyNickname(String replyNickname) {
        this.replyNickname = replyNickname;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "Id=" + Id +
                ", replyId=" + replyId +
                ", replyNickname='" + replyNickname + '\'' +
                ", userId=" + userId +
                ", nickname='" + nickname + '\'' +
                ", blogId=" + blogId +
                ", content='" + content + '\'' +
                ", createDate=" + createDate +
                ", Pid=" + Pid +
                ", level=" + level +
                '}';
    }
}
