package com.qinhan.videoblog.web;

import com.qinhan.videoblog.dal.model.Blog;
import com.qinhan.videoblog.dal.model.Category;
import com.qinhan.videoblog.dal.model.User;
import com.qinhan.videoblog.service.BlogService;
import com.qinhan.videoblog.service.CategoryService;
import com.qinhan.videoblog.service.UserService;
import com.qinhan.videoblog.web.common.SimpleDigestUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.NumberFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
public class ManagerBlogController {
    @Autowired
    UserService userService;
    @Autowired
    BlogService blogService;
    @Autowired
    CategoryService categoryService;
    @RequestMapping("/toManagerLogin")
    public String toManagerLogin(){

        return  "manager/ManagerLogin";
    }

    /**
     * 跳转到视频列表页面审核界面
     * @param modelMap
     * @param page
     * @param size
     * @return
     */
    @RequestMapping("/toVideoList")
    public String toVideoListPage(ModelMap modelMap,@NumberFormat Integer page, Integer size){
        if(page==null){
            page=1;
        }
        size=1;
        Page<Blog> bLogsPage=null;

        /*获取到视频播客*/
            bLogsPage=blogService.getAllBlogs(page,size);

        List<String> statusList = new ArrayList<>();
        statusList.add("NORMAL");
        statusList.add("FROZEN");
        statusList.add("AUDIT");

        modelMap.put("categories", statusList);
        modelMap.put("bLogsPage",bLogsPage);
        return "manager/managerVideoList";
    }

    @RequestMapping("/toManagerNormal")
    public String toPerson(HttpSession httpSession, ModelMap modelMap) {
        return "manager/managerPersonal";
    }

    /**
     * 管理员登陆
     * @param session
     * @param username
     * @param password
     * @param modelMap
     * @return
     */
    @RequestMapping("/MagagerLogin")
    public String loginForManager(HttpSession session, String username, String password, ModelMap modelMap){
        boolean flag=userService.checkSuperUserInfo(username, SimpleDigestUtil.encryptSHA(password));
        User user=userService.findUserByUsername(username);
        //如果成功登陆则
        if(flag){
            /*将管理员的用户名放入到session中*/
            session.setAttribute("managerUsername",user.getUsername());
            //如果登陆成功直接进入到管理员界面
            return "manager/managerPersonal";
        }
        //提示用户登录失败
        modelMap.addAttribute("result","管理员登陆失败,请重新登陆！");
        return "manager/ManagerLogin";
    }

    /**
     * 管理员删除有问题的博客
     * @param videoId
     * @param modelMap
     * @return
     */
    @RequestMapping("/deleteBlogByManager")
    public String deleteBlog(@NumberFormat Integer videoId, ModelMap modelMap){
        //删除博客信息
        try{
            blogService.deleteBlogById(videoId);
        }catch (RuntimeException e){
            e.printStackTrace();
            if(e.getMessage()!=null&&e.getMessage().equals("DELETE_BLOG_ERROR")){
                modelMap.put("result","删除视频博客失败");
                return "pass/deleteResult";
            }
        }
        //请求重新渲染博客页面
        modelMap.put("result","删除该博客成功");
        return "pass/managerDeleteBlog";
    }

    /**
     * 管理员审核视频
     * @param videoId
     * @param modelMap
     * @param page
     * @return
     */
    @RequestMapping("/auditBlogByManager")
    public String auditBlog(@NumberFormat Integer videoId, ModelMap modelMap,Integer page){
        Blog videoblog = null;
        try {
            videoblog = blogService.getVideoInfo(videoId);
        } catch (RuntimeException e) {
            if (e.getMessage().equals("VIDEOBLOG_NOT_FOUND")) {
                modelMap.put("result", "该视频播客未找到");
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
        modelMap.put("curBlogCategory", category);
        modelMap.put("curBlogPage",page);
        return "manager/managerAuditDetial";
    }

    /**
     * 管理员审核通过视频播客
     * @param videoId
     * @param modelMap
     * @param page
     * @return
     */
    @RequestMapping("/passBlogByManager")
    public String passBlogByManager(@NumberFormat Integer videoId, ModelMap modelMap,Integer page){
        blogService.changeBlogStatus(videoId,"NORMAL");
        modelMap.put("result","视频审核通过");
        modelMap.put("curBlogPage",page);
        return "pass/managerResult";
    }

    /**
     * 管理员冻结视频播客
     * @param videoId
     * @param modelMap
     * @param page
     * @return
     */
    @RequestMapping("/forzenBlogByManager")
    public String forzenBlogByManager(@NumberFormat Integer videoId, ModelMap modelMap,Integer page){
        blogService.changeBlogStatus(videoId,"FROZEN");
        modelMap.put("result","视频已被冻结");
        modelMap.put("curBlogPage",page);
        return "pass/managerResult";
    }

}
