package project;

public class Member {
	// 정보
	private String id;			// 아이디
	private String pw;			// 비밀번호
	private String userName;	// 이름
	private String phoneNumber;	// 전화번호
	
	// 예약
	private int jinryo;		// 예약진료과
	private String date;	//	예약날짜
	private int time;		// 예약시간
	
	private String res;		// 예약진료과 + 예약날짜 + 예약시간 ( 예약내역 출력시 세가지 값 묶어서 사용 )
	
	
	
	
	public String getRes() {
		return res;
	}
	public void setRes(String res) {
		this.res = res;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPw() {
		return pw;
	}
	public void setPw(String pw) {
		this.pw = pw;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	
	// 예약
	public int getJinryo() {
		return jinryo;
	}
	public void setJinryo(int jinryo) {
		this.jinryo = jinryo;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public int getTime() {
		return time;
	}
	public void setTime(int time) {
		this.time = time;
	}
	
}
