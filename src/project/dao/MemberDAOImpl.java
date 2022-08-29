package project.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import project.Member;

public class MemberDAOImpl implements MemberDAO {
	private Connection con;

	// 오라클 연결
	public MemberDAOImpl() {
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
	//	 반드시 firstmemberRes에도 아이디를 insert 해줘야함.
	public boolean insertMember(Member m) {
		try {
			String sql = "insert into firstmember values(?,?,?,?)";
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setString(1, m.getUserName());
			pstmt.setString(2, m.getPhoneNumber());
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

	// 로그인시 아이디 비밀번호 확인 : 맞으면 true
	public boolean checkLogin(String id, String pw) {
		try {
			String sql = "select count(*) from firstmember where id=? and pw=?";
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setString(1, id);
			pstmt.setString(2, pw);

			ResultSet rs = pstmt.executeQuery();

			rs.next();
			int result = rs.getInt(1);
			if(result >= 1) {
				return true;
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	// 입력된 아이디에 해당하는 이름, 전화번호, 비밀번호 가져옴.
	public Member select(String id) {
		Member m = new Member();
		try {
			String sql = "select * from firstmember where id=?";
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setString(1, id);

			ResultSet rs = pstmt.executeQuery();

			if(rs.next()) {
				m.setUserName(rs.getString("userName"));
				m.setPhoneNumber(rs.getString("phoneNum"));
				m.setId(rs.getString("id"));
				m.setPw(rs.getString("pw"));
			}			
		}catch (Exception e) {
			e.printStackTrace();
		}
		return m;
	}

	// 진료 예약하기
	@Override         
	public boolean insertRes(Member m) {
		try {
			String sql = "insert into firstmemberRes values (?,?,?,?)";
			PreparedStatement pstmt = con.prepareStatement(sql);

			pstmt.setInt(1, m.getJinryo());
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

	// 관리자 모든회원정보 출력 (모든 회원정보 불러오기위해 리스트 사용)
	// 날짜순서대로 출력됨.
	@Override
	public List<Member> selectAdmin() {
		List<Member> memberList = new ArrayList<Member>();	// 모든회원정보 List
		try {
			String sql = "select m.userName, m.phoneNum, m.id, m.pw, r.resJinryo, r.resDate, r.resTime from firstmember m, firstmemberRes r where m.id=r.id(+) order by resDate";
			PreparedStatement pstmt = con.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();

			while(rs.next()) {
				Member m = new Member();
				m.setUserName(rs.getString(1));
				m.setPhoneNumber(rs.getString(2));
				m.setId(rs.getString(3));
				m.setPw(rs.getString(4));

				if(rs.getInt(5)!=0) {
					m.setJinryo(rs.getInt(5));
				}
				Date date1 = rs.getDate(6);
				if(date1 != null) {
					m.setDate(rs.getString(6));
				}
				if(rs.getInt(7)!=0) {
					m.setTime(rs.getInt(7));
				}

				if(m.getDate() == null) {	// 진료예약내용이없으면(date값 없으면)
					m.setRes("예약내역 없음");	// 진료예약내용에 "예약내역없음" 출력
				}else {						// 진료예약내용 있으면(date값이 있으면)
					String depart = null;
					switch(m.getJinryo()) {
					case 1:
						depart="정형외과";
						break;
					case 2:
						depart="이비인후과";
						break;
					case 3:
						depart="내과";
						break;
					}

					StringBuffer date = new StringBuffer(rs.getString(6));
					date.setLength(10);

					String time = null;
					switch(m.getTime()) {
					case 1:
						time="9시 30분";
						break;
					case 2:
						time="10시 30분";
						break;
					case 3:
						time="11시 30분";
						break;
					case 4:
						time="13시 30분";
						break;
					case 5:
						time="14시 30분";
						break;
					case 6:
						time="15시 30분";
						break;
					}
					m.setRes("진료과 : "+depart 	// Res에 진료과,날짜,시간 내용합쳐서저장.
							+" / 날짜 : "+date
							+" / 시간 : "+time);
				}
				memberList.add(m);				// 모든 회원정보 리스트에 저장.
			}			
		}catch (Exception e) {
			e.printStackTrace();
		}
		return memberList;
	}

	//  예약여부확인 (예약하기 버튼 비활성화,예약내역 삭제시) : 예약내역있으면 true
	@Override
	public boolean checkRes(String id) {
		try {
			//			String sql = "select count(*) from (select m.userName, m.phoneNum, m.id, m.pw, r.resJinryo, r.resDate, r.resTime from firstmember m, firstmemberRes r where m.id=r.id) group by id having id=?";
			String sql = "select count(*) from firstmemberRes where id=?";
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setString(1, id);

			ResultSet rs = pstmt.executeQuery();

			rs.next();
			int result = rs.getInt(1);
			if(result >= 1) {
				return true;
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	// <관리자페이지>테이블뷰에서 선택된 회원 강제탈퇴 , <회원정보수정 페이지> 정보수정페이지에서 탈퇴
	// 반드시 예약내역 삭제가 선행되어야함.(primary key 때문에)
	@Override
	public boolean deleteUser(String id) {
		try {
			String sql = "delete from firstmember where id=?";
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setString(1, id);

			int result = pstmt.executeUpdate();
			if(result >= 1) {
				return true;
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	// <관리자페이지> 테이블뷰에서 선택된 회원의 모든 예약내역 삭제
	// 회원강제탈퇴시에만 사용함
	@Override
	public boolean deleteUserResAll(String id) {
		try {
			String sql = "delete from firstmemberRes where id=?";
			PreparedStatement pstmt = con.prepareStatement(sql);

			pstmt.setString(1, id);

			int result = pstmt.executeUpdate();

			if(result >= 1) {
				return true;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	// <관리자페이지,유저페이지> 테이블뷰에서 선택된 예약내역만 삭제
	@Override
	public boolean deleteResSelect(String id,int resJinryo) {
		try {
			String sql = "delete from firstmemberRes where id=? and resJinryo=?";
			PreparedStatement pstmt = con.prepareStatement(sql);

			pstmt.setString(1, id);
			pstmt.setInt(2, resJinryo);

			int result = pstmt.executeUpdate();

			if(result >= 1) {
				return true;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	// 회원가입시에 같은 아이디가있는지 검색 : 동일한 아이디 있으면 true
	@Override
	public boolean findSameId(String id) {
		try {
			String sql = "select count(*) from firstmember where id=?";
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setString(1, id);

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

	// 진료 가능여부 확인. : 예약 가능시 true
	@Override
	public boolean findSameRes(int value1,String value2,int value3) {
		try {
			String sql = "select count(*) from firstmemberRes where resJinryo=? and resDate=? and resTime=?";
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, value1);
			pstmt.setString(2, value2);
			pstmt.setInt(3, value3);

			ResultSet rs = pstmt.executeQuery();

			rs.next();
			int result = rs.getInt(1);
			if(result >= 1) {
				return false;
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}

	// 회원 정보 수정
	public boolean updateInfo(Member m, String id) {
		try {
			String sql = 
					"update firstmember set userName=?, phoneNum=?, "
							+ "pw=? where id = ?";
			PreparedStatement pstmt = con.prepareStatement(sql);

			pstmt.setString(1, m.getUserName());
			pstmt.setString(2, m.getPhoneNumber());
			pstmt.setString(3, m.getPw());

			pstmt.setString(4, id);

			int result = pstmt.executeUpdate();

			if(result >= 1) {
				return true;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	// 입력된 아이디에 해당하는 모든 예약정보 출력
	// 예약된 내역이없으면 아무것도 출력안됨.
	@Override
	public List<Member> selectIdRes(String id) {
		List<Member> memberList = new ArrayList<Member>();	// 모든회원예약정보 List
		try {
			String sql = "select m.userName, m.phoneNum, m.id, m.pw, r.resJinryo, r.resDate, r.resTime from firstmember m, firstmemberRes r where m.id=r.id and m.id=?";
			PreparedStatement pstmt = con.prepareStatement(sql);

			pstmt.setString(1, id);

			ResultSet rs = pstmt.executeQuery();

			while(rs.next()) {
				Member m = new Member();
				m.setUserName(rs.getString(1));
				m.setPhoneNumber(rs.getString(2));
				m.setId(rs.getString(3));
				m.setPw(rs.getString(4));

				if(rs.getInt(5)!=0) {
					m.setJinryo(rs.getInt(5));
				}
				Date date1 = rs.getDate(6);
				if(date1 != null) {
					m.setDate(rs.getString(6));
				}
				if(rs.getInt(7)!=0) {
					m.setTime(rs.getInt(7));
				}
				String depart = null;
				switch(m.getJinryo()) {
				case 1:
					depart="정형외과";
					break;
				case 2:
					depart="이비인후과";
					break;
				case 3:
					depart="내과";
					break;
				}

				StringBuffer date = new StringBuffer(rs.getString(6));
				date.setLength(10);

				String time = null;
				switch(m.getTime()) {
				case 1:
					time="9시 30분";
					break;
				case 2:
					time="10시 30분";
					break;
				case 3:
					time="11시 30분";
					break;
				case 4:
					time="13시 30분";
					break;
				case 5:
					time="14시 30분";
					break;
				case 6:
					time="15시 30분";
					break;
				}
				m.setRes("진료과 : "+depart 	// Res에 진료과,날짜,시간 내용합쳐서저장.
						+" / 날짜 : "+date
						+" / 시간 : "+time);

				memberList.add(m);				// 모든 회원정보 리스트에 저장.
			}			
		}catch (Exception e) {
			e.printStackTrace();
		}
		return memberList;
	}

	// 해당진료과로 예약내역이 있는지 확인 (추가진료예약시에 사용) 없으면 true
	public boolean noJinryo(String id, int resJinryo) {
		try {
			String sql = "select count(*) from firstmemberRes where id=? and resJinryo=?";
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setString(1, id);
			pstmt.setInt(2, resJinryo);

			ResultSet rs = pstmt.executeQuery();

			rs.next();
			int result = rs.getInt(1);
			if(result >= 1) {
				return false;
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}







}
