package project.service;

import project.Member;
import project.controller.InfoChangeController;
import project.controller.LoginController;
import project.controller.MyResCheckController;
import project.controller.MyResController;
import project.dao.MemberDAO;
import project.dao.MemberDAOImpl;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LoginServiceImpl implements LoginService{
	private MemberDAO dao;
	private CommonService comServ;
	private LoginController loginController;
	private MyResController myResController;
	private MyResCheckController myResCheckController;
	private InfoChangeService infoChangeService;
	private InfoChangeController infoChangeController;

	public LoginServiceImpl() {
		dao = new MemberDAOImpl();
		comServ = new CommonServiceImpl();
		loginController = new LoginController();
		myResController = new MyResController();
		myResCheckController = new MyResCheckController();
		infoChangeService = new InfoChangeServiceImpl();
		infoChangeController = new InfoChangeController();
	}

	// Login<첫 기본로그인 페이지> 에서 MyPage<마이페이지> [로그인 버튼] 기능: 아이디 비밀번호 입력오류 체크
	@Override
	public void LoginProc(Parent root) {
		TextField idTxt = (TextField) root.lookup("#lId");
		PasswordField pwTxt = (PasswordField) root.lookup("#lPw");

		String id = idTxt.getText();
		String pw = pwTxt.getText();
		

		
		// 입력받은 아이디 비밀번호로 회원이 있는지 조회하여 로그인
		if(dao.checkLogin(id, pw)) {

			// 로그인할때 쓴 아이디값을 필요한 클래스로 보내줌
			loginController.setId(id);
			myResController.setId(id);
			myResCheckController.setId(id);
			infoChangeService.setId(id);
			infoChangeController.setId(id);
			
			// 마이페이지 좌상단에 이름을 출력해주기위해서 이름을 가져옴.
			Member m = dao.select(id);
			String userName=m.getUserName();
			
			// 로그인한사람의 이름 출력과 환영 메세지
			comServ.errorBox("로그인", "로그인 성공하였습니다.", userName+"님 환영합니다.");
			
			System.out.println("로그인할때 입력한 아이디값:"+id);
			System.out.println("해당아이디에 해당하는 회원 이름:"+userName);

			// 로그인 후 아이디비번쓰는 창 닫음
			Stage page = (Stage) root.getScene().getWindow(); 
			page.close();	

			// 마이페이지(진료예약,예약확인버튼 있는페이지) 다시 띄우기 (새창띄워서 버튼비활성화하기위함)
			Stage s = new Stage(); 
			root=comServ.showWindow(s, "../fxml/Mypage.fxml");
			s.setX(450);
			s.setY(110);

			// <마이페이지> 좌측 상단 표기
			Label myPageId = (Label) root.lookup("#myPageId");
			myPageId.setText(userName+"님 환영합니다!");
			
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
	
	// Login<첫 기본로그인 페이지> 에서 ManageLogin<관리자로그인 페이지>
	@Override
	public void manageOk(Parent root) {
		Stage s = (Stage) root.getScene().getWindow();
		comServ.showWindow(s, "../fxml/ManageLogin.fxml");
		s.setX(450);
		s.setY(110);
	
	}

	
	
	
	
	
	
	


	
	
	
	
	
	

	
	
	
	
	

	
	

	
	
	
	
	
	
	
	
	
	
	
	



	
	
	




}