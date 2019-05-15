package com.qinhan.videoblog.service;

import com.qinhan.videoblog.dal.model.Blog;
import com.qinhan.videoblog.dal.model.User;
import com.qinhan.videoblog.web.modelvo.BlogBodyForm;
import org.springframework.data.domain.Page;

import java.util.List;

public interface BlogService {

    void uploadBlog(BlogBodyForm blogbody);

    Page<Blog> getUploadedVideoBlogs(User user, int page, int size);

    Blog getVideoInfo(int videoId);

    Page<Blog> getPopularVideoBlogs(Integer num);

    Page<Blog> getAllBlogs(Integer page, Integer size);

    Blog getTheMostPopularBlog();

    void deleteBlogById(Integer blogId);
}
