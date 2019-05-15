package com.qinhan.videoblog.service.impl;

import com.qinhan.videoblog.dal.BlogRepo;
import com.qinhan.videoblog.dal.UserRepo;
import com.qinhan.videoblog.dal.model.Blog;
import com.qinhan.videoblog.dal.model.User;
import com.qinhan.videoblog.service.MediaService;
import com.qinhan.videoblog.service.VideoService;
import com.qinhan.videoblog.web.common.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Date;

@Service
public class VideoServiceImpl implements VideoService {
    private final static long FILE_SIZE=1024*1024*600;
    @Autowired
    BlogRepo blogRepo;
    @Autowired
    UserRepo userRepo;
    @Autowired
    MediaService mediaService;
    @Override
    public int uploadVideo(MultipartFile video, String savepath, String ownername, String ffmpegpath) {
        //如果文件不为空，写入上传路径
        if(!video.isEmpty()){
            //获得上传文件的保存路径
            String path=savepath;
            //获取上传文件名并加入时间前缀作为该视频目录
            String filename= DateUtil.formatLong(new Date())+video.getOriginalFilename();
            String videopath=path+filename;

            Long filesize=video.getSize();
            if(filesize>FILE_SIZE){
                throw new RuntimeException("FILE_TOO_BIG#600");
            }
            File filepath=new File(path,filename);
            //判断路径是否存在，如果不存在就创建一个
            if(!filepath.getParentFile().exists()){
                filepath.getParentFile().mkdirs();//创建一个目录树
            }
            //将上传文件保存到目标文件当中
            try {
                video.transferTo(new File(path+File.separator+filename));
            } catch (IOException e) {
                throw new RuntimeException("FILE_UPLOAD_ERROR");
            }

            //抽取关键帧，保存为封面,获取时长
            String covername=DateUtil.formatLong(new Date())+".jpg";
            String upFilePath=videopath;//上传视频文件的路径
            String videoPicPath= path+File.separator+covername;//视频截图文件存放的路径
            //转码
            mediaService.convert(ffmpegpath,videopath,filename);
            //截图
            mediaService.cutPic(ffmpegpath,videopath,covername);
            //视频信息
           /* Map<String, String> meta = mediaService.getInfo(ffmpegpath,videopath);
            System.out.println(meta);*/

            //持久化
            User user=userRepo.findByUsername(ownername);
            Blog videoblog=new Blog();
            videoblog.setUserId(user.getUserId());
            videoblog.setVideoUrl(filename);
            videoblog.setCoverUrl(covername);
            videoblog.setDuration("00:00:00:000");
            videoblog.setBlogContent("默认博客发表内容-这个博主有点懒");
            videoblog.setBlogInfo("默认博客简介-这个博主有点懒");
            videoblog.setBlogTitle("默认博客标题-这个博主有点懒");
            videoblog.setCommentNum(0);
            videoblog.setStarNum(0);
            videoblog.setShareNum(0);
            videoblog.setCategoryId(0);
            videoblog.setType(1);
            blogRepo.save(videoblog);
            int blogId=blogRepo.findByVideoUrl(filename).getId();
            return blogId;
        }else{
            return 0;
        }
    }
}
