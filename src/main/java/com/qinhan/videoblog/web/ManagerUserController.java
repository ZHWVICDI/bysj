package com.qinhan.videoblog.web;

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

@Controller
public class ManagerUserController {
    @Autowired
    UserService userService;
    @Autowired
    BlogService blogService;
    @Autowired
    CategoryService categoryService;
    @RequestMapping("/toUserList")
    public String toUserPage(ModelMap modelMap, @NumberFormat Integer page, Integer size){
        if(page==null){
            page=1;
        }
        size=10;
        Page<User> usersPage=null;

        usersPage=userService.getAllUsers(page,size);
        modelMap.put("usersPage",usersPage);
        return "manager/managerUserList";
    }

    @RequestMapping("/deleteUserByManager")
    public String deleteUser(@NumberFormat Integer userId, ModelMap modelMap){
        //删除博客信息
        userService.deleteUserById(userId);
        //请求重新渲染博客页面
        modelMap.put("result","删除用户成功");
        modelMap.put("userId",userId);
        return "pass/managerDetleteUser";
    }

    @RequestMapping("/passUser")
    public String passUser(@NumberFormat Integer userId, ModelMap modelMap,Integer page){
        userService.changeUserState(userId,"NORMAL");
        modelMap.put("result","用户"+userId+"已被解冻！");
        modelMap.put("curBlogPage",page);
        return "pass/managerUserResult";
    }
    @RequestMapping("/frozenUser")
    public String frozenUser(@NumberFormat Integer userId, ModelMap modelMap,Integer page){
        userService.changeUserState(userId,"FROZEN");
        modelMap.put("result","用户"+userId+"已被冻结！");
        modelMap.put("curBlogPage",page);
        return "pass/managerUserResult";
    }


}
