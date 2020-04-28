package com.care.Controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.care.service.JdbcContentServiceImple;
import com.care.service.JdbcSaveServiceImple;
import com.care.service.JdbcService;

@Controller
public class MemberController {
	
	private JdbcService jdbc;
	
	@RequestMapping("content")
	public String content(Model model) {
		jdbc = new JdbcContentServiceImple();
		jdbc.execute(model);	//model값을 execute로 넘겨줌
		
		return "content";
	}
	
	@RequestMapping("save_view")
	public String save_view() {
		return "save_view";
	}
	
	@RequestMapping("save")
	public String save(Model model, HttpServletRequest request) {
		model.addAttribute("request", request);
		jdbc = new JdbcSaveServiceImple();
		jdbc.execute(model);
		
		return "redirect:content";
	}
}
