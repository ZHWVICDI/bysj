package com.qinhan.videoblog.web;

import com.qinhan.videoblog.dal.model.User;
import com.qinhan.videoblog.service.UserService;
import com.qinhan.videoblog.web.common.SimpleDigestUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;


@Controller
public class RegisterController {
	
	@Autowired
    UserService userService;

    /**
     * 跳转到注册页面
     * @return
     */
    @RequestMapping("/toRegister")
    public String toRegister(){
        return "registration";
    }

	@RequestMapping("/register")
	public String register(@Valid @ModelAttribute User user, Errors errors, ModelMap modelMap) {

        //对用户注册的信息进行校验 ，使用校验器来做。
                //这里设置用户默认的状态为NORMAL
            user.setState("NORMAL");
            user.setPassword(SimpleDigestUtil.encryptSHA(user.getPassword()));
            /*这里默认一个头像*/
            user.setHeadingUrl("default.jpg");
        //如果用户已经存在则注册失败并反馈给用户,如果用户不存在，则准许注册
        boolean isExists= userService.isUserNameExist(user.getUsername());

        if (!isExists) {
            userService.registerUser(user);
            //如果成功，则提示注册成功然后跳转到中间页面，然后几秒后跳转回首页，或者点击返回首页
            return "pass/registationsuccess";
        }
        //注册失败会带回用户信息，以便用户不用再次填写
        modelMap.addAttribute("userinfo",user);
        modelMap.addAttribute("result","注册失败，请重新注册");
        return "registration";
    }
    @RequestMapping("/userIsExist")
    @ResponseBody
    public boolean userIsExist(String username){
	    if(userService.isUserNameExist(username)){
	        return false;
	    }
	    return true;

    }
}
