package project.service;

import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import project.Member;
import project.dao.MemberDAO;
import project.dao.MemberDAOImpl;
import project.service.CommonService;
import project.service.CommonServiceImpl;

public class MyResCheckServiceImpl implements MyResCheckService{
	private CommonService comServ;
	private MemberDAO dao;

	public MyResCheckServiceImpl() {
		comServ = new CommonServiceImpl();
		dao = new MemberDAOImpl();
	}
	
	// [뒤로가기 버튼] 로그인한 회원의 예약내역을 보는 현재 페이지에서 뒤로가서 마이페이지로 
	public void backMypage2(Parent root,String id) {
		// 내 예약내역 확인 페이지 닫힘
		Stage page = (Stage) root.getScene().getWindow();
		page.close();	

		// 마이페이지(진료예약,예약확인버튼 있는페이지) 다시 띄우기 
		Stage s = new Stage(); 
		root=comServ.showWindow(s, "../fxml/Mypage.fxml");
		s.setX(450);
		s.setY(110);

		// <마이페이지> 좌측 상단 표기
		Label myPageId = (Label) root.lookup("#myPageId");
		
		// 마이페이지 좌상단에 이름을 출력해주기위해서 이름을 가져옴.
		Member member = dao.select(id);
		String userName=member.getUserName();
		
		myPageId.setText(userName+"님 환영합니다!");

		// 예약된 내역이있는지 boolean으로 체크하고 버튼비활성화 처리
		if(dao.checkRes(id)){
			System.out.println("예약내용있음");
			Button myResBtn = (Button) root.lookup("#myResBtn");
			myResBtn.setDisable(true);
		}
	}
	
	



}