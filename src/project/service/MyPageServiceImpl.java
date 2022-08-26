package project.service;

import javafx.scene.Parent;
import javafx.stage.Stage;

public class MyPageServiceImpl implements MyPageService{
	private CommonService comServ;

	public MyPageServiceImpl() {
		comServ = new CommonServiceImpl();
	}

	// MyPage<마이 페이지>에서 MyRes<진료예약 정보 입력페이지>
	public void res(Parent root) {
		Stage membershipForm = new Stage();
		comServ.showWindow(membershipForm, "../MyRes.fxml");
	}

	// MyPage<마이페이지> 에서 MyResCheck<내 예약정보 보기 페이지>
	public void resOk(Parent root) {
		// 내 예약내역확인 페이지 클릭하면 마이페이지 창 닫음
		// 혹시 예약내역확인페이지에서 예약내역삭제하면 마이페이지 다시띄워서
		// 예약하기 버튼 활성화를 위함.
		Stage myPage = (Stage) root.getScene().getWindow();
		myPage.close();	

		Stage membershipForm = new Stage();
		comServ.showWindow(membershipForm, "../fxml/MyResCheck.fxml");
		membershipForm.setX(300);
		membershipForm.setY(80);
	}

	// MyPage<마이페이지> 에서 Login<로그인 페이지>
	@Override
	public void Logout(Parent root) {
		Stage s = (Stage) root.getScene().getWindow();
		comServ.showWindow(s, "../fxml/Login.fxml");
		s.setX(300);
		s.setY(80);
	}


	
	
	
	
	
	
	
	

	
	

	
	
	
	
	
	
	
	
	
	
	
	



	
	
	




}