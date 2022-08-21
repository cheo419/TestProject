package project.service;

import java.io.IOException;

import project.Controller;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class CommonServiceImpl implements CommonService{

	// 새로운 화면 보이기
	
	@Override
	public Parent showWindow(Stage s, String formPath) {
		// TODO Auto-generated method stub
		FXMLLoader loader = new FXMLLoader
				(getClass().getResource(formPath));
		
		Parent root = null;
		
		try {
			root = loader.load();
			s.setScene(new Scene(root));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Controller ctl = loader.getController();
		
		ctl.setRoot(root);
		
		s.show();
		
		return root;
	}

	// 에러박스
	
	@Override
	public void errorBox(String title, String header, String content) {
		// TODO Auto-generated method stub
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle(title);
		alert.setHeaderText(header);
		alert.setContentText(content);
		alert.showAndWait();
	}

	@Override
	public void errorBox(String header, String content) {
		// TODO Auto-generated method stub
		errorBox("error", header, content);
	}

	@Override
	public void errorBox(String content) {
		// TODO Auto-generated method stub
		errorBox("error", "error header", content);
	}

	// 화면 닫기
	
	@Override
	public void WindowClose(ActionEvent event) {
		// TODO Auto-generated method stub
		Parent root = (Parent) event.getSource();
		Stage stage = (Stage) root.getScene().getWindow();
		stage.close();
	}



}