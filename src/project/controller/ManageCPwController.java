package project.controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.stage.Stage;
import project.service.CommonService;
import project.service.CommonServiceImpl;


public class ManageCPwController extends Controller implements Initializable {
	private Parent root;
	private CommonService commonServ;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		commonServ = new CommonServiceImpl();
	}

	@Override
	public void setRoot(Parent root) {
		// TODO Auto-generated method stub
		this.root = root;
	}

	public void cancleMPw() {
		Stage s = (Stage) root.getScene().getWindow();
		commonServ.showWindow(s, "../fxml/ManageLogin.fxml");
		s.setX(450);
		s.setY(110);
	}
}
