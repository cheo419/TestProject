package project.service;

import project.Member;
import project.DAO.MemberDAO;
import project.DAO.MemberDAOImpl;
import javafx.event.ActionEvent;
import javafx.scene.Parent;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LoginServiceImpl implements LoginService{
	MemberDAO dao;
	CommonService comServ;
	public LoginServiceImpl() {
		// TODO Auto-generated constructor stub
		dao = new MemberDAOImpl();
		comServ = new CommonServiceImpl();
	}
	
	// 로그인 버튼 눌렀을 때
	
	@Override
	public void LoginProc(Parent root) {
		// TODO Auto-generated method stub
		TextField idTxt = (TextField) root.lookup("#lId");
		PasswordField pwTxt = (PasswordField) root.lookup("#lPw");
		
		String id = idTxt.getText();
		String pw = pwTxt.getText();
		if(dao.checkLogin(id, pw)) {
			comServ.errorBox("로그인", "로그인 여부", "로그인에 성공했습니다.");
			Stage myPage = (Stage) root.getScene().getWindow();
			root = comServ.showWindow(myPage, "../Mypage.fxml");
			
//			myInfo(root, id);
		} else {
			comServ.errorBox("로그인", "로그인 여부", "로그인에 실패했습니다.");
		}
		
	}
	
	// 마이페이지 화면
	
	public void res(Parent root) {
		Stage membershipForm = new Stage();
		comServ.showWindow(membershipForm, "../MyRes.fxml");
	}
	
	// 진료과선택
	
	@Override
	public int myResJinryo(Parent root) {
		// TODO Auto-generated method stub
		ComboBox<String> cmbJinryo = (ComboBox<String>) root.lookup("#cmbJinryo");
		
		if(cmbJinryo == null) {
			return -1;
		}
		String value1 = cmbJinryo.getValue().toString();
		
		if(value1.equals("정형외과")) {
			return 1;
		} else if(value1.equals("이비인후과")) {
			return 2;
		} else if(value1.equals("내과")) {
			return 3;
		} else {
			return -2;
		}
		
	}
	
	// 진료시간선택
	
	@Override
	public int myResTime(Parent root) {
		// TODO Auto-generated method stub
		ComboBox<String> cmbTime = (ComboBox<String>) root.lookup("#cmbTime");
		
		if(cmbTime == null) {
			return -1;
		}
		String value2 = cmbTime.getValue().toString();
		if(value2.equals("09:30")) {
			return 1;
		} else if(value2.equals("10:30")) {
			return 2;
		} else if(value2.equals("11:30")) {
			return 3;
		} else if(value2.equals("13:30")) {
			return 4;
		} else if(value2.equals("14:30")) {
			return 5;
		} else if(value2.equals("15:30")) {
			return 6;
		} else {
			return -2;
		}
	}
	
	// 내 예약 출력
	
	public void resOk(Parent root) {
		Stage membershipForm = new Stage();
		comServ.showWindow(membershipForm, "../MyResPage.fxml");
	}
	
	// 로그아웃
	
	@Override
	public void Logout(Parent root) {
		// TODO Auto-generated method stub
		Stage s = (Stage) root.getScene().getWindow();
		comServ.showWindow(s, "../Login.fxml");
	}
	
	// 내 예약출력화면에서 확인하면 닫기
	
	@Override
	public void resCheck(ActionEvent event) {
		// TODO Auto-generated method stub
		comServ.WindowClose(event);
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
	public void manageLogin(Parent root) {
		// TODO Auto-generated method stub
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

	
	
}