package project.service;

import java.util.Optional;

import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import project.Member;
import project.dao.MemberDAO;
import project.dao.MemberDAOImpl;

public class InfoChangeServiceImpl implements InfoChangeService {
	private MemberDAO dao;
	private CommonService commonServ;
	private static String id;
	private Member member;

	public InfoChangeServiceImpl() {
		dao = new MemberDAOImpl();
		commonServ = new CommonServiceImpl();
		member = new Member();
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getId() {
		return id;
	}

	// [수정 버튼] InfoChange<회원정보 수정 페이지> 에서 MyPage<마이페이지> 회원정보를 수정하고 마이페이지로 돌아갑니다.
	@Override
	public void change(Parent root) {
		TextField txtPw = (TextField) root.lookup("#lPw");
		TextField txtPwOk = (TextField) root.lookup("#lPwOk");
		TextField txtName = (TextField) root.lookup("#lName");
		TextField txtNumber = (TextField) root.lookup("#lNumber");

		// 비어있는 입력칸이 있으면 알림창띄움
		String[] txtEmpty = {
				txtPw.getText(),
				txtPwOk.getText(),
				txtName.getText(),
				txtNumber.getText()
		};
		String[] txtEmptyName = {"비밀번호","비밀번호 확인","이름","전화번호"};
		for(int i=0;i<txtEmpty.length;i++) {
			if(isEmpty(txtEmpty[i])) {
				commonServ.errorBox("수정정보 입력 오류",txtEmptyName[i] + " 입력창이 비어 있습니다","수정이 되지 않았습니다. 다시 시도 해주세요.");
				return;
			}
		}

		// 입력한 암호가 다를경우 
		String newPw = txtPw.getText();
		String newPwOk = txtPwOk.getText();
		if(comparePw(newPw, newPwOk)){
			commonServ.errorBox("회원정보 수정 오류","입력하신 암호가 다릅니다.");
			txtPw.requestFocus();
			return;
		}
		// 새로 입력받은 정보를 멤버 클래스에 저장
		member.setPw(txtPw.getText());
		member.setUserName(txtName.getText());
		member.setPhoneNumber(txtNumber.getText());

		if(dao.updateInfo(member, id)) {
			commonServ.errorBox("회원정보 수정", "수정 완료", "회원정보가 정상적으로 수정되었습니다.");
		} else {
			commonServ.errorBox("에러","에러","에러");
			return;
		}

		// 기존 <회원정보 수정> 창을 닫고 <마이페이지> 창을 띄움
		Stage myPage = (Stage) root.getScene().getWindow();
		myPage.close();	

		Stage membershipForm = new Stage();
		root=commonServ.showWindow(membershipForm, "../fxml/MyPage.fxml");
		membershipForm.setX(300);
		membershipForm.setY(80);

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

	// [탈퇴 버튼] InfoChange<회원정보 수정 페이지> 에서 Login<첫 기본 로그인페이지> 회원정보를 모두 삭제하고 로그인페이지로 돌아갑니다.
	@Override
	public void out(Parent root) {
		Alert alertWarn = new Alert(AlertType.CONFIRMATION);
		alertWarn.setTitle(id+"님 탈퇴하기");
		alertWarn.setHeaderText(id+"회원님의 모든 정보를 삭제하고 회원탈퇴 됩니다.");
		alertWarn.setContentText("탈퇴시 모든 정보는 복구가 불가능합니다. 탈퇴하시겠습니까?");
		Optional<ButtonType>result = alertWarn.showAndWait();

		// 회원 탈퇴하기 경고창에서 확인버튼 눌러 탈퇴 진행
		if(result.get()==ButtonType.OK) {
			System.out.println("회원탈퇴를 경고하는 알림창에서 확인버튼을 클릭");

			if(dao.deleteUser(id)) {
				System.out.println("아이디: "+id+"의 회원님 탈퇴됨.");
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setContentText("아이디: "+id+"의 회원님이 탈퇴되었습니다.");
				alert.showAndWait();	//알림창 띄우고 잠시 기다리기
			}
		} else {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setContentText("아이디: "+id+"님의 회원님의 회원탈퇴가 취소되었습니다.");
			alert.showAndWait();
			return;
		}
		// 기존 <회원정보수정>창을 닫고 맨처음 <기본 첫 로그인페이지>으로 돌아감
		Stage myPage = (Stage) root.getScene().getWindow();
		myPage.close();	

		Stage membershipForm = new Stage();
		root=commonServ.showWindow(membershipForm, "../fxml/Login.fxml");
		membershipForm.setX(300);
		membershipForm.setY(80);
	}

	// 비어있으면 진행 안되게
	public boolean isEmpty(String str) {
		if(str.isEmpty()) {
			return true;
		}
		return false;
	}
	// 입력된 비밀번호 확인
	public boolean comparePw(String pw1, String pw2) {
		if(pw1.equals(pw2)) {
			return false;
		}
		return true;
	}



}



