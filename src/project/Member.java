package project;

public class Member {
	// 정보
	private String id;
	private String pw;
	private String userName;
	private String phoneNumber;
	
	// 예약
	private int jinryo;
	private String date;
	private int time;
	
	private String res;
	
	
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
