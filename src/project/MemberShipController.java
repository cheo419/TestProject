package project;

import java.net.URL;
import java.util.ResourceBundle;

import project.DAO.MemberDAO;
import project.DAO.MemberDAOImpl;
import project.service.CommonService;
import project.service.CommonServiceImpl;
import project.service.MemberShipService;
import project.service.MemberShipServiceImpl;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class MemberShipController extends Controller implements Initializable{
	private Parent root;
	private CommonService comServ;
	private MemberShipService membershipServ;
	private MemberDAO dao;

	@Override
	public void setRoot(Parent root) {
		// TODO Auto-generated method stub
		this.root = root;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		comServ = new CommonServiceImpl();
		membershipServ = new MemberShipServiceImpl();
		dao = new MemberDAOImpl();
	}

	// signup 회원가입 화면 확인 버튼
	
	public void memberShipProc() {
		Member member = new Member();
		TextField txtId = (TextField) root.lookup("#lId");
		PasswordField txtPw = (PasswordField) root.lookup("#lPw");
		PasswordField txtPwOk = (PasswordField) root.lookup("#lPwOk");
		TextField txtName = (TextField) root.lookup("#lName");
		TextField txtNumber = (TextField) root.lookup("#lNumber");

		String[] txtEmpty = {txtId.getText(), 
				txtPw.getText()};
		String[] txtEmptyName = {"이름","아이디","암호"};

		for(int i=0;i<txtEmpty.length;i++) {
			if(membershipServ.isEmpty(txtEmpty[i])) {
				comServ.errorBox(txtEmptyName[i] + " 입력창이 비어 있습니다");
				return;
			}
		}

		String pw = txtPw.getText();
		String pwOk = txtPwOk.getText();

		if(membershipServ.comparePw(pw, pwOk)){
			comServ.errorBox("암호가 다릅니다.");
			return;
		}


		member.setId(txtId.getText());
		member.setPw(txtPw.getText());
		member.setUserName(txtName.getText());
		member.setPhoneNumber(txtNumber.getText());


		if(dao.insertMember(member)) {
			comServ.errorBox("회원가입", "회원가입성공", "회원가입이 정상적으로 이루어졌습니다.");
		} else {
			comServ.errorBox("DB 에러", "DB 문제 발생", "DB 입력에 문제가 발생했습니다.");
			return;
		}

		Stage stage = (Stage) root.getScene().getWindow();
		stage.close();

	}


}