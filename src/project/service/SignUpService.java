package project.service;

import javafx.scene.Parent;

public interface SignUpService {
	
	// 비어있으면 진행 안되게
	public boolean isEmpty(String str); 
	
	// 비밀번호 확인
	public boolean comparePw(String pw1, String pw2);

	// 동일한아이디로 가입된게 있는지 중복체크
	public void sameCheck(Parent root);


}