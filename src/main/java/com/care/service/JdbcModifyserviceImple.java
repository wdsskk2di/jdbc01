package com.care.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.ui.Model;

import com.care.jdbc_dao.JdbcDAO;

public class JdbcModifyserviceImple implements JdbcService {

	@Override
	public void execute(Model model) {
		Map<String, Object> map = model.asMap();	//model.asMap() == model을 map형태로 변환
		HttpServletRequest request = (HttpServletRequest)map.get("request");	//map형태를 request로 변환
		String id = request.getParameter("id");	//id 파람 값 가져옴
		
		JdbcDAO dao = new JdbcDAO();
		model.addAttribute("list", dao.modify(id)); //DB연결해서 modify 메소드 실행
	}

}
