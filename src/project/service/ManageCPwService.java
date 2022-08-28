package project.service;

import javafx.scene.Parent;

public interface ManageCPwService {
	
	// 비어있으면 진행 안되게
	public boolean isEmpty(String str); 
	
	// 비밀번호 확인
	public boolean comparePw(String pw1, String pw2);

	// <확인 버튼> 관리자 비번을 변경 (입력한 내용에 문제없는지 체크 후 수정)
	public void changeMPw(Parent root);
	

	
	

	
	
	
	







}