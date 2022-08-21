package project.DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javafx.scene.control.TableView;
import project.Member;
import project.service.CommonService;
import project.service.CommonServiceImpl;

public class MemberDAOImpl implements MemberDAO {
	Connection con;
	CommonService comServ;
	
	// 오라클 연결
	
	public MemberDAOImpl() {
		// TODO Auto-generated constructor stub
		comServ = new CommonServiceImpl();
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			String url = "jdbc:oracle:thin:@127.0.0.1:1521:xe";
			String user = "system";
			String pass = "oracle";
			
			con = DriverManager.getConnection(url, user, pass);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	// 회원가입
	
	public boolean insertMember(Member m) {
		// TODO Auto-generated method stub
		try {
			String sql = "insert into firstmember values(?,?,?,?,?,?,?)";
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setString(1, m.getId());
			pstmt.setString(2, m.getPw());
			pstmt.setString(3, m.getUserName());
			pstmt.setString(4, m.getPhoneNumber());
			
			
			pstmt.setInt(5, m.getJinryo());
			pstmt.setString(6, m.getDate());
			pstmt.setInt(7, m.getTime());
			
			int result = pstmt.executeUpdate();

			if(result >= 1) {
				return true;
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return false;
	}

	// 로그인
	
	public boolean checkLogin(String id, String pw) {
		// TODO Auto-generated method stub
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
			// TODO: handle exception
			e.printStackTrace();
		}
		return false;
	}

	// 로그인 내용 저장
	
	public Member select(String id) {
		// TODO Auto-generated method stub
		try {
			String sql = "select * from firstmember where id=?";
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setString(1, id);
			
			ResultSet rs = pstmt.executeQuery();
			
			if(rs.next()) {
				Member m = new Member();
				m.setId(rs.getString("id"));
				m.setPw(rs.getString("pw"));
				m.setUserName(rs.getString("userName"));
				m.setPhoneNumber(rs.getString("phoneNumber"));
				
				m.setJinryo(rs.getInt("resJinryo"));
				m.setDate(rs.getString("resDate"));
				m.setTime(rs.getInt("resTime"));
				
				return m;
			}			
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return null;
	}

	// 예약
	
	@Override         
	public boolean insertRes(Member m) {
		// TODO Auto-generated method stub
		try {
			String sql = "update firstmember set "
					+ "resJinryo= ?,resDate=?,resTime=? "
					+ "where id=?;";
			PreparedStatement pstmt = con.prepareStatement(sql);
			
			pstmt.setInt(5, m.getJinryo());
			pstmt.setString(6, m.getDate());
			pstmt.setInt(7, m.getTime());
			
			int result = pstmt.executeUpdate();

			if(result >= 1) {
				return true;
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return false;
	}

	// 예약내역출력 (미완성)
	
	@Override
	public TableView<Member> selectAdmin() {
		// TODO Auto-generated method stub
		TableView<Member> memberView = new TableView<Member>();
		try {
			String sql = "select * from firstmember";
			PreparedStatement pstmt = con.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			
			while(rs.next()) {
				Member m = new Member();
				m.setId(rs.getString(1));
				m.setPw(rs.getString(2));
				m.setUserName(rs.getString(3));
				m.setPhoneNumber(rs.getString(4));
				m.setJinryo(rs.getInt(5));
				m.setDate(rs.getString(6));
				m.setTime(rs.getInt(7));
				
				memberView.getColumns().setAll();
			}
				
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return memberView;
		
	}
}
