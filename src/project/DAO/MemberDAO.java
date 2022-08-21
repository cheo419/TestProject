package project.DAO;

import javafx.scene.control.TableView;
import project.Member;

public interface MemberDAO {
	public boolean insertMember(Member m); // 회원가입
	
	public boolean checkLogin(String id, String pw); // 로그인
	
	public Member select(String id); // 로그인 내용 저장
	
	public boolean insertRes (Member m); // 예약
	
	public TableView<Member> selectAdmin(); // 예약출력 미완성
}
