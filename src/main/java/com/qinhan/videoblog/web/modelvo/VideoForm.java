package com.qinhan.videoblog.web.modelvo;

import org.springframework.web.multipart.MultipartFile;

public class VideoForm {
    private MultipartFile video;

    public MultipartFile getVideo() {
        return video;
    }

    public void setVideo(MultipartFile video) {
        this.video = video;
    }

}
