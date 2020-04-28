package com.care.jdbc_dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import com.care.jdbc_dto.JdbcDTO;

public class JdbcDAO {
	private String driver = "oracle.jdbc.driver.OracleDriver";
	private String url = "jdbc:oracle:thin:@192.168.0.27:1521:xe";
	private String user="java", pwd="1234";
	private Connection con;
	private PreparedStatement ps;
	private ResultSet rs;
	
	public JdbcDAO() {
		try {
			Class.forName(driver);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	//목록보기
	public ArrayList<JdbcDTO> list() {
		String sql = "select * from test_jdbc";
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
		
	}
	
	
	//저장하기
	public void save(String id, String name) {
		String sql = "insert into test_jdbc(id, name) values(?,?)";
		
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
	}
	
	//수정하기 전 수정하려는 id가 있는지 확인
	public JdbcDTO modify(String id) {
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
	}
	
	//수정하기
	public void modifySave(String id, String name) {
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
	}
}
