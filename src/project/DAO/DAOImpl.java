package project.DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import project.Member;

public class DAOImpl implements DAO {
	
	Connection con;
	
	public DAOImpl() {
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

	// 회원가입
	@Override
	public boolean insertMember(Member m) {
		try {
			String sql = "insert into firstmember (userName,phoneNum,id,pw) values(?,?,?,?)";
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setString(1, m.getName());
			pstmt.setString(2, m.getPhoneNum());
			pstmt.setString(3, m.getId());
			pstmt.setString(4, m.getPw());
			
			int result = pstmt.executeUpdate();

			if(result >= 1) {
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	// 예약
	@Override
	public boolean insertRes(Member m) {
		try {
			String sql = "update firstmember set "
					+ "resDepart= ?,resDate=?,resTime=? "
					+ "where id=?;";
			PreparedStatement pstmt = con.prepareStatement(sql);
			
			pstmt.setInt(1, m.getDepart());
			pstmt.setString(2, m.getDate());
			pstmt.setInt(3, m.getTime());
			pstmt.setString(4, m.getId());
			
			int result = pstmt.executeUpdate();

			if(result >= 1) {
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	// 아이디 페스워드 확인
	@Override
	public boolean checkLogin(String id, String pw) {
		try {
			String sql = "select count(*) from firstmember where id=? and pw=?";
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setString(1, id);
			pstmt.setString(2, pw);
			
			ResultSet rs = pstmt.executeQuery();
			
			rs.next();
			int result = rs.getInt(1);
			if(result == 1) {
				return true;
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	// 유저 예약내역
	@Override
	public Member selectUser(String id) {
		try {
			String sql = "select * from firstmember where id=?";
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setString(1, id);
			
			ResultSet rs = pstmt.executeQuery();
			
			if(rs.next()) {
				Member m = new Member();
				m.setName(rs.getString("name"));
				m.setPhoneNum(rs.getString("phoneNum"));
				m.setId(rs.getString("id"));
				m.setPw(rs.getString("pw"));
				m.setDepart(rs.getInt("depart"));
				m.setDate(rs.getString("date"));
				m.setTime(rs.getInt("time"));
				return m;
			}			
		}catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	// 관리자 예약내역
	@Override
	public List<Member> selectAdmin() {
		List<Member> memberList = new ArrayList<Member>();
		try {
			String sql = "select * from firstmember";
			PreparedStatement pstmt = con.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			
			while(rs.next()) {
				Member m = new Member();
				m.setName(rs.getString(1));
				m.setPhoneNum(rs.getString(2));
				m.setId(rs.getString(3));
				m.setPw(rs.getString(4));
				m.setDepart(rs.getInt(5));
				m.setDate(rs.getString(6));
				m.setTime(rs.getInt(7));
				
				memberList.add(m);
			}			
		}catch (Exception e) {
			e.printStackTrace();
		}
		return memberList;
	}

	
	

}
