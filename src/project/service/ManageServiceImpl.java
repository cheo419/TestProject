package project.service;

import javafx.scene.Parent;
import javafx.stage.Stage;

public class ManageServiceImpl implements ManageService{
	private CommonService comServ;

	public ManageServiceImpl() {
		comServ = new CommonServiceImpl();
	}

	/// Manage<관리자 페이지>에서 Login<로그인 페이지>
	@Override
	public void backLogin(Parent root) {
		Stage s = (Stage) root.getScene().getWindow();
		comServ.showWindow(s, "../fxml/Login.fxml");
		s.setX(300);
		s.setY(80);
	}


	
	
	
	
	
	
	
	
	
	
	
	



	
	
	




}