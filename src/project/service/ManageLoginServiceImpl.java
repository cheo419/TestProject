package project.service;

import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import project.Admin;
import project.dao.AdminDAO;
import project.dao.AdminDAOImpl;

public class ManageLoginServiceImpl implements ManageLoginService{
	private CommonService comServ;
	private AdminDAO aDao;

	public ManageLoginServiceImpl() {
		comServ = new CommonServiceImpl();
		aDao = new AdminDAOImpl();
	}

	// ManageLogin<관리자로그인 페이지> 에서 Manage<관리자 페이지> 
	@Override
	public void manageLogin(Parent root) {
		TextField mpw = (TextField) root.lookup("#mPw");
		// 입력받은 비밀번호
		String pw = mpw.getText();
		System.out.println("입력받은 비밀번호 :"+pw);
		// DB에서 비밀번호 가져와서 ADMIN에 저장하여 불러옴
		Admin a = new Admin();
		a=aDao.select();	
		String mPw = a.getMPw();
		
		System.out.println("db에서 가져온 비밀번호 : "+mPw);

		// 관리자 비밀번호 확인 
		if(pw.equals(mPw)) {
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