package com.qinhan.videoblog.web;

import com.qinhan.videoblog.dal.model.User;
import com.qinhan.videoblog.service.UserService;
import com.qinhan.videoblog.web.common.SimpleDigestUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class LoginController {
	@Autowired
	UserService userService;
	
	@RequestMapping("/login")
	 public String login(HttpSession httpSession, String username, String password, ModelMap modelMap){
			//校验
		boolean flag=false;
			try{
				flag=userService.checkUserInfo(username, SimpleDigestUtil.encryptSHA(password));
			}catch (RuntimeException e){
				if(e.getMessage()!=null&&e.getMessage().equals("USERACCOUNT_FROZEN")){
					modelMap.put("result","您的账号已经被冻结，请联系管理员进行解冻处理！");
					return "pass/loginsuccess";
				}
			}
	        User user=userService.findUserByUsername(username);
	        //如果成功登陆则
	        if(flag){
	                //服务器几秒后跳转回登陆页面
	        	
	            //然后将用户信息和图片路径放到Session中
	            /*HttpSession session=httpServletRequest.getSession()*/;
	            httpSession.setAttribute("userInfo",user);
	            httpSession.setAttribute("username",user.getUsername());
	            //提示用户登录成功
				modelMap.addAttribute("result","登陆成功，欢迎您！");
	            return "pass/loginsuccess";
	        }
	        //提示用户登录失败
			modelMap.addAttribute("result","登陆失败,请重新登陆！");
	        return "pass/loginsuccess";
	    }
	 
	 @RequestMapping("/exit")
	    public String exit(HttpServletRequest request,ModelMap modelMap){
	        HttpSession session=request.getSession(false);
	        if(session!=null){
	            session.invalidate();
	            modelMap.put("result","用户退出登录");
	        }

		 return "pass/indexresult";
	    }
}
