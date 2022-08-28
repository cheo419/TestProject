package project.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import project.Admin;

public class AdminDAOImpl implements AdminDAO {
	private Connection con;

	// 오라클 연결
	public AdminDAOImpl() {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			String url = "jdbc:oracle:thin:@127.0.0.1:1521:xe";
			String user = "system";
			String pass = "oracle";

			con = DriverManager.getConnection(url, user, pass);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 관리자 비밀번호 가져오기
	@Override
	public Admin select() {
		Admin a = new Admin();
		try {
			String sql = "select * from firstAdmin";
			PreparedStatement pstmt = con.prepareStatement(sql);

			ResultSet rs = pstmt.executeQuery();

			if(rs.next()) {
				a.setMPw(rs.getString("mPw"));
			}			
		}catch (Exception e) {
			e.printStackTrace();
		}
		return a;
	}
	
	// 관리자 비밀번호 변경하기
	@Override
	public boolean updateMPw(Admin a) {
		try {
	         String sql = 
	         "update firstAdmin set mPw=? where mPw = ?";
	         PreparedStatement pstmt = con.prepareStatement(sql);

	         pstmt.setString(1, a.getNewMPw());
	         pstmt.setString(2, a.getMPw());
	         

	         int result = pstmt.executeUpdate();

	         if(result >= 1) {
	            return true;
	         }

	      } catch (Exception e) {
	         e.printStackTrace();
	      }
	      return false;
	  }
	

}
	
	
	
	
	
