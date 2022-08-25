package project.controller;

import java.net.URL;
import java.util.ResourceBundle;

import project.dao.MemberDAO;
import project.dao.MemberDAOImpl;
import project.Member;
import project.service.CommonService;
import project.service.CommonServiceImpl;
import project.service.SignUpService;
import project.service.SignUpServiceImpl;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class SignUpController extends Controller implements Initializable{
	private Parent root;
	private CommonService commonServ;
	private SignUpService signUpServ;
	private MemberDAO dao;
	
	private Member member;	// 입력되는 회원정보 저장
	
	@FXML private TextField lId;
	@FXML private PasswordField lPw;
	@FXML private PasswordField lPwOk;
	@FXML private TextField lName;
	@FXML private TextField lNumber;
	@FXML private Button signUpOk;
	
	// <회원가입 페이지> (입력: 아이디,비밀번호,비밀번호확인,이름,전화번호) (버튼: 확인,취소)

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		member = new Member();
		commonServ = new CommonServiceImpl();
		signUpServ = new SignUpServiceImpl();
		dao = new MemberDAOImpl();
		
		// 회원가입시 텍스트 입력칸에서 엔터 입력시 다음 입력칸으로 포커스 이동. 마지막엔 확인버튼 눌림.
		lId.setOnAction(e -> lPw.requestFocus());
		lPw.setOnAction(e -> lPwOk.requestFocus());
		lPwOk.setOnAction(e -> lName.requestFocus());
		lName.setOnAction(e -> lNumber.requestFocus());
		lNumber.setOnAction(e -> signUpOk.requestFocus());
		signUpOk.setOnKeyPressed(e -> memberShipProc());
	}
	
	@Override
	public void setRoot(Parent root) {
		this.root = root;
	}

	// [확인 버튼] 입력칸 모두 입력 후 회원가입하는 버튼
	public void memberShipProc() {

		TextField txtId = (TextField) root.lookup("#lId");
		PasswordField txtPw = (PasswordField) root.lookup("#lPw");
		PasswordField txtPwOk = (PasswordField) root.lookup("#lPwOk");
		TextField txtName = (TextField) root.lookup("#lName");
		TextField txtNumber = (TextField) root.lookup("#lNumber");
		
		txtId.requestFocus();

		String[] txtEmpty = 
			{txtId.getText(), 
			txtPw.getText(),
			txtPwOk.getText(),
			txtName.getText(),
			txtNumber.getText()
			};
		String[] txtEmptyName = {"아이디","비밀번호","비밀번호 확인","이름","전화번호"};

		for(int i=0;i<txtEmpty.length;i++) {
			if(signUpServ.isEmpty(txtEmpty[i])) {
				commonServ.errorBox("회원가입 오류",txtEmptyName[i] + " 입력창이 비어 있습니다","회원가입이 되지 않았습니다. 다시 시도 해주세요.");
				return;
			}
		}

		String id = txtId.getText();
		String pw = txtPw.getText();
		String pwOk = txtPwOk.getText();
		
		// 회원가입시에 같은 아이디가있는지 검색 (동일한 아이디 있으면 true)
		if(dao.findSameId(id)) {
			commonServ.errorBox("회원가입 오류","동일한 아이디로 회원이 존재합니다.","다른 아이디로 시도해주세요.");
			txtId.requestFocus();
			return;
		}

		// 입력한 암호가 다를경우 
		if(signUpServ.comparePw(pw, pwOk)){
			commonServ.errorBox("회원가입 오류","입력하신 암호가 다릅니다.");
			txtPw.requestFocus();
			return;
		}

		member.setId(txtId.getText());
		member.setPw(txtPw.getText());
		member.setUserName(txtName.getText());
		member.setPhoneNumber(txtNumber.getText());

		if(dao.insertMember(member)) {
			commonServ.errorBox("회원가입", "회원가입성공", "회원가입이 정상적으로 이루어졌습니다.");
		} else {
			commonServ.errorBox("DB 에러", "DB 문제 발생", "DB 입력에 문제가 발생했습니다.");
			return;
		}
		// 회원가입 정보 입력 창 닫기
		Stage stage = (Stage) root.getScene().getWindow();
		stage.close();
	}

	// [취소 버튼] 회원가입 정보 입력 도중 취소
	public void memberShipCancle() {
		Stage stage = (Stage) root.getScene().getWindow();
		stage.close();
	}





















}
