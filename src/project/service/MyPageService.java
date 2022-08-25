package project.service;

import javafx.event.ActionEvent;
import javafx.scene.Parent;

public interface MyPageService {
	
	// MyPage<마이 페이지>에서 MyRes<진료예약 정보 입력페이지>
	public void res(Parent root); 
	
	// MyPage<마이페이지> 에서 MyResCheck<내 예약정보 보기 페이지>
	public void resOk(Parent root,ActionEvent event); 
	
	// MyPage<마이페이지> 에서 Login<로그인 페이지>
	public void Logout(Parent root); 
	
	
	
	
	
	
	

	
	
	
	
	

	
	
}