package project.service;

import project.controller.LoginController;
import project.Member;
import project.dao.MemberDAO;
import project.dao.MemberDAOImpl;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class MyResServiceImpl implements MyResService{
	private MemberDAO dao;
	private CommonService comServ;
	private LoginController loginController;
	
	public MyResServiceImpl() {
		dao = new MemberDAOImpl();
		comServ = new CommonServiceImpl();
		loginController = new LoginController();
	}

	// MyRes<내 진료예약 정보입력 페이지> [확인 버튼] 기능 : 입력 오류 체크 및 저장
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
			comServ.errorBox("날짜 입력오류","날짜가 입력되지 않았거나 잘못선택 되었습니다.","예약하실 날짜를 선택하세요.");
			return;
		}
		int time;
		if(tComboBox(root)) {
			time = getComboBoxTime(root);
			// 예약마감 시간이 선택됐을경우 time이 7으로 들어오므로 리턴시킴.
			if(time==7) {
				comServ.errorBox("시간 입력오류","마감된 시간으로 예약하셨습니다.","예약가능 시간으로 선택해주세요.");
				return;
			}
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
			Stage membershipForm = new Stage(); 
			root=comServ.showWindow(membershipForm, "../fxml/Mypage.fxml");
			membershipForm.setX(450);
			membershipForm.setY(110);

			// <마이페이지> 좌측 상단에 아이디 표기
			Label myPageId = (Label) root.lookup("#myPageId");
			myPageId.setText(id+"님 환영합니다!");
			
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
	
	// 콤보박스에서 선택한 내용을 숫자로 변경 (예약진료과)
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

	// 데이트피커로 선택받은 날짜를 스트링값으로 변경 (예약날짜)
	public String getDatePicker(Parent root) {

		DatePicker cmbDate = (DatePicker)root.lookup("#cmbDate");

		String value = cmbDate.getValue().toString();
		return value;
	}

	// 콤보박스에서 선택한 예약시간을 숫자로 변경 (예약시간)
	public int getComboBoxTime(Parent root) {
		ComboBox<String> cmbTime = (ComboBox<String>)root.lookup("#cmbTime");
		if(cmbTime==null) {
			return -1;
		}
		String value = cmbTime.getValue().toString();
		if(value.equals("예약가능: 09:30")) {
			return 1;
		} else if(value.equals("예약가능: 10:30")){
			return 2;
		}else if(value.equals("예약가능: 11:30")){
			return 3;
		}else if(value.equals("예약가능: 13:30")){
			return 4;
		}else if(value.equals("예약가능: 14:30")){
			return 5;
		}else if(value.equals("예약가능: 15:30")){
			return 6;
		}else {	// '예약마감: 시간' 인경우 7이 출력됨.
			return 7;
		}
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





	
	
	
	
	
	
	
	



	
	
	




}