package project.controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import project.Member;
import project.dao.MemberDAO;
import project.dao.MemberDAOImpl;
import project.service.CommonService;
import project.service.CommonServiceImpl;
import project.service.InfoChangeService;
import project.service.InfoChangeServiceImpl;

public class InfoChangeController extends Controller implements Initializable{
	private Parent root;
	private CommonService commonServ;
	private InfoChangeService infoChangeServ;
	private MemberDAO dao;
	private static String id;
	private String name;
	private String phoneNumber;

	private @FXML Label lId;
	private @FXML TextField lPw;
	private @FXML TextField lPwOk;
	private @FXML TextField lName;
	private @FXML TextField lNumber;
	private @FXML Button modify;

	// <회원정보 수정 페이지> 아이디제외 모든 정보 수정, 탈퇴가능한 페이지 (입력:비밀번호,비밀번호확인,이름,전화번호) (버튼:수정,취소,탈퇴)

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		commonServ = new CommonServiceImpl();
		infoChangeServ = new InfoChangeServiceImpl();
		dao = new MemberDAOImpl();

		// 창 떴을때 비번 입력칸에 포커스 주고 엔터치면서 수정하고 마지막에 엔터로 수정버튼 눌림.
		lPw.requestFocus();
		lPw.setOnAction(e -> lPwOk.requestFocus());
		lPwOk.setOnAction(e -> lName.requestFocus());
		lName.setOnAction(e -> lNumber.requestFocus());
		lNumber.setOnAction(e -> modify.requestFocus());
		modify.setOnKeyPressed(e -> change());

		// 회원정보를 가져오기위해 메서드 처리 ( 회원정보 수정페이지에 이름, 전화번호는 미리 채워두기위함)
		getInfo(id);

		// 아이디는 수정하지 않을것이므로 라벨로 기존 아이디값을 출력
		lId.setText(id);

		// 정보 조회된 이름 전화번호를 텍스트필드에 채움
		lName.setText(name);
		lNumber.setText(phoneNumber);
	}

	// 회원정보에대한 모든 정보를 불러와서 Member클래스에 저장
	private void getInfo(String id) {
		// 불러온 정보를 멤버 클래스에 저장. 수정창에 이름 전화번호는 기존 정보로 띄우기위함.
		Member m= dao.select(id);
		name = m.getUserName();
		phoneNumber = m.getPhoneNumber();
	}

	@Override
	public void setRoot(Parent root) {
		this.root = root;
	}

	// [수정 버튼] 수정할 정보 입력 후 누르는 버튼
	public void change() {
		System.out.println("정보 입력 후 수정버튼을 눌렀습니다.");
		infoChangeServ.change(root);
	}

	// [탈퇴 버튼] 탈퇴를 선택함 회원정보를 지우고 로그인페이지로 돌아감
	public void out() {
		System.out.println("탈퇴 버튼을 눌렀습니다.");
		infoChangeServ.out(root);
	}

	// [뒤로가기 버튼] 수정을 취소하고 다시 마이페이지로 돌아감
	public void backagain() {
		commonServ.errorBox("회원정보 수정 취소", "회원정보 수정을 취소했습니다.", "마이페이지로 돌아갑니다.");
		System.out.println("뒤로가기 버튼을 눌렀습니다. 마이페이지로 돌아갑니다.");

		// <수정페이지> 창을 끄고 <마이페이지> 창을 다시 띄움
		Stage myPage = (Stage) root.getScene().getWindow();
		myPage.close();	

		Stage s = new Stage();
		root=commonServ.showWindow(s, "../fxml/MyPage.fxml");
		s.setX(450);
		s.setY(110);

		// <마이페이지> 좌측 상단에 아이디 표기
		Label myPageId = (Label) root.lookup("#myPageId");
		myPageId.setText(id+"님 환영합니다!");

		// 예약된 내역이있는지 boolean으로 체크하고 버튼비활성화 처리
		if(dao.checkRes(id)){
			System.out.println("예약내용있음");
			Button myResBtn = (Button) root.lookup("#myResBtn");
			myResBtn.setDisable(true);
		}
	}

	public static String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}





}
