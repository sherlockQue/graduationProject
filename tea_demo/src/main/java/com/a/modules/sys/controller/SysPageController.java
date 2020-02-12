
package com.a.modules.sys.controller;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.a.common.core.ControllerAide;
import com.a.common.core.R;
import com.google.code.kaptcha.Constants;
import com.google.code.kaptcha.Producer;

/**
 * 
 * 系统页面视图，管理员登陆
 *
 */
@Controller
public class SysPageController {
	
	@Autowired
	private Producer producer;
	
	@GetMapping("captcha.jpg")
	public void captcha(HttpServletResponse response)throws IOException {
        response.setHeader("Cache-Control", "no-store, no-cache");
        response.setContentType("image/jpeg");

        //生成文字验证码
        String text = producer.createText();
        //生成图片验证码
        BufferedImage image = producer.createImage(text);
        //保存到shiro session
        ControllerAide.setSessionAttribute(Constants.KAPTCHA_SESSION_KEY, text);
        
        ServletOutputStream out = response.getOutputStream();
        ImageIO.write(image, "jpg", out);
	}
	
	/**
	 * 登录
	 */
	@ResponseBody
	@PostMapping("/sys/login")
	public R login(String username, String password, String captcha) {
		Object kaptcha = ControllerAide.getKaptcha(Constants.KAPTCHA_SESSION_KEY);
		if(kaptcha==null){
			return R.error("验证码为空");
		}
		if(!captcha.equalsIgnoreCase(kaptcha.toString())){
			return R.error("验证码不正确");
		}
	 
		//保留一个
//		//验证码通过后，再删除
//		Collection<Object> c=ControllerAide.getSession().getAttributeKeys();
//	    for(Object o:c) {
//	    	System.out.println(o+","+ControllerAide.getSession().getAttribute(o));
//	    }
		
		
		
			Subject subject = ControllerAide.getSubject();
			UsernamePasswordToken token = new UsernamePasswordToken(username, password);
			subject.login(token);
		 
	    
		return R.ok();
	}
	
	/**
	 * 退出
	 */
	@GetMapping("logout")
	public String logout() {
		ControllerAide.logout();
		return "redirect:login";
	}
	
	
	
	
	@GetMapping("modules/{module}/{url}")
	public String module(@PathVariable("module") String module, @PathVariable("url") String url){
		return "modules/" + module + "/" + url;
	}

	@GetMapping(value = {"/", "index"})
	public String index(){
		return "index";
	}

 

	@GetMapping("login")
	public String login(){
		return "login";
	}

	@GetMapping("main")
	public String main(){
		return "main";
	}

 
}
