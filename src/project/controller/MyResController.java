package project.controller;

import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

import project.service.CommonServiceImpl;
import project.service.MyResService;
import project.service.MyResServiceImpl;
import project.service.CommonService;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.stage.Stage;


// <진료 예약 페이지> 로그인한 회원의 진료예약 (입력: 진료과_콤보박스, 진료날짜_데이트피커, 진료시간_콤보박스) (버튼: 확인, 취소)

public class MyResController extends Controller implements Initializable{
	private Parent root;
	private MyResService myResServ;
	private CommonService commonServ;

	@FXML DatePicker cmbDate;
	@FXML Button btnResOk;

	// 오늘 날짜
	LocalDate now = LocalDate.now();	      
	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");         
	String formatedNow = now.format(formatter);  
	int today = Integer.parseInt(formatedNow);

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		myResServ = new MyResServiceImpl();
		commonServ = new CommonServiceImpl();

		// 날짜 표기 변환
		String pattern = "yyyyMMdd";	
		DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(pattern);

		// 데이트 피커에서 날짜 선택시 출력
		cmbDate.setOnAction(new EventHandler<ActionEvent>() 
		{
			@Override
			public void handle(ActionEvent event) 
			{
				LocalDate date = cmbDate.getValue();
				System.out.println("선택한 날짜: " + dateFormatter.format(date));
				int selectedDate = Integer.parseInt(dateFormatter.format(date));
				System.out.println("오늘 날짜 : " + today);
				
				//선택된 날짜가 오늘 또는 과거날짜인경우
				if(selectedDate<=today) {
					System.out.println("오늘 또는 과거날짜를 선택하셨습니다.");
					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setTitle("날짜 선택 오류");
					alert.setHeaderText("오늘날짜 또는 과거날짜는 선택 불가능합니다.");
					alert.setContentText("원하시는 진료 날짜를 다시 선택해주세요.");
					alert.showAndWait();
					btnResOk.setDisable(true);
					
					cmbDate.requestFocus();
					
				} else {	// 내일이후 날짜 선택한경우 콘솔에 출력
					System.out.println("내일 이후 날짜를 선택하셨습니다. 확인 버튼이 활성화됩니다.");
					btnResOk.setDisable(false);
					
				}
			}
		});
	}

	@Override
	public void setRoot(Parent root) {
		this.root = root;
	}


	// [확인 버튼] 진료예약(진료과,날짜,시간 선택) 입력한 후 최종 진료예약하는 버튼 
	public void resCheck() {
		myResServ.resCheck(root);
	}

	// [취소 버튼] 진료예약(진료과,날짜,시간 선택) 입력하던 중 취소 후 마이페이지로
	public void backMyPage() {
		// 진료예약페이지 닫힘
		Stage myPage = (Stage) root.getScene().getWindow();
		myPage.close();	

		// 마이페이지(진료예약,예약확인버튼 있는페이지) 다시 띄우기 
		Stage membershipForm = new Stage(); 
		root=commonServ.showWindow(membershipForm, "../fxml/Mypage.fxml");
	}










}
