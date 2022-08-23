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
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.stage.Stage;

public class LoginController extends Controller implements Initializable{
	private Parent root;
	private LoginService loginServ;
	private CommonService commonServ;
	MemberDAO dao;
	static String id;	// 로그인할때 입력한 아이디값을 고정으로 저장하기위함.

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		loginServ = new LoginServiceImpl();
		commonServ = new CommonServiceImpl();
		dao = new MemberDAOImpl();
	}
	
	// 로그인할때 입력된 아이디값을 저장하고 되돌려줌.
	public void setId(String id) {
		this.id = id;
	}
	public String getId() {
		return id;
	}

	@Override
	public void setRoot(Parent root) {
		// TODO Auto-generated method stub
		this.root = root;
	}

	// 로그인 버튼
	public void LoginProc() {
		loginServ.LoginProc(root);
	}

	// 회원가입 화면 켜기
	public void signOk() {
		Stage membershipForm = new Stage();
		commonServ.showWindow(membershipForm, "../SignUp.fxml");
	}

	// 로그아웃
	public void Logout() {
		loginServ.Logout(root);
	}

	// 마이페이지 진료예약 및 콤보박스 저장
	public void res() {
		System.out.println(id);
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

	// 마이페이지 예약 (내용 입력 후 확인)
	public void resOk(ActionEvent event) {
		loginServ.resOk(root,event);
	}


	// 진료예약 확인 (입력된 내용 확인)
	public void resCheck(ActionEvent event) {
		loginServ.resCheck(root,event);
	}

	// 로그인 화면 관리자 버튼(로그인 비밀번호 쳐야하는 페이지 오픈)
	public void manageOk() {
		loginServ.manageOk(root);
	}

	// 관리자 화면 로그인 버튼 (비밀번호 입력후 누르는 버튼)
	public void manageLogin(ActionEvent event) {
		loginServ.manageLogin(root,event);
	}

	

}
