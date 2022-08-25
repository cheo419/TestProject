package project.controller;

import java.net.URL;
import java.util.ResourceBundle;

import project.service.CommonServiceImpl;
import project.service.LoginServiceImpl;
import project.service.CommonService;
import project.service.LoginService;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.stage.Stage;

public class MyResController extends Controller implements Initializable{
	private Parent root;
	private LoginService loginServ;
	private CommonService commonServ;
	
	// <진료 예약 페이지> 로그인한 회원의 진료예약 (입력: 진료과_콤보박스, 진료날짜_데이트피커, 진료시간_콤보박스) (버튼: 확인, 취소)

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		loginServ = new LoginServiceImpl();
		commonServ = new CommonServiceImpl();
		
	}

	@Override
	public void setRoot(Parent root) {
		this.root = root;
	}
	

	// [확인 버튼] 진료예약(진료과,날짜,시간 선택) 입력한 후 최종 진료예약하는 버튼 
	public void resCheck() {
		loginServ.resCheck(root);
	}
	
	// [취소 버튼] 진료예약(진료과,날짜,시간 선택) 입력하던 중 취소 후 마이페이지로
	public void backMyPage() {
		// 진료예약페이지 닫힘
		Stage myPage = (Stage) root.getScene().getWindow();
		myPage.close();	
		
		// 마이페이지(진료예약,예약확인버튼 있는페이지) 다시 띄우기 
		Stage membershipForm = new Stage(); 
		root=commonServ.showWindow(membershipForm, "../fxml/Mypage.fxml");
	}

	
	
	






}
