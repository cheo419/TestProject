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
		TextField txtPw = (TextField) root.lookup("#lPw");
		String id = txtId.getText();
		System.out.println("가입하려는아이디 : "+id);
		if(id.equals("")) {
			comServ.errorBox("아이디 입력 오류","아이디가 입력되지 않았습니다.","가입하실 아이디를 입력해주세요.");
		} else {
			// 회원가입시에 같은 아이디가있는지 검색 : 동일한 아이디 있으면 true
			if(dao.findSameId(id)) {
				comServ.errorBox("아이디 중복 체크 완료","동일한 아이디로 회원이 존재합니다.","다른 아이디로 시도해주세요.");
				txtId.requestFocus();
				return;
			} else {
				comServ.errorBox("아이디 중복 체크 완료","동일한 아이디로 가입된 정보가 없습니다.","입력하신 "+id+"로 회원가입 가능합니다.");
				txtPw.requestFocus();
			}
			
		}
		
		
		
	}



}