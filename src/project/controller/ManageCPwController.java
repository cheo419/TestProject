package project.controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.stage.Stage;
import project.service.CommonService;
import project.service.CommonServiceImpl;
import project.service.ManageCPwService;
import project.service.ManageCPwServiceImpl;


public class ManageCPwController extends Controller implements Initializable {
	private Parent root;
	private CommonService commonServ;
	private ManageCPwService manageCPwServ;
	
	//<관리자 비번경경 페이지> (버튼:확인, 취소)
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		commonServ = new CommonServiceImpl();
		manageCPwServ = new ManageCPwServiceImpl();
	}

	@Override
	public void setRoot(Parent root) {
		// TODO Auto-generated method stub
		this.root = root;
	}

	// [취소버튼] 
	public void cancleMPw() {
		// 현재 창을 관리자 비번입력창으로 변경
		Stage s = (Stage) root.getScene().getWindow();
		commonServ.showWindow(s, "../fxml/ManageLogin.fxml");
		s.setX(450);
		s.setY(110);
	}
	
	// <확인 버튼> 관리자 비번을 변경 (입력한 내용에 문제없는지 체크 후 수정)
	public void changeMPw() {
		
		manageCPwServ.changeMPw(root);
		
	}
	
	
	
	
	
	
	
	
}
