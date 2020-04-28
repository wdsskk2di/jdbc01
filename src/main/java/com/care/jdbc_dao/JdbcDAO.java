package com.care.jdbc_dao;

//import java.sql.Connection;
//import java.sql.DriverManager;
import java.sql.PreparedStatement;
//import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;

import com.care.jdbc_dto.JdbcDTO;
import com.care.template.Constant;

public class JdbcDAO {
	/*
	private String driver = "oracle.jdbc.driver.OracleDriver";
	private String url = "jdbc:oracle:thin:@192.168.0.27:1521:xe";
	private String user="java", pwd="1234";
	private Connection con;
	private PreparedStatement ps;
	private ResultSet rs;
	*/
	private JdbcTemplate template;	//200428 두번째 추가
	
	public JdbcDAO() {
		
		this.template = Constant.template;	//200428 두번째 추가
		/*
		try {
			Class.forName(driver);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		*/
	}
	
	//목록보기
	public ArrayList<JdbcDTO> list() {
		String sql = "select * from test_jdbc order by id asc";
		
		//200428 두번째 추가
		ArrayList<JdbcDTO> list = (ArrayList<JdbcDTO>)template.query(sql, new BeanPropertyRowMapper<JdbcDTO>(JdbcDTO.class));
		
		return list;
		
		//또는				     값을 여러개 가져올때 query (sql 구문, 그 결과)
		//return (ArrayList<JdbcDTO>)template.query(sql, new BeanPropertyRowMapper<JdbcDTO>(JdbcDTO.class));
		//로도 표현 가능
		
		/*
		ArrayList<JdbcDTO> list = new ArrayList<JdbcDTO>();
		try {
			con = DriverManager.getConnection(url, user, pwd);
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			
			while (rs.next()) {
				JdbcDTO dto = new JdbcDTO();
				dto.setId(rs.getInt("id"));
				dto.setName(rs.getString("name"));
				list.add(dto);
			}
		} catch (Exception e) {	e.printStackTrace();}
		
		return list;
		*/
	}
	
	
	//저장하기
	public void save(final String id, final String name) {	//이동하는 도중 값이 변동될 수 있으므로 이전 방식과 달리 final을 붙여야 한다
		String sql = "insert into test_jdbc(id, name) values(?,?)";
		
		//200428 두번째 추가. sql이 완성된 쿼리문이면  template.update(sql); 한 줄로도 된다.
		//그렇지 않다면..  ?값을 넣어줘야한다
		template.update(sql, new PreparedStatementSetter() {
			
			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setString(1, id);
				ps.setString(2, name);
			}
		});
		
		//람다식 표현은 ps->{ps.setString(1,id); ps.setString(2, name);} 다만 람다식은 버전이 안맞으면 사용 못함
		
		/*
		try {
			con = DriverManager.getConnection(url, user, pwd);
			ps = con.prepareStatement(sql);
			ps.setString(1, id);	//String으로 넣기 가능. DB에서 숫자는 String, int 둘 다 된다
			//ps.setInt(1, Integer.parseInt(id)); int형에 맞춰 넣으려면..
			ps.setString(2, name);
			ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		*/
	}
	
	//수정하기 전 수정하려는 id가 있는지 확인
	public JdbcDTO modify(String id) {
		//200428 두번째 추가.
		String sql = "select * from test_jdbc where id="+id;
//		JdbcDTO dto = null;
		return template.queryForObject(sql, new BeanPropertyRowMapper<JdbcDTO>(JdbcDTO.class));
		//하나의 값을 가져올때 == queryForObject()
		
		/*
		String sql = "select * from test_jdbc where id=?";
		JdbcDTO dto = null;
		
		try {
			con = DriverManager.getConnection(url, user, pwd);
			ps = con.prepareStatement(sql);
			ps.setString(1, id);
			rs = ps.executeQuery();
			
			if(rs.next()) {
				dto = new JdbcDTO();
				dto.setId(rs.getInt("id"));
				dto.setName(rs.getString("name"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dto;
		*/
	}
	
	//수정하기
	public void modifySave(String id, String name) {
		//200428 두번째 추가.
		String sql = "update test_jdbc set name='"+name+"' where id="+id;
		template.update(sql);
		
		/*
		String sql = "update test_jdbc set name=? where id=?";
	
		try {
			con = DriverManager.getConnection(url, user, pwd);
			ps = con.prepareStatement(sql);
			ps.setString(1, name);
			ps.setString(2, id);
			ps.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		}
		*/
	}
	
	//삭제하기
	public void delete(String id) {
		//200428 두번째 추가.
		String sql = "delete from test_jdbc where id="+id;
		template.update(sql);
		
		/*
		String sql = "delete from test_jdbc where id=?";	
		try {
			con = DriverManager.getConnection(url, user, pwd);
			ps = con.prepareStatement(sql);
			ps.setString(1, id);
			ps.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		}
		*/
	}
}
