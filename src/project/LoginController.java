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
import javafx.scene.control.DatePicker;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LoginController extends Controller implements Initializable{
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
		
//		// 아이디입력 후 엔터치면 패스워드 입력칸으로 포커스이동
//		lId.setOnAction(e -> lPw.requestFocus());
//		
//		lPw.setOnAction(e -> loginBtn.requestFocus());
//		
//		loginBtn.setOnKeyPressed(e -> LoginProc());
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

	// <마이페이지> 로그아웃
	public void Logout() {
		loginServ.Logout(root);
	}

	// <마이페이지> 진료예약 버튼 (진료과,날짜,시간 선택하는 페이지를 띄우는 버튼)
	public void res() {
		System.out.println(id);
		Stage myPage = (Stage) root.getScene().getWindow();
		myPage.close();	
		Stage membershipForm = new Stage();
		Parent form = commonServ.showWindow(membershipForm, "../MyRes.fxml");
		String[] items = {"정형외과", "이비인후과", "내과"};

		ComboBox<String> cmbjinryo = (ComboBox<String>) form.lookup("#cmbJinryo");
		for(String item:items) {
			cmbjinryo.getItems().add(item);
		}

		String[] items1 = {"09:30", "10:30", "11:30", "13:30", "14:30", "15:30"};
		ComboBox<String> cmbtime = (ComboBox<String>) form.lookup("#cmbTime");

		for(String item1 : items1) {
			cmbtime.getItems().add(item1);
		}
	}

	// <마이페이지> 예약 (내용 입력 후 확인)
	public void resOk(ActionEvent event) {
		loginServ.resOk(root,event);
	}


	// <내 진료예약(진료과,날짜,시간 선택) 화면> 확인버튼(진료예약하기) 
	public void resCheck() {
		loginServ.resCheck(root);
	}

	// <시작 로그인 화면> 관리자 버튼(로그인 비밀번호 쳐야하는 페이지 오픈)
	public void manageOk() {
		loginServ.manageOk(root);
	}

	// <관리자 화면> 로그인 버튼 (비밀번호 입력후 누르는 버튼)
	public void manageLogin(ActionEvent event) {
		loginServ.manageLogin(root,event);
	}

	// <관리자 로그인 화면(관리자 비밀번호 입력창)>에서 뒤로 가기(=로그인화면으로)
	public void backL() {
		Stage s = (Stage) root.getScene().getWindow();
		commonServ.showWindow(s, "../Login.fxml");
	}

	// <내 진료예약(진료과,날짜,시간 선택) 화면> 에서 취소하면 마이페이지로
	public void backMyPage() {
		// 진료예약페이지 닫힘
		Stage myPage = (Stage) root.getScene().getWindow();
		myPage.close();	

		// 마이페이지(진료예약,예약확인버튼 있는페이지) 다시 띄우기 
		Stage membershipForm = new Stage(); 
		root=commonServ.showWindow(membershipForm, "../Mypage.fxml");
	}




}
