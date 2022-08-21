package project.service;

import javafx.scene.Parent;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;

public class MemberShipServiceImpl implements MemberShipService{

	// 비어있으면 진행 안되게
	
	@Override
	public boolean isEmpty(String str) {
		// TODO Auto-generated method stub
		if(str.isEmpty()) {
			return true;
		}
		return false;
	}

	// 비밀번호 확인
	
	@Override
	public boolean comparePw(String pw1, String pw2) {
		// TODO Auto-generated method stub
		if(pw1.equals(pw2)) {
			return false;
		}
		return true;
	}



}