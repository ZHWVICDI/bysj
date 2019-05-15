package com.qinhan.videoblog.web.modelvo;

import org.springframework.format.annotation.NumberFormat;

public class ReplyCommentForm {
    private String replycontent;
    @NumberFormat
    private Integer replyId;

    private  String replynickname;
    @NumberFormat
    private  Integer posterId;

    private  String nickname;
    @NumberFormat
    private Integer videoId;
    @NumberFormat
    private Integer pId;
    @NumberFormat
    private Integer level;

    public String getReplycontent() {
        return replycontent;
    }

    public void setReplycontent(String replycontent) {
        this.replycontent = replycontent;
    }

    public Integer getReplyId() {
        return replyId;
    }

    public void setReplyId(Integer replyId) {
        this.replyId = replyId;
    }

    public String getReplynickname() {
        return replynickname;
    }

    public void setReplynickname(String replynickname) {
        this.replynickname = replynickname;
    }

    public Integer getPosterId() {
        return posterId;
    }

    public void setPosterId(Integer posterId) {
        this.posterId = posterId;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public Integer getVideoId() {
        return videoId;
    }

    public void setVideoId(Integer videoId) {
        this.videoId = videoId;
    }

    public Integer getpId() {
        return pId;
    }

    public void setpId(Integer pId) {
        this.pId = pId;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    @Override
    public String toString() {
        return "ReplyCommentForm{" +
                "replycontent='" + replycontent + '\'' +
                ", replyId=" + replyId +
                ", replynickname='" + replynickname + '\'' +
                ", posterId=" + posterId +
                ", nickname='" + nickname + '\'' +
                ", videoId=" + videoId +
                ", pId=" + pId +
                ", level=" + level +
                '}';
    }
}
