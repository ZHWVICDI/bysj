package com.qinhan.videoblog.web;

import com.qinhan.videoblog.dal.model.Blog;
import com.qinhan.videoblog.dal.model.Category;
import com.qinhan.videoblog.dal.model.User;
import com.qinhan.videoblog.service.BlogService;
import com.qinhan.videoblog.service.CategoryService;
import com.qinhan.videoblog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.NumberFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class VideoDetailController {
    @Autowired
    BlogService blogService;
    @Autowired
    UserService userService;
    @Autowired
    CategoryService categoryService;

    @RequestMapping("/jumpVideoDetail")
    public String jumpVideoDetail(@NumberFormat Integer videoId, ModelMap modelMap) {
        Blog videoblog = null;
        try {
            videoblog = blogService.getVideoInfo(videoId);
        } catch (RuntimeException e) {
            if (e.getMessage().equals("VIDEOBLOG_NOT_FOUND")) {
                modelMap.put("result", "该视频博客未找到");
                return "pass/indexresult";
            } else {
                modelMap.put("result", "系统异常");
                return "pass/indexresult";
            }
        }
        //取出4条按照评论数来排序的博客
        Page<Blog> popularBlogs;
        try {
            popularBlogs = blogService.getPopularVideoBlogs(4);
        } catch (RuntimeException e) {
            e.printStackTrace();
            if (e.getMessage().equals("POPULARBLOGS_FOUND_FAILED")) {
                modelMap.put("result", "流行博客加载失败");
                return "pass/indexresult";
            } else {
                modelMap.put("result", "系统异常");
                return "pass/indexresult";
            }
        }
        //取出博客的所有人信息
        User owneruser = userService.getUserById(videoblog.getUserId());

        Category category = null;
        try {
            category = categoryService.getCategoryForCurVideoBlog(videoblog.getCategoryId());
        } catch (RuntimeException e) {
            if(e.getMessage().equals("CATEGORY_NOT_FOUND")){

            }else{
                modelMap.put("result", "系统异常");
                return "pass/indexresult";
            }
        }
        /*}*/
        modelMap.put("curvideoblog", videoblog);
        modelMap.put("curVideoPoster", owneruser);
        modelMap.put("popularBlogs", popularBlogs.getContent());
        modelMap.put("curBlogCategory", category);

        return "videoblog_detail";
    }

    @RequestMapping("/jumpVideoListCate")
    public String jumpVideoList() {
        return "videoblog_category";
    }

    @RequestMapping("/jumpVideoAll")
    public String jumpVideoAll() {
        return "videoblog_all";
    }
}
