package com.qinhan.videoblog.service;

import java.util.Map;

/**
 * 多媒体服务
 */
public interface MediaService {
    /**
     * 获取信息
     * @param videoPath
     * @return
     */
    Map<String, String> getInfo(String ffmpegPath, String videoPath);

    /**
     * 转码
     * @param videoPath
     * @param target
     * @return
     */
    void convert(String ffmpegPath, String videoPath, String target);

    /**
     * 提取关键帧
     * @param  ffmpegPath
     * @param videoPath
     * @param fileName
     * @return
     */
    void cutPic(String ffmpegPath, String videoPath, String fileName);

}
