package project;

import java.net.URL;
import java.util.ResourceBundle;

import project.service.CommonServiceImpl;
import project.service.LoginServiceImpl;
import project.DAO.MemberDAO;
import project.DAO.MemberDAOImpl;
import project.service.CommonService;
import project.service.LoginService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LoginPageController extends Controller implements Initializable{
	private Parent root;
	private LoginService loginServ;
	private CommonService commonServ;
	MemberDAO dao;
	static String id;	// 로그인할때 입력한 아이디값을 고정으로 저장하기위함.
	
	@FXML private TextField lId;
	@FXML private PasswordField lPw;
	@FXML private Button loginBtn;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		loginServ = new LoginServiceImpl();
		commonServ = new CommonServiceImpl();
		dao = new MemberDAOImpl();
		
		// 아이디입력 후 엔터치면 패스워드 입력칸으로 포커스이동
		lId.setOnAction(e -> lPw.requestFocus());
		lPw.setOnAction(e -> loginBtn.requestFocus());
		loginBtn.setOnKeyPressed(e -> LoginProc());
	}

	@Override
	public void setRoot(Parent root) {
		this.root = root;
	}
	
	// 로그인할때 입력된 아이디값을 저장하고 되돌려줌.
	public void setId(String id) {
		this.id = id;
	}
	public String getId() {
		return id;
	}

	// <기본 로그인페이지>로그인 버튼
	public void LoginProc() {
		loginServ.LoginProc(root);
	}

	// <기본 로그인페이지>회원가입 화면 켜기
	public void signOk() {
		Stage membershipForm = new Stage();
		commonServ.showWindow(membershipForm, "../SignUp.fxml");
	}

	// <기본 로그인 화면> 관리자 버튼(로그인 비밀번호 쳐야하는 페이지 오픈)
	public void manageOk() {
		loginServ.manageOk(root);
	}
	
	



}