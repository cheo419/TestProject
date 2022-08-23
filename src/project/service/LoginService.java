package project.service;

import javafx.event.ActionEvent;
import javafx.scene.Parent;

public interface LoginService {
	
	public void LoginProc(Parent root); // 로그인 버튼 눌렀을 때
	
	public void res(Parent root); // 마이페이지 화면
	
	public int myResJinryo(Parent root); // 진료과선택
	
	public int myResTime(Parent root); // 진료시간선택
	
	public void resOk(Parent root,ActionEvent event); // 내 예약 출력
	
	public void Logout(Parent root); // 로그아웃
	
	public void resCheck(Parent root,ActionEvent event); // 내 예약출력화면에서 확인하면 닫기
	
	public void manageOk(Parent root); // 관리자 비밀번호창 열기
	
	public void manageLogin(Parent root,ActionEvent event); // 관리자 로그인 모든 예약 출력 화면

	public void backLogin(Parent root); // 관리자 화면에서 로그인 화면으로
	
}