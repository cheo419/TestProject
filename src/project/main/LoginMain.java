package project.main;

import project.service.CommonService;
import project.service.CommonServiceImpl;
import javafx.application.Application;
import javafx.stage.Stage;

public class LoginMain extends Application {
	@Override
	public void start(Stage primaryStage) throws Exception {
		CommonService comServ = new CommonServiceImpl();
		comServ.showWindow(primaryStage, "../fxml/Login.fxml");
		primaryStage.setX(300);
		primaryStage.setY(80);
	}

	public static void main(String[] args) {
		launch(args);
	}
}