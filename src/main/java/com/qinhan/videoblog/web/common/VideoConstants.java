package com.qinhan.videoblog.web.common;

public interface VideoConstants {

	//转码工具存放位置---之后将通过服务器上下文来获取这个工具来使用。
	public static final String FFMPEG_INSTALLATION_PATH="F:\\bysj\\app\\ffmpeg\\bin\\ffmpeg.exe";
	//转码视频存放位置
	public static final String VIDEO_CONVERTED_PATH="F:\\bysj\\tmp\\video-converted\\";
	//视频截图存放位置
	public static final String PIC_CUTTED_PATH="F:\\bysj\\tmp\\pic-cutted\\";

	//用户上传视频存放位置
	public static final String VIDEO_TEMP_PATH="F:\\bysj\\tmp\\";

	//用户上传头像原始图片存放位置
	public static final String HEADING_PIC_ORIGIN_PATH="F:\\bysj\\tmp\\headings\\origin\\";
	//用户上传头像处理后存放位置
	public static final String HEADING_PIC_PATH="F:\\bysj\\tmp\\headings\\";
}
