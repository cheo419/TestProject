package project.service;



public class MemberShipServiceImpl implements MemberShipService{

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



}