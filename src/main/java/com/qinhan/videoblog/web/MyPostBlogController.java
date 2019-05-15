package com.qinhan.videoblog.web;

import com.qinhan.videoblog.dal.CategoryRepo;
import com.qinhan.videoblog.dal.model.Blog;
import com.qinhan.videoblog.dal.model.Category;
import com.qinhan.videoblog.dal.model.User;
import com.qinhan.videoblog.service.BlogService;
import com.qinhan.videoblog.service.CategoryService;
import com.qinhan.videoblog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class MyPostBlogController {
    @Autowired
    UserService userService;
    @Autowired
    BlogService blogService;
    @Autowired
    CategoryService categoryService;


    @RequestMapping("/toMyPersonblogs")
    public String showPersonBlogs(HttpSession session, ModelMap modelMap, Integer page, Integer size){
        String username=(String )session.getAttribute("username");
        User user = userService.findUserByUsername(username);
        System.out.println("page:"+page);
        if(page==null){
            page=1;
        }
        size=1;
        Page<Blog> uploadedBlogsPage= blogService.getUploadedVideoBlogs(user,page,size);

        List<Category> categoryList = null;
        try {
            categoryList=categoryService.getCategories();
        } catch (RuntimeException e) {
            String msg = null;
            if (e.getMessage().equals("FIRST_CATEGORY_GET_ERROR")) {
                modelMap.addAttribute("result", "博客获取分类失败异常");
                //然后转到异常页面
                return "pass/indexresult";
            }else{
                modelMap.addAttribute("result", "系统异常");
                //然后转到异常页面
                return "pass/indexresult";
            }
        }
        modelMap.put("categories", categoryList);
        modelMap.put("uploadedBLogsPage",uploadedBlogsPage);
        modelMap.put("userInfo",user);
        return "person/table-images-list";
    }
}
