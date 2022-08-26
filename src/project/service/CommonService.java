package project.service;

import javafx.event.ActionEvent;
import javafx.scene.Parent;
import javafx.stage.Stage;

public interface CommonService {
	public void WindowClose(ActionEvent event); // 닫기
	
	public Parent showWindow(Stage s, String formPath); // 새로운 화면
	
	public void errorBox(String title, String header, String content); // 에러박스
	public void errorBox(String header, String content);
	public void errorBox(String content);
}