package project.main;

import project.service.CommonService;
import project.service.CommonServiceImpl;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javafx.application.Application;
import javafx.stage.Stage;

public class LoginMain extends Application {
	@Override
	public void start(Stage primaryStage) throws Exception {
		CommonService comServ = new CommonServiceImpl();
		comServ.showWindow(primaryStage, "../fxml/Login.fxml");


	}

	public static void main(String[] args) {
		launch(args);
	}
}
