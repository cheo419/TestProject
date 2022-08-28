package project.service;

import javafx.scene.Parent;

public interface InfoChangeService {
	
	// InfoChange<회원정보 수정 페이지> 에서 MyPage<마이페이지> [수정 버튼] 기능: 회원정보를 수정내용을 체크하고 저장됨
	public void change(Parent root);

	// InfoChange<회원정보 수정 페이지> 에서 Login<첫 기본 로그인페이지> [탈퇴 버튼] 기능: 회원정보를 모두 삭제시키고 로그인페이지로 다시 돌아감
	public void out(Parent root);

	// 로그인할때 입력한 아이디 가져옴
	public void setId(String id);

	// 입력창 비어있는것 확인
	public boolean isEmpty(String str);

	// 비밀번호 확인하기
	public boolean comparePw(String pw1, String pw2); 
	

}
