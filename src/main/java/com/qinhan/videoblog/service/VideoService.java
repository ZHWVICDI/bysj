package com.qinhan.videoblog.service;

import org.springframework.web.multipart.MultipartFile;

public interface VideoService {
    int uploadVideo(MultipartFile video, String savepath, String ownername, String ffmpegpath);

}
