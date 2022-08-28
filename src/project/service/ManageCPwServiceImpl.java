package project.service;

import javafx.scene.Parent;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import project.Admin;
import project.dao.AdminDAO;
import project.dao.AdminDAOImpl;

public class ManageCPwServiceImpl implements ManageCPwService{
	
	private CommonService comServ;
	private AdminDAO aDao;
	
	public ManageCPwServiceImpl() {
		comServ = new CommonServiceImpl();
		aDao = new AdminDAOImpl();
	}

	// <확인 버튼> 관리자 비번을 변경 (입력한 내용에 문제없는지 체크 후 수정)
	@Override
	public void changeMPw(Parent root) {
		Admin a = new Admin();
		TextField txtMPw = (TextField) root.lookup("#mPw");
		TextField txtNewMPw = (TextField) root.lookup("#newMPw");
		TextField txtNewMPwOk = (TextField) root.lookup("#newMPwOk");

		// 비어있는 입력칸이 있으면 알림창띄움
		String[] txtEmpty = {
				txtMPw.getText(),
				txtNewMPw.getText(),
				txtNewMPwOk.getText(),
		};
		String[] txtEmptyName = {"현재 비밀번호","신규 비밀번호","신규 비밀번호 확인"};
		for(int i=0;i<txtEmpty.length;i++) {
			if(isEmpty(txtEmpty[i])) {
				comServ.errorBox("수정정보 입력 오류",txtEmptyName[i] + " 입력창이 비어 있습니다","수정이 되지 않았습니다. 다시 시도 해주세요.");
				return;
			}
		}

		// 입력한 암호가 다를경우 
		String NewMPw = txtNewMPw.getText();
		String NewMPwOk = txtNewMPwOk.getText();
		if(comparePw(NewMPw, NewMPwOk)){
			comServ.errorBox("관리자 비밀번호 수정 오류","입력하신 암호가 다릅니다.");
			txtNewMPw.requestFocus();
			return;
		}
		// 새로 입력받은 정보를 ADMIN 클래스에 저장
		a.setMPw(txtMPw.getText());
		a.setNewMPw(txtNewMPw.getText());

		// 관리자 비밀번호 변경하기
		if(aDao.updateMPw(a)) {
			comServ.errorBox("관리자 비밀번호 수정", "수정 완료", "관리자 비밀번호가 정상적으로 수정되었습니다.");
			
			Stage s = (Stage) root.getScene().getWindow();
			comServ.showWindow(s, "../fxml/ManageLogin.fxml");
			s.setX(450);
			s.setY(110);
		} else {
			comServ.errorBox("관리자 비밀번호 수정 오류","관리자 비밀번호가 수정되지않았습니다.","기존 관리자비밀번호를 확인해주세요.");
			// 다시 기존비밀번호를 받기위해 포커스
			txtMPw.requestFocus();
			return;
		}
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




