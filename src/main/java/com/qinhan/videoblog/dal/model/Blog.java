package com.qinhan.videoblog.dal.model;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "videoblog_blog")
public class Blog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer Id;
    //发表博客人id
    @Column(name = "user_id", nullable = false)
    private Integer userId;
    //策略id
    @Column(name = "category_id", nullable = false)
    private Integer categoryId;
    //博客发表时间
    @Column
    @CreationTimestamp//由数据库创建时间
    @Temporal(TemporalType.TIMESTAMP)//指定日期精度
    private Date createDate;
    //博客标题
    @Column(name = "title", columnDefinition = "varchar(50) default '博客标题默认' comment '博客标题'",nullable = false)
    private String blogTitle;
    //博客简介
    @Column(name = "bloginfo",columnDefinition = "varchar(125) default '博客内容默认' comment '博客简介'",nullable = false)
    private String blogInfo;
    //博客内容
    @Column(name = "content", columnDefinition = "text default '该博文暂时没有内容' comment '博客内容'",nullable = false)
    private String blogContent;
    //图文博客的图片url
    @Column(name = "picture_url", columnDefinition = "varchar(512) comment '图文博客的url(限制为5)'")
    private String pictureUrl;//属于图文博客的图片
    //视频播客的视频url
    @Column(name = "video_url")
    private String videoUrl;//属于视频播客的视频url
    //视频播客的视频抽帧图片url
    @Column(name = "cover_url")
    private String coverUrl;//属于视频封面的抽帧url
    //视频博客的视频的时长
    @Column(name = "duration", columnDefinition = "varchar(50) default '00:00:00:000' comment '视频时长'")
    private String duration;
    //评论人数
    @Column(name = "commentnum", columnDefinition = "int(11) default 0")
    private Integer commentNum;
    //分享人数
    @Column(name = "viewnum", columnDefinition = "int(11) default 0")
    private Integer shareNum;
    //收藏人数
    @Column(name = "starnum", columnDefinition = "int(11) default 0")
    private Integer starNum;
    //0-图文博客  1-视频播客
    @Column(name = "type", columnDefinition = "int(11) default 0 comment '博客类型 0-图文博客 1-视频播客'", nullable = false)
    private Integer type;
    @Column(name = "status", columnDefinition = "varchar(20) default 'NORMAL' comment '状态 NORMAL|FROZEN'")
    private String status;



    public String getBlogInfo() {
        return blogInfo;
    }

    public void setBlogInfo(String blogInfo) {
        this.blogInfo = blogInfo;
    }

    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
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

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public String getCoverUrl() {
        return coverUrl;
    }

    public void setCoverUrl(String coverUrl) {
        this.coverUrl = coverUrl;
    }

    public Integer getCommentNum() {
        return commentNum;
    }

    public void setCommentNum(Integer commentNum) {
        this.commentNum = commentNum;
    }

    public Integer getShareNum() {
        return shareNum;
    }

    public void setShareNum(Integer shareNum) {
        this.shareNum = shareNum;
    }

    public Integer getStarNum() {
        return starNum;
    }

    public void setStarNum(Integer starNum) {
        this.starNum = starNum;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    @Override
    public String toString() {
        return "Blog{" +
                "Id=" + Id +
                ", userId=" + userId +
                ", createDate=" + createDate +
                ", blogTitle='" + blogTitle + '\'' +
                ", blogContent='" + blogContent + '\'' +
                ", pictureUrl='" + pictureUrl + '\'' +
                ", videoUrl='" + videoUrl + '\'' +
                ", coverUrl='" + coverUrl + '\'' +
                ", commentNum=" + commentNum +
                ", shareNum=" + shareNum +
                ", starNum=" + starNum +
                ", type=" + type +
                ", status='" + status + '\'' +
                '}';
    }
}
