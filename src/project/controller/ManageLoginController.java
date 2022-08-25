package project.controller;

import java.net.URL;
import java.util.ResourceBundle;

import project.service.CommonServiceImpl;
import project.service.LoginServiceImpl;
import project.service.CommonService;
import project.service.LoginService;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ManageLoginController extends Controller implements Initializable{
	private Parent root;
	private LoginService loginServ;
	private CommonService commonServ;
	
	@FXML private TextField mPw;
	@FXML private Button mBtn;
	
	// <관리자 로그인 페이지> 관리자 로그인을 위해 비밀번호 입력하는 페이지 (입력: 비밀번호) (버튼:로그인,뒤로기가)

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		loginServ = new LoginServiceImpl();
		commonServ = new CommonServiceImpl();
		
		// 패스워드 입력 후 확인버튼 포커스이동 엔터로 로그인
		mPw.setOnAction(e -> mBtn.requestFocus());
		mBtn.setOnKeyPressed(e -> loginServ.manageLogin(root));
	}

	@Override
	public void setRoot(Parent root) {
		this.root = root;
	}
	 
	
	
	
	// [로그인 버튼] 
	public void manageLogin() {
		loginServ.manageLogin(root);
	}
	// [뒤로가기 버튼]
	public void backL() {
		Stage s = (Stage) root.getScene().getWindow();
		commonServ.showWindow(s, "../fxml/Login.fxml");
	}





}