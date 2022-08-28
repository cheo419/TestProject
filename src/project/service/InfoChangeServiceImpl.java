package project.service;

import java.util.Optional;

import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import project.Member;
import project.dao.MemberDAO;
import project.dao.MemberDAOImpl;

public class InfoChangeServiceImpl implements InfoChangeService {
	private MemberDAO dao;
	private CommonService commonServ;
	private static String id;
	private String checkPw;
	
	public InfoChangeServiceImpl() {
		dao = new MemberDAOImpl();
		commonServ = new CommonServiceImpl();
	}

	// 메세지알림창에서 입력받은 비밀번호를 저장하고 보내줌
	public String getCheckPw() {
		return checkPw;
	}
	public void setCheckPw(String checkPw) {
		this.checkPw = checkPw;
	}
	// 로그인할때 입력받은 아이디를 받아오고 사용
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
			commonServ.errorBox("회원정보 수정 오류","입력하신 암호가 다릅니다.","입력하신 비밀번호를 확인해주세요");
			txtPw.requestFocus();
			return;
		}
		
		// 기존비밀번호와 새로변경할 비번다른지 확인
		Member member = dao.select(id);
		if(member.getPw().equals(newPw)) {
			commonServ.errorBox("회원정보 수정 오류","신규비밀번호가 기존비밀번호와 동일합니다.","입력하신 비밀번호를 확인해주세요");
			txtPw.requestFocus();
			return;
		}
		
		// 텍스트 입력받는 메세지창을 띄워서 기존비밀번호와 확인하기
		TextInputDialog tid = new TextInputDialog("기존비밀번호 입력");
		tid.setTitle("회원정보 수정");
		tid.setHeaderText("기존 비밀번호를 확인합니다.");
		tid.setContentText("기존 비밀번호를 입력해주세요.");
		Optional<String> result = tid.showAndWait();
		result.ifPresent(name -> {
			System.out.println("기존비밀번호: " + name);
			setCheckPw(name);
		});
		
		if(member.getPw().equals(getCheckPw())) {
			// 새로 입력받은 정보를 멤버 클래스에 저장
			member.setPw(txtPw.getText());
			member.setUserName(txtName.getText());
			member.setPhoneNumber(txtNumber.getText());
			
			if(dao.updateInfo(member, id)) {
				commonServ.errorBox("회원정보 수정", "수정 완료", "회원정보가 정상적으로 수정되었습니다.");
			} else {
				commonServ.errorBox("회원정보 수정 오류","데이터베이스에 저장되지 않았습니다.","오류를 확인해주세요.");
				return;
			}
		} else {
			commonServ.errorBox("회원정보 수정 오류","입력하신 기존 비밀번호가 틀립니다.","기존 비밀번호를 확인해주세요.");
			txtPw.requestFocus();
			return;
		}

		// 기존 <회원정보 수정> 창을 닫고 <마이페이지> 창을 띄움
		Stage page = (Stage) root.getScene().getWindow();
		page.close();	

		Stage s = new Stage();
		root=commonServ.showWindow(s, "../fxml/MyPage.fxml");
		s.setX(300);
		s.setY(80);

		// <마이페이지> 좌측 상단 표기
		Label myPageId = (Label) root.lookup("#myPageId");
		String userName=member.getUserName();	//멤버클래스에 저장된 이름저장.

		myPageId.setText(userName+"님 환영합니다!");


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
		// 탈퇴확인시에 기존비밀번호와 맞는지 확인을하기위해 저장
		Member member = dao.select(id);
		
		// 텍스트 입력받는 메세지창을 띄워서 기존비밀번호와 확인하기
		TextInputDialog tid2 = new TextInputDialog("기존비밀번호 입력");
		tid2.setTitle("아이디 :"+id+"님 탈퇴를 위해 기존비밀번호를 확인합니다.");
		tid2.setHeaderText("아이디 :"+id+"님의 탈퇴시 정보는 복구되지 않습니다.");
		tid2.setContentText("탈퇴를 원하시면 기존 비밀번호를 입력해주세요.");
		Optional<String> result2 = tid2.showAndWait();
		result2.ifPresent(name -> {
			System.out.println("기존비밀번호: " + name);
			setCheckPw(name);
		});

		// 회원 탈퇴하기 기존비밀번호 확인 알림창에서 비번입력후 확인버튼 눌름
		// 입력하신 기존비밀번호가 실제 비밀번호와 같으므로 탈퇴가 진행됨.
		if(member.getPw().equals(getCheckPw())) {
			System.out.println("회원탈퇴를 경고하는 알림창에서 확인버튼을 클릭");

			// 데이터베이스에서 정보를 삭제함.
			if(dao.deleteUser(id)) {
				System.out.println("아이디: "+id+"의 회원님 탈퇴됨.");
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setContentText("아이디: "+id+"의 회원님이 탈퇴되었습니다.");
				alert.showAndWait();	//알림창 띄우고 잠시 기다리기
			}
		} else {
			// 알림창에서 입력된 비밀번호가 다른경우
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("탈퇴가 취소되었습니다.");
			alert.setHeaderText("기존비밀번호가 입력되지않았거나 입력된 비밀번호가 기존비밀번호와 다릅니다.");;
			alert.setContentText("아이디: "+id+"님의 회원님의 회원탈퇴가 취소되었습니다.");
			alert.showAndWait();
			return;
		}
		// 기존 <회원정보수정>창을 닫고 맨처음 <기본 첫 로그인페이지>으로 돌아감
		Stage page = (Stage) root.getScene().getWindow();
		page.close();	

		Stage s = new Stage();
		root=commonServ.showWindow(s, "../fxml/Login.fxml");
		s.setX(300);
		s.setY(80);
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



