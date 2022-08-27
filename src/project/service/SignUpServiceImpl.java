package project.service;

import javafx.scene.Parent;
import javafx.scene.control.TextField;
import project.dao.MemberDAO;
import project.dao.MemberDAOImpl;

public class SignUpServiceImpl implements SignUpService{

	private MemberDAO dao;
	private CommonService comServ;
	
	
	public SignUpServiceImpl() {
		dao = new MemberDAOImpl();
		comServ = new CommonServiceImpl();
	}
	
	// 비어있으면 진행 안되게
	@Override
	public boolean isEmpty(String str) {
		if(str.isEmpty()) {
			return true;
		}
		return false;
	}

	// 비밀번호 확인
	@Override
	public boolean comparePw(String pw1, String pw2) {
		if(pw1.equals(pw2)) {
			return false;
		}
		return true;
	}

	// [중복체크 버튼]
	@Override
	public void sameCheck(Parent root) {
		
		TextField txtId = (TextField) root.lookup("#lId");
		String id = txtId.getText();
		
		// 회원가입시에 같은 아이디가있는지 검색 : 동일한 아이디 있으면 true
		if(dao.findSameId(id)) {
			comServ.errorBox("아이디 중복 체크","동일한 아이디로 회원이 존재합니다.","다른 아이디로 시도해주세요.");
			txtId.requestFocus();
			return;
		}
		
		
	}



}