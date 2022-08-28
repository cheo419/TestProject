package project.service;

import java.util.Optional;

import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import project.Member;
import project.dao.MemberDAO;
import project.dao.MemberDAOImpl;

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

	// [추가 진료 예약 버튼] 예약내역보는 페이지에서 추가진료예약 진행
	@Override
	public void addRes(Parent root, String id) {
		System.out.println("추가 진료 예약 진행하기");
		
		// 모든 과에 진료예약내역이 있는경우 (없으면 true)
		if(dao.noJinryo(id, 1)==false&&dao.noJinryo(id, 2)==false&&dao.noJinryo(id, 3)==false) {
			comServ.errorBox("추가 진료예약 불가","모든 진료과에 예약내역이 존재합니다.","추가 진료예약이 취소됩니다.");
			return;
		}

		// 추가진료예약은 같은과는 안됨을 알리고 확인 취소 선택하게하기
		Alert alertWarn = new Alert(AlertType.CONFIRMATION);
		alertWarn.setTitle("추가 진료예약");
		alertWarn.setHeaderText("이미 예약한 진료과는 추가 진료 예약이 불가능합니다.");
		alertWarn.setContentText("예약하지 않은 진료과로 추가 예약 하시겠습니까?");
		Optional<ButtonType>result = alertWarn.showAndWait();

		// 확인버튼 누른 경우 추가진료예약을 받기위해 진료예약페이지 MyRes페이지로 이동
		if(result.get()==ButtonType.OK) {
			Stage s = (Stage) root.getScene().getWindow();
			comServ.showWindow(s, "../fxml/MyRes.fxml");
			s.setX(450);
			s.setY(110);
			
		} else {
			comServ.errorBox("추가 진료예약 취소","추가 진료예약을 취소하셨습니다.","기존 예약내역이 유지됩니다.");
		}







		}





	}