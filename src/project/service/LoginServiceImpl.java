package project.service;

import project.controller.LoginController;
import project.Member;
import project.controller.MyResCheckController;
import project.dao.MemberDAO;
import project.dao.MemberDAOImpl;
import javafx.event.ActionEvent;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class LoginServiceImpl implements LoginService{
	MemberDAO dao;
	CommonService comServ;
	LoginController loginController;
	MyResCheckController myResCheckController;

	public LoginServiceImpl() {
		dao = new MemberDAOImpl();
		comServ = new CommonServiceImpl();
		loginController = new LoginController();
		myResCheckController = new MyResCheckController();
	}

	// 로그인 버튼 눌렀을 때
	@Override
	public void LoginProc(Parent root) {
		TextField idTxt = (TextField) root.lookup("#lId");
		PasswordField pwTxt = (PasswordField) root.lookup("#lPw");

		String id = idTxt.getText();
		String pw = pwTxt.getText();
		if(dao.checkLogin(id, pw)) {

			comServ.errorBox("로그인", "로그인 성공하였습니다.", id+"님 환영합니다.");
			System.out.println("로그인할때 입력한 아이디값:"+id);

			loginController.setId(id);	// 로그인할때 쓴 아이디값을 -> 로그인컨트롤러->되돌아와서 resCheck저장
			myResCheckController.setId(id);

			// 로그인 후 아이디비번쓰는 창 닫음
			Stage myPage = (Stage) root.getScene().getWindow(); 
			myPage.close();	

			// 마이페이지(진료예약,예약확인버튼 있는페이지) 다시 띄우기 (새창띄워서 버튼비활성화하기위함)
			Stage membershipForm = new Stage(); 
			root=comServ.showWindow(membershipForm, "../fxml/Mypage.fxml");

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
		comServ.showWindow(membershipForm, "../fxml/MyResCheck.fxml");
	}

	// 로그아웃
	@Override
	public void Logout(Parent root) {
		Stage s = (Stage) root.getScene().getWindow();
		comServ.showWindow(s, "../fxml/Login.fxml");
	}
	
	// 각 진료과(콤보박스),진료날짜(데이트피커),진료시간(콤보박스)에 입력값이 없으면 boolean으로 false 
	public boolean jComboBox(Parent root) {
		ComboBox<String> cmbJinryo = (ComboBox<String>) root.lookup("#cmbJinryo");
		
		if(cmbJinryo == null) {
			return false;
		} else if(cmbJinryo.getValue() == null) {
			cmbJinryo.requestFocus();
			return false;
		}
		return true;
	}
	public boolean datePicker(Parent root) {
		DatePicker cmbDate = (DatePicker)root.lookup("#cmbDate");
		if(cmbDate == null) {
			return false;
		} else if(cmbDate.getValue() == null) {
			cmbDate.requestFocus();
			return false;
		}
		return true;
	}
	public boolean tComboBox(Parent root) {
		ComboBox<String> cmbTime = (ComboBox<String>) root.lookup("#cmbTime");
		
		if(cmbTime == null) {
			return false;
		} else if(cmbTime.getValue() == null) {
			cmbTime.requestFocus();
			return false;
		}
		return true;
	}
	

	// 내 예약출력화면에서 확인하면 닫기
	public void resCheck(Parent root) {
		Member m = new Member();	// 진료예약정보를 저장하기위함.
		
		// 진료과,날짜,시간을 입력값이있을때 true를 보내주는 메서드 처리하고
		// 입력값이 있을때 입력값을 가져오는 메서드를 통해 값 저장.
		int jinryo;
		if(jComboBox(root)) {
			jinryo = getComboBoxJinryo(root);
		} else {
			comServ.errorBox("진료과 입력오류","진료과가 입력되지 않았습니다","예약하실 진료과를 선택하세요.");
			return;
		}
		String date;
		if(datePicker(root)) {
			date = getDatePicker(root);
		} else {
			comServ.errorBox("날짜 입력오류","날짜가 입력되지 않았습니다","예약하실 날짜를 선택하세요.");
			return;
		}
		int time;
		if(tComboBox(root)) {
			time = getComboBoxTime(root);
		} else {
			comServ.errorBox("시간 입력오류","시간이 입력되지 않았습니다","예약하실 시간을 선택하세요.");
			return;
		}
		
		// DB저장을 위해 Member클래스에 값 저장.
		m.setJinryo(jinryo);
		m.setDate(date);
		m.setTime(time);

			String id = loginController.getId(); // 로그인할때 썼던 아이디값을 받아옴.
			System.out.println("로그인시입력한 아이디값을 받아옴:"+id);

			m.setId(id);	// 로그인할때 썼던 아이디값을 Member id값으로 저장
			System.out.println("가져와서 member에저장된 id값 "+m.getId());

			// 진료 예약하기 (DB에 진료예약정보 저장되면 true)
			if(dao.insertRes(m)) {
				comServ.errorBox("진료예약", "진료예약성공", "진료예약이 정상적으로 이루어졌습니다.");

				// 진료예약성공 후 진료과,예약날짜,시간선택하는 창 닫음
				Stage myPage = (Stage) root.getScene().getWindow();
				myPage.close();	

				// 마이페이지(진료예약,예약확인버튼 있는페이지) 다시 띄우기 (새창띄워서 버튼비활성화하기위함)
				Stage membershipForm = new Stage(); ////////////////
				root=comServ.showWindow(membershipForm, "../fxml/Mypage.fxml");

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
		Stage s = (Stage) root.getScene().getWindow();
		comServ.showWindow(s, "../fxml/ManageLogin.fxml");
	}
	// 관리자 로그인 모든 예약 출력 화면
	@Override
	public void manageLogin(Parent root) {
		TextField mpw = (TextField) root.lookup("#mPw");

		String pw = mpw.getText();

		// 관리자 비밀번호
		if(pw.equals("1111")) {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setContentText("관리자로 로그인하셨습니다.");
			alert.showAndWait();

			Stage s = (Stage) root.getScene().getWindow();
			comServ.showWindow(s, "../fxml/Manage.fxml");
		} else {
			comServ.errorBox("관리자 로그인 실패","잘못 입력 되었습니다.","다시 시도해주세요.");
		}
	}
	// 관리자에서 로그인 화면으로
	@Override
	public void backLogin(Parent root) {
		Stage s = (Stage) root.getScene().getWindow();
		comServ.showWindow(s, "../fxml/Login.fxml");
	}





}