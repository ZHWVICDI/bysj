package com.qinhan.videoblog.service.impl;

import com.qinhan.videoblog.dal.BlogRepo;
import com.qinhan.videoblog.dal.CategoryRepo;
import com.qinhan.videoblog.dal.UserRepo;
import com.qinhan.videoblog.dal.model.Blog;
import com.qinhan.videoblog.dal.model.User;
import com.qinhan.videoblog.service.BlogService;
import com.qinhan.videoblog.web.common.VideoConstants;
import com.qinhan.videoblog.web.modelvo.BlogBodyForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.List;
import java.util.Optional;

@Service
public class BlogServiceImpl implements BlogService {
    @Autowired
    CategoryRepo categoryRepo;
    @Autowired
    BlogRepo blogRepo;
    @Autowired
    UserRepo userRepo;

    /**
     * 上传博客内容
     * @param blogbody
     */
    @Override
    public void uploadBlog(BlogBodyForm blogbody) {
        User user=userRepo.findByUsername(blogbody.getUsername());
        if(user==null){
            throw new RuntimeException("USER_NOT_FOUND");
        }
        Blog blog;
        if(blogbody.getBlogId()==0){
            blog=new Blog();
        }else{
           blog=blogRepo.findById(blogbody.getBlogId()).get();
            if(blog==null){
                throw new RuntimeException("BLOG_NOT_FOUND");
            }
        }
        blog.setUserId(user.getUserId());
        blog.setBlogTitle(blogbody.getBlogTitle());
        blog.setBlogInfo(blogbody.getBlogInfo());
        blog.setBlogContent(blogbody.getBlogContent());
        blog.setType(blogbody.getType());
        blog.setCategoryId(blogbody.getBlogCategory());
        blogRepo.save(blog);
    }

    /**
     * 获取到上传博客 的分页对象
     * @param user
     * @param page
     * @param size
     * @return
     */
    @Override
    public Page<Blog> getUploadedVideoBlogs(User user, int page, int size) {
        //带条件的分页排序查询
        Blog blog=new Blog();
        blog.setUserId(user.getUserId());
        Example<Blog> example=Example.of(blog);

        Sort sort=new Sort(Sort.Direction.DESC,"createDate");
        Pageable pageable=PageRequest.of(page-1,size,sort);
        Page<Blog> blogs=blogRepo.findAll(example,pageable);

        return blogs;
    }

    /**
     * 通过视频博客的id获取博客对象信息
     * @param videoId
     * @return
     */
    @Override
    public Blog getVideoInfo(int videoId) {
        Optional<Blog> blogOpl=blogRepo.findById(videoId);
        if(blogOpl.isPresent()){
            Blog blog=blogOpl.get();
            if (blog.getShareNum()==null){
                blog.setShareNum(0);
            }
            blog.setShareNum(blog.getShareNum()+1);
            blogRepo.save(blog);
            return blogRepo.findById(videoId).get();
        }
        throw  new RuntimeException("VIDEOBLOG_NOT_FOUND");
    }

    /**
     * 获取受欢迎博客的分页对象  但我们只从0开始取，取几个
     * @param num
     * @return
     */
    @Override
    public Page<Blog> getPopularVideoBlogs(Integer num) {
        // TODO: 2019/5/10 这里如果有余力，就修改为获取最近一段时间内评论数最多的博客。这里暂时以评论数为倒序查找四条博客
        Sort sort=new Sort(Sort.Direction.DESC,"commentNum");
        Pageable pageable=PageRequest.of(0,num,sort);
        Page<Blog> blogs=blogRepo.findAll(pageable);
        if(blogs==null){
            throw new RuntimeException("POPULARBLOGS_FOUND_FAILED");
        }
        return blogs;
    }

    /**
     * 获取所有博客的分页对象
     * @param page
     * @param size
     * @return
     */
    @Override
    public Page<Blog> getAllBlogs(Integer page, Integer size) {
        Sort sort=new Sort(Sort.Direction.DESC,"createDate");
        Pageable pageable=PageRequest.of(page-1,size,sort);
        Page<Blog> blogs=blogRepo.findAll(pageable);
        return blogs;
    }

    /**
     * 获取到最受欢迎的博客的  这里根据观看数量来
     * @return
     */
    @Override
    public Blog getTheMostPopularBlog() {
        Sort sort=new Sort(Sort.Direction.DESC,"shareNum");
        Blog themostestBlog=blogRepo.findAll(sort).get(0);
        if(themostestBlog!=null){
            return themostestBlog;
        }
        throw new RuntimeException("THEBLOG_NOT_FOUND");
    }

    /**
     * 删除视频博客
     * @param blogId
     */
    @Override
    public void deleteBlogById(Integer blogId) {
        //获取博客url并且删除相关的视频文件、关键帧文件 converurl、以及videourl
        Blog blog=blogRepo.findById(blogId).get();
        if(blog==null){
            throw new RuntimeException("BLOG_NOT_FOUND");
        }
        // TODO: 2019/5/17 这里因为转码问题，所以用的仍然是MP4，所以之类的视频url是tem/下的视频文件，有余力更改过来
            //得到视频文件地址、关键帧文件地址
        String videopath= VideoConstants.VIDEO_TEMP_PATH+blog.getVideoUrl();
        String videoConvertedPath=VideoConstants.VIDEO_CONVERTED_PATH+blog.getVideoUrl();
        String cutpicPath=VideoConstants.PIC_CUTTED_PATH+blog.getCoverUrl();
        //然后删除
        File file1=new File(videopath);
        if(file1.exists()){
           file1.delete();
        }
        File file2=new File(videoConvertedPath);
        if(file2.exists()){
            file2.delete();
        }
        File file3=new File(cutpicPath);
        if(file3.exists()){
          file3.delete();
        }
        blogRepo.deleteById(blogId);
    }

    /**
     * 根据状态返回待审核视频列表
     * @param page
     * @param size
     * @param status
     * @return
     */
    @Override
    public Page<Blog> getAllBlogsByStatus(Integer page, Integer size, String status) {
        Blog blog=new Blog();
        blog.setStatus(status);
        Example<Blog> example=Example.of(blog);

        Sort sort=new Sort(Sort.Direction.DESC,"createDate");
        Pageable pageable=PageRequest.of(page-1,size,sort);
        Page<Blog> blogs=blogRepo.findAll(example,pageable);
        return blogs;
    }

    /**
     * 将博客的状态更改为指定的状态
     * @param videoId
     * @param status
     */
    @Override
    public void changeBlogStatus(Integer videoId,String status) {
        Blog blog=blogRepo.findById(videoId).get();
        blog.setStatus(status);
        blogRepo.save(blog);
    }

}
