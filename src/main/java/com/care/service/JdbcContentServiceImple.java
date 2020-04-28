package com.care.service;

import java.util.ArrayList;

import org.springframework.ui.Model;

import com.care.jdbc_dao.JdbcDAO;
import com.care.jdbc_dto.JdbcDTO;

public class JdbcContentServiceImple implements JdbcService {

	@Override
	public void execute(Model model) {
		JdbcDAO dao = new JdbcDAO();
		ArrayList<JdbcDTO> list = dao.list();	//list라는 변수에 = dao의 list메소드 실행 결과 저장
		model.addAttribute("list", list);	//list라는 Attribute에 list변수 값 저장
		
		//추가
		model.addAttribute("count", dao.count());
	}

}
