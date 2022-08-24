package project.controller;

import java.net.URL;
import java.util.ResourceBundle;

import project.service.CommonServiceImpl;
import project.service.LoginServiceImpl;
import project.dao.MemberDAO;
import project.dao.MemberDAOImpl;
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
	MemberDAO dao;
	
	@FXML private TextField mPw;
	@FXML private Button mBtn;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		loginServ = new LoginServiceImpl();
		commonServ = new CommonServiceImpl();
		dao = new MemberDAOImpl();
		
		// 패스워드 입력 후 확인버튼 포커스이동 엔터로 로그인
		mPw.setOnAction(e -> mBtn.requestFocus());
		mBtn.setOnKeyPressed(e -> loginServ.manageLogin(root));
	}

	@Override
	public void setRoot(Parent root) {
		this.root = root;
	}
	 
	
	
	
	// <관리자 화면> 로그인 버튼 (비밀번호 입력후 누르는 버튼)
	public void manageLogin() {
		loginServ.manageLogin(root);
	}
	// <관리자 로그인 화면(관리자 비밀번호 입력창)>에서 뒤로 가기(=로그인화면으로)
	public void backL() {
		Stage s = (Stage) root.getScene().getWindow();
		commonServ.showWindow(s, "../fxml/Login.fxml");
	}





}