package com.qinhan.videoblog.web;

import com.qinhan.videoblog.dal.model.Blog;
import com.qinhan.videoblog.dal.model.User;
import com.qinhan.videoblog.service.BlogService;
import com.qinhan.videoblog.service.UserService;
import com.qinhan.videoblog.web.common.VideoConstants;
import com.qinhan.videoblog.web.modelvo.UserModifyForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static javax.swing.text.html.CSS.getAttribute;

@Controller
public class PersonController {
    @Autowired
    UserService userService;
    @Autowired
    BlogService blogService;

    /*person跳转页面*/
    @RequestMapping("/toPerson")
    public String toPerson(HttpSession httpSession, ModelMap modelMap) {
        String username = (String) httpSession.getAttribute("username");
        if (username == null) {
            modelMap.put("result", "用户未登陆，请登陆后再进入个人中心");
            return "pass/indexresult";
        }
        User user = userService.findUserByUsername(username);
        modelMap.put("userinfo", user);
        return "person/personal";
    }

    @RequestMapping("/toMyStars")
    public String toMyStars() {
        return "person/table-font-list";
    }

    // TODO: 2019/5/10 上传图片博客
    @RequestMapping("/toUploadView")
    public String toUpload() {
        return "person/form-line";
    }

    /**
     * 跳转到个人中心界面
     *
     * @param request
     * @param modelMap
     * @return
     */
    @RequestMapping("/toPersonInfo")
    public String toPersonInfo(HttpServletRequest request, ModelMap modelMap) {
        //判断用户是否已经登录，如果没有登陆，则提示用户登陆，并返回首页---这里出现了很严重的代码重复问题
        User user = (User) request.getSession().getAttribute("userInfo");
        String username = (String) request.getSession().getAttribute("username");
        // TODO: 2019/5/16 有空余时间将这里更改，我将user存入到了session中，这里根本不需要再取一次
        if (user != null && username != null) {
            //如果已经登录则跳转到该页，将session中的userInfo信息填到里面去。
            User userdetail = userService.findUserByUsername(username);
            modelMap.addAttribute("userDetail", userdetail);
            return "person/form-amazeui";
        }

        modelMap.addAttribute("result", "请先登录后再查看个人中心");
        return "pass/indexresult";
    }

    @RequestMapping("/toModifyHead")
    public String toModifyHead() {
        return "person/modifyHead";
    }

    /**
     * 修改用户个人信息
     *
     * @param request
     * @param userModifyForm
     * @param modelMap
     * @return
     */
    @RequestMapping("/modifyInfo")
    public String modifyInfo(HttpServletRequest request, UserModifyForm userModifyForm, ModelMap modelMap) {
        //校验 如果有则返回页面，并将错误信息带回去
        String username = (String) request.getSession().getAttribute("username");
        if (username == null) {
            modelMap.addAttribute("result", "请先登录后再查看个人中心");
            return "pass/indexresult";
        }
        //调用service层进行用户修改业务
        try {
            userService.modifyUserInfo(userModifyForm, username);
        } catch (RuntimeException e) {
            String msg = null;
            if (e.getMessage().equals("USER_NOTFOUND")) {
                msg = "用户未找到";
                modelMap.addAttribute("result", msg);
                return "person/form-amazeui";
            } else if (e.getMessage().equals("MODIFY_ERROR")) {
                msg = "用户修改失败";
                modelMap.addAttribute("result", msg);
                return "person/form-amazeui";
            }
        }
        modelMap.addAttribute("result", "用户修改成功");
        User userdetail = userService.findUserByUsername(username);
        /*System.out.println(userdetail.getUserAbout());*/
        modelMap.addAttribute("userDetail", userdetail);
        return "person/form-amazeui";
    }

    @RequestMapping("/modifyHeading")
    public String modifyHeading(HttpSession session, MultipartFile headingUrl, ModelMap modelMap) {
        String username = (String) session.getAttribute("username");
        String savePath = VideoConstants.HEADING_PIC_ORIGIN_PATH;
        User user = userService.updateHeadingUrl(headingUrl, savePath, username);
        if (user!=null) {
            session.setAttribute("userInfo",user);
            modelMap.put("result", "头像修改成功");
        }
        return "person/modifyHead";
    }

}
