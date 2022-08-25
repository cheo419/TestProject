package project.service;

import project.controller.LoginController;
import project.controller.MyResCheckController;
import project.dao.MemberDAO;
import project.dao.MemberDAOImpl;
import javafx.scene.Parent;
import javafx.stage.Stage;

public class ManageServiceImpl implements ManageService{
	MemberDAO dao;
	CommonService comServ;
	LoginController loginController;
	MyResCheckController myResCheckController;

	public ManageServiceImpl() {
		dao = new MemberDAOImpl();
		comServ = new CommonServiceImpl();
		loginController = new LoginController();
		myResCheckController = new MyResCheckController();
	}

	/// Manage<관리자 페이지>에서 Login<로그인 페이지>
	@Override
	public void backLogin(Parent root) {
		Stage s = (Stage) root.getScene().getWindow();
		comServ.showWindow(s, "../fxml/Login.fxml");
	}


	
	
	
	
	
	
	
	
	
	
	
	



	
	
	




}