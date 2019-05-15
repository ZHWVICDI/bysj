package com.qinhan.videoblog.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class JumpController {
	@RequestMapping("/toRegister")
	public String toRegister(){
		return "registration";
	}
}
