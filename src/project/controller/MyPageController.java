package project.controller;

import java.net.URL;
import java.util.ResourceBundle;

import project.service.CommonServiceImpl;
import project.service.LoginServiceImpl;
import project.service.CommonService;
import project.service.LoginService;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;

public class MyPageController extends Controller implements Initializable{
	private Parent root;
	private LoginService loginServ;
	private CommonService commonServ;
	
	// <마이페이지> (버튼: 진료예약, 예약확인, 로그아웃)

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		loginServ = new LoginServiceImpl();
		commonServ = new CommonServiceImpl();
		
	}

	@Override
	public void setRoot(Parent root) {
		this.root = root;
	}

	// [로그아웃 버튼]
	public void Logout() {
		loginServ.Logout(root);
	}
	
	// [진료예약 버튼] (진료과,날짜,시간 선택하는 페이지를 띄우는 버튼)
	public void res() {
		Stage myPage = (Stage) root.getScene().getWindow();
		myPage.close();	
		Stage membershipForm = new Stage();
		Parent form = commonServ.showWindow(membershipForm, "../fxml/MyRes.fxml");
		String[] items = {"정형외과", "이비인후과", "내과"};

		ComboBox<String> cmbjinryo = (ComboBox<String>) form.lookup("#cmbJinryo");
		for(String item:items) {
			cmbjinryo.getItems().add(item);
		}

		String[] items1 = {"09:30", "10:30", "11:30", "13:30", "14:30", "15:30"};
		ComboBox<String> cmbtime = (ComboBox<String>) form.lookup("#cmbTime");

		for(String item1 : items1) {
			cmbtime.getItems().add(item1);
		}
	}
	// [예약확인 버튼]
	public void resOk(ActionEvent event) {
		loginServ.resOk(root,event);
	}

	
	
	
	



	
	
	






}