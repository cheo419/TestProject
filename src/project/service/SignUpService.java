package project.service;


public interface SignUpService {
	
	// 비어있으면 진행 안되게
	public boolean isEmpty(String str); 
	
	// 비밀번호 확인
	public boolean comparePw(String pw1, String pw2); 

}