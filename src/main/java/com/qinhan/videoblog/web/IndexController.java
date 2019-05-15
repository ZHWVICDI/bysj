package com.qinhan.videoblog.web;

import com.qinhan.videoblog.dal.model.Blog;
import com.qinhan.videoblog.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.NumberFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class IndexController {
    @Autowired
    BlogService blogService;
    @RequestMapping("/toIndex2")
    public String toIndex(){
        return "index";
    }
    @RequestMapping("/")
    public String toIndex2(){return "index";}

    @RequestMapping("/toIndex")
    public String getBlogs(@NumberFormat Integer page, @NumberFormat Integer size, ModelMap modelMap){

        if(page==null){
            page=1;
        }
        size=4;

        Page<Blog> blogs=blogService.getAllBlogs(page,size);
        Blog popestBlog=blogService.getTheMostPopularBlog();
        List<Blog>  popularBlogs=blogService.getPopularVideoBlogs(3).getContent();

        modelMap.put("indexBlogs",blogs);
        modelMap.put("theMostBlog",popestBlog);
        modelMap.put("popularBlogs",popularBlogs);
        return "index";
    }

    // TODO: 2019/5/13 有时间把这个推荐做成websocket实时推送方式
    @RequestMapping("/getPopestBlog")
    public String getPopesBlog(){
        Blog popestBlog=blogService.getTheMostPopularBlog();

        return null;
    }
}
