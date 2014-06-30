package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class RegDao {
	String oraID = "system";
	String oraPASS = "123456";
	String oraURL = "jdbc:oracle:thin:@localhost:1521:XE";
	Connection con;
	
	public void con() {
		try {
			//1. 드라이브 로드
			Class.forName("oracle.jdbc.driver.OracleDriver");
			//2 컨넥션 객체 선언
			con = DriverManager.getConnection(oraURL, oraID, oraPASS);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	// 안드로이드로부터 넘어온 regid 값 저장 메소드
	public void insertRegid(String regid, String phoneid) {
		// 기존에 phoneid가 있는지를 알아내는 변수
		int num=0;
		String sql;
		PreparedStatement stmt;
		try{
			// 커넥션 연결
			con();
			// 기존 phoneid가 있는지 확인
			sql = "select count(*) from gcm where phoneid = ?";
			stmt = con.prepareStatement(sql);
			stmt.setString(1, phoneid);
			// 쿼리 실행후 결과 리턴
			ResultSet rs = stmt.executeQuery();
			if(rs.next()) {
				num = rs.getInt(1); // 기존에 폰 아이디가 존재 한다면
			}
			
			// 새로운 폰 아이디 등록
			if (num ==0) {
				// 쿼리 준비
				sql = "insert into gcm values (?, ?)";
				// 쿼리를 실행 시켜주는 객체 선언
				stmt = con.prepareStatement(sql);
				// ?에 값 대입
				stmt.setString(1, phoneid);
				stmt.setString(2, regid);
			} else {
				// 쿼리 준비
				sql = "update gcm set regid=? where phoneid=?";
				// 쿼리를 실행 시켜주는 객체 선언
				stmt = con.prepareStatement(sql);
				// ?에 값 대입
				stmt.setString(1, regid);
				stmt.setString(2, phoneid);
			}
			
			// 쿼리 실행
			stmt.executeUpdate();
			// 자원 반납
			stmt.close();
			con.close();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	//모든 회원의 gcm등록 id값을 얻어와서 리턴해 주는 메소드
	public ArrayList<String> getAllRegid() {
		// 모든 회원의 정보를 저장하는 객체 생성
		ArrayList<String> array = new ArrayList<>();
		try {
			// 데이터 베이스 연결
			con();
			// 쿼리 준비
			String sql = "select regid from gcm";
			// Statement 객체 생성
			PreparedStatement stmt = con.prepareStatement(sql);
			// 결과 얻어옴
			ResultSet rs = stmt.executeQuery();
			// 결과를 리스트에 넣어줌
			while(rs.next()) {
				array.add(rs.getString(1));
			}
			// 자원 반납
			rs.close();
			stmt.close();
			con.close();
		} catch(Exception e) {
			e.printStackTrace();
		}
		return array;
		
	}
}
