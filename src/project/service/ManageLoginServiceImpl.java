package project.service;

import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class ManageLoginServiceImpl implements ManageLoginService{
	private CommonService comServ;

	public ManageLoginServiceImpl() {
		comServ = new CommonServiceImpl();
	}

	// ManageLogin<관리자로그인 페이지> 에서 Manage<관리자 페이지> 
	@Override
	public void manageLogin(Parent root) {
		TextField mpw = (TextField) root.lookup("#mPw");

		String pw = mpw.getText();

		// 관리자 비밀번호
		if(pw.equals("1111")) {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setContentText("관리자로 로그인하셨습니다.");
			alert.showAndWait();

			Stage s = (Stage) root.getScene().getWindow();
			comServ.showWindow(s, "../fxml/Manage.fxml");
			s.setX(450);
			s.setY(110);
		} else {
			comServ.errorBox("관리자 로그인 실패","잘못 입력 되었습니다.","다시 시도해주세요.");
		}
	}

	
	
	

	
	
	
	
	
	
	
	
	
	
	
	



	
	
	




}