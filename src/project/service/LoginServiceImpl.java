package project.service;

import project.LoginController;
import project.Member;
import project.MyResController;
import project.DAO.MemberDAO;
import project.DAO.MemberDAOImpl;
import javafx.event.ActionEvent;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LoginServiceImpl implements LoginService{
	MemberDAO dao;
	CommonService comServ;
	LoginController loginController;
	MyResController myResController;

	public LoginServiceImpl() {
		dao = new MemberDAOImpl();
		comServ = new CommonServiceImpl();
		loginController = new LoginController();
		myResController = new MyResController();
	}

	// 로그인 버튼 눌렀을 때
	@Override
	public void LoginProc(Parent root) {
		TextField idTxt = (TextField) root.lookup("#lId");
		PasswordField pwTxt = (PasswordField) root.lookup("#lPw");

		String id = idTxt.getText();
		String pw = pwTxt.getText();
		if(dao.checkLogin(id, pw)) {

			comServ.errorBox("로그인", "로그인 여부", "로그인에 성공했습니다.");
			System.out.println("로그인할때 입력한 아이디값:"+id);

			loginController.setId(id);	// 로그인할때 쓴 아이디값을 -> 로그인컨트롤러->되돌아와서 resCheck저장
			myResController.setId(id);

			// 로그인 후 아이디비번쓰는 창 닫음
			Stage myPage = (Stage) root.getScene().getWindow(); 
			myPage.close();	

			// 마이페이지(진료예약,예약확인버튼 있는페이지) 다시 띄우기 (새창띄워서 버튼비활성화하기위함)
			Stage membershipForm = new Stage(); 
			root=comServ.showWindow(membershipForm, "../Mypage.fxml");

			// 예약된 내역이있는지 boolean으로 체크하고 버튼비활성화 처리
			if(dao.checkRes(id)){
				System.out.println("예약내용있음");
				Button myResBtn = (Button) root.lookup("#myResBtn");
				myResBtn.setDisable(true);
			}
		} else {
			comServ.errorBox("로그인", "로그인 여부", "로그인에 실패했습니다.");
		}

	}

	// 콤보박스 (예약진료과)
	public int getComboBoxJinryo(Parent root) {
		ComboBox<String> cmbJinryo = (ComboBox<String>)root.lookup("#cmbJinryo");
		if(cmbJinryo==null) {
			return -1;
		}

		String value = cmbJinryo.getValue().toString();
		if(value.equals("정형외과")) {
			return 1;
		} else if(value.equals("이비인후과")){
			return 2;
		}else if(value.equals("내과")){
			return 3;
		}else {
			return 4;
		}
	}

	// 데이트피커 (예약날짜)
	public String getDatePicker(Parent root) {

		DatePicker cmbDate = (DatePicker)root.lookup("#cmbDate");

		String value = cmbDate.getValue().toString();
		return value;
	}

	// 콤보박스 (예약시간)
	public int getComboBoxTime(Parent root) {

		ComboBox<String> cmbTime = (ComboBox<String>)root.lookup("#cmbTime");
		if(cmbTime==null) {
			return -1;
		}

		String value = cmbTime.getValue().toString();
		if(value.equals("09:30")) {
			return 1;
		} else if(value.equals("10:30")){
			return 2;
		}else if(value.equals("11:30")){
			return 3;
		}else if(value.equals("13:30")){
			return 4;
		}else if(value.equals("14:30")){
			return 5;
		}else if(value.equals("15:30")){
			return 6;
		}else {
			return 7;
		}
	}


	// 마이페이지 화면
	public void res(Parent root) {
		Stage membershipForm = new Stage();
		comServ.showWindow(membershipForm, "../MyRes.fxml");
	}

	// 내 예약내역보기 
	public void resOk(Parent root,ActionEvent event) {
		// 내 예약내역확인 페이지 클릭하면 마이페이지 창 닫음
		// 혹시 예약내역확인페이지에서 예약내역삭제하면 마이페이지 다시띄워서
		// 예약하기 버튼 활성화를 위함.
		Stage myPage = (Stage) root.getScene().getWindow();
		myPage.close();	
		
		Stage membershipForm = new Stage();
		comServ.showWindow(membershipForm, "../MyResPage.fxml");
	}

	// 로그아웃
	@Override
	public void Logout(Parent root) {
		Stage s = (Stage) root.getScene().getWindow();
		comServ.showWindow(s, "../Login.fxml");
	}

	// 내 예약출력화면에서 확인하면 닫기
	public void resCheck(Parent root) {
		System.out.println("loginController.getId():"+loginController.getId());
		String id = loginController.getId(); // 로그인할때 썼던 아이디값을 받아옴.

		System.out.println("로그인시입력한 아이디값을 받아옴:"+id);

		Member m = new Member();
		m.setJinryo(getComboBoxJinryo(root));
		m.setDate(getDatePicker(root));
		m.setTime(getComboBoxTime(root));
		m.setId(id);	// 로그인할때 썼던 아이디값을 Member id값으로 저장

		System.out.println("가져와서 member에저장된 id값 "+m.getId());

		System.out.println(m.getJinryo());
		System.out.println(m.getDate());
		System.out.println(m.getTime());
		System.out.println(id);		

		if(dao.insertRes(m)) {
			comServ.errorBox("진료예약", "진료예약성공", "진료예약이 정상적으로 이루어졌습니다.");

			// 진료예약성공 후 진료과,예약날짜,시간선택하는 창 닫음
			Stage myPage = (Stage) root.getScene().getWindow();
			myPage.close();	

			// 마이페이지(진료예약,예약확인버튼 있는페이지) 다시 띄우기 (새창띄워서 버튼비활성화하기위함)
			Stage membershipForm = new Stage(); ////////////////
			root=comServ.showWindow(membershipForm, "../Mypage.fxml");

			// 예약된 내역이있는지 boolean으로 체크하고 버튼비활성화 처리
			if(dao.checkRes(id)){
				System.out.println("예약내용있음");
				Button myResBtn = (Button) root.lookup("#myResBtn");
				myResBtn.setDisable(true);
			}

		} else {
			comServ.errorBox("DB 에러", "DB 문제 발생", "DB 입력에 문제가 발생했습니다.");
			return;
		}

	}
	// 관리자 비밀번호창 열기
	@Override
	public void manageOk(Parent root) {
		// TODO Auto-generated method stub
		Stage s = (Stage) root.getScene().getWindow();
		comServ.showWindow(s, "../ManagePw.fxml");
	}

	// 관리자 로그인 모든 예약 출력 화면
	@Override
	public void manageLogin(Parent root,ActionEvent event) {
		TextField mpw = (TextField) root.lookup("#mPw");

		String pw = mpw.getText();

		// 관리자 비밀번호
		if(pw.equals("1111")) {
			Stage s = (Stage) root.getScene().getWindow();
			comServ.showWindow(s, "../ManagePage.fxml");
		} else {
			comServ.errorBox("관리자 로그인 실패");
		}
	}

	// 관리자에서 로그인 화면으로
	@Override
	public void backLogin(Parent root) {
		Stage s = (Stage) root.getScene().getWindow();
		comServ.showWindow(s, "../Login.fxml");
	}





}