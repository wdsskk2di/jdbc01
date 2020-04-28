package com.care.Controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.care.service.JdbcContentServiceImple;
import com.care.service.JdbcDeleteserviceImple;
import com.care.service.JdbcModifySaveserviceImple;
import com.care.service.JdbcModifyserviceImple;
import com.care.service.JdbcSaveServiceImple;
import com.care.service.JdbcService;
import com.care.template.Constant;

@Controller
public class MemberController {
	
	private JdbcService jdbc;
	
	//200428 두번째 추가
	public MemberController() {
		System.out.println("자동 실행");
		String config = "classpath:applicationJDBC.xml";
		GenericXmlApplicationContext ctx = new GenericXmlApplicationContext(config);
		JdbcTemplate template = ctx.getBean("template", JdbcTemplate.class);
		Constant.template = template;
	}	
	
	//목록보기
	@RequestMapping("content")
	public String content(Model model) {
		jdbc = new JdbcContentServiceImple();
		jdbc.execute(model);	//model값을 execute로 넘겨줌
		
		return "content";
	}
	
	//저장화면
	@RequestMapping("save_view")
	public String save_view() {
		return "save_view";
	}
		//DB연결 저장 메소드
	@RequestMapping("save")
	public String save(Model model, HttpServletRequest request) {
		model.addAttribute("request", request);
		jdbc = new JdbcSaveServiceImple();
		jdbc.execute(model);
		
		return "redirect:content";
	}
	
	//수정화면
	@RequestMapping("modify")
	public String modify(Model model, HttpServletRequest request) {
		model.addAttribute("request", request);
		jdbc = new JdbcModifyserviceImple();
		jdbc.execute(model);
		
		return "modify";
	}
	
		//DB연결 수정 메소드
	@RequestMapping("modifySave")
	public String modifySave(Model model, HttpServletRequest request) {
		model.addAttribute("request", request);
		jdbc = new JdbcModifySaveserviceImple();
		jdbc.execute(model);
		
		return "redirect:content";
	}
	
	//삭제
	@RequestMapping("delete")
	public String delete(Model model, HttpServletRequest request) {
		model.addAttribute("request", request);
		jdbc = new JdbcDeleteserviceImple();
		jdbc.execute(model);
		
		return "redirect:content";
	}
}
