package project.controller;

import java.net.URL;
import java.util.ResourceBundle;

import project.service.CommonServiceImpl;
import project.service.MyPageService;
import project.service.MyPageServiceImpl;
import project.service.CommonService;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;

public class MyPageController extends Controller implements Initializable{
	private Parent root;
	private MyPageService myPageServ;
	private CommonService commonServ;
	
	// <마이페이지> (버튼: 진료예약, 예약확인, 로그아웃)

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		myPageServ = new MyPageServiceImpl();
		commonServ = new CommonServiceImpl();
		
	}

	@Override
	public void setRoot(Parent root) {
		this.root = root;
	}

	// [로그아웃 버튼]
	public void Logout() {
		myPageServ.Logout(root);
	}
	
	// [진료예약 버튼] (진료과,날짜,시간 선택하는 페이지를 띄우는 버튼)
	public void res() {
		Stage myPage = (Stage) root.getScene().getWindow();
		myPage.close();	
		Stage membershipForm = new Stage();
		root = commonServ.showWindow(membershipForm, "../fxml/MyRes.fxml");
		

		

	}
	// [예약확인 버튼]
	public void resOk() {
		myPageServ.resOk(root);
	}

	
	
	
	



	
	
	






}