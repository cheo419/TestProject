package project;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import project.DAO.MemberDAO;
import project.DAO.MemberDAOImpl;
import project.service.CommonService;
import project.service.CommonServiceImpl;
import project.service.LoginService;
import project.service.LoginServiceImpl;

public class ManageController extends Controller implements Initializable{

	private Parent root;
	private LoginService loginServ;
	CommonService comServ;
	MemberDAO dao;

	// 테이블뷰에서 행 선택시 선택된 행의 아이디값 저장을 위한 변수 선언
	String seleted;

	@FXML private TableView<Member> manageTable;
	@FXML private TableColumn<Member, String>  nameCol;
	@FXML private TableColumn<Member, String> numberCol;
	@FXML private TableColumn<Member, String>  idCol;
	@FXML private TableColumn<Member, String>  pwCol;
	@FXML private TableColumn<Member, String>  resCol;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		comServ = new CommonServiceImpl();

		dao = new MemberDAOImpl();
		List<Member> memberList = dao.selectAdmin();

		manageTable.setItems(getProduct(memberList));


		nameCol.setCellValueFactory 		   
		(new PropertyValueFactory<Member, String>("userName")); 
		numberCol.setCellValueFactory 		   
		(new PropertyValueFactory<Member, String>("phoneNumber")); 
		idCol.setCellValueFactory 	   
		(new PropertyValueFactory<Member, String>("id")); 
		pwCol.setCellValueFactory 		   
		(new PropertyValueFactory<Member, String>("pw"));
		resCol.setCellValueFactory	   
		(new PropertyValueFactory<Member, String>("res")); 

		// 테이블뷰에서 선택된 행
		manageTable.setOnMouseClicked(e->{
			seleted= manageTable.getSelectionModel().getSelectedItem().getId();
			System.out.println(manageTable.getSelectionModel().getSelectedItem().getId());
		});

		loginServ = new LoginServiceImpl();

	}

	@Override
	public void setRoot(Parent root) {
		this.root = root;

	}

	private ObservableList<Member> getProduct(List<Member> memberList) {
		ObservableList<Member> adminList =
				FXCollections.observableArrayList(memberList);
		return adminList;
	}

	// 관리자 페이지에서 다시 로그인 화면으로
	public void backLogin() {
		loginServ.backLogin(root);
	}

	// 관리자 페이지에서 선택된 예약 삭제
	public void deleteRes() {
		System.out.println("예약삭제버튼클릭");
		System.out.println("선택된 회원아이디: "+seleted);

		// 삭제되어 테이블뷰 업데이트를 위해 창 닫기
		Stage myPage = (Stage) root.getScene().getWindow();
		myPage.close();

		// 관리자페이지에서 테이블뷰에서 선택된 회원 예약내역 삭제
		if(dao.deleteUserRes(seleted)){
			System.out.println("아이디: "+seleted+"의 회원님 예약내역삭제");

			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setContentText("아이디: "+seleted+"의 회원님의 예약내역이 삭제되었습니다.");
			alert.showAndWait();	//알림창 띄우고 잠시 기다리기
		}

		//  예약내역삭제된 후 수정된 내용으로 창 다시 띄우기
		Stage membershipForm = new Stage();
		root=comServ.showWindow(membershipForm, "../ManagePage.fxml");

	}


	// 관리자 페이지에서 선택된 회원 전부다 삭제
	public void deleteUser() {
		System.out.println("삭제버튼클릭");
		System.out.println("선택된 회원아이디: "+seleted);

		// 삭제되어 테이블뷰 업데이트를 위해 창 닫기
		Stage myPage = (Stage) root.getScene().getWindow();
		myPage.close();

		// 관리자페이지에서 테이블뷰에서 선택된 회원 강제탈퇴
		if(dao.deleteUser(seleted)){
			System.out.println("아이디: "+seleted+"의 회원님 강제탈퇴");

			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setContentText("아이디: "+seleted+"의 회원님이 강제탈퇴되었습니다.");
			alert.showAndWait();	//알림창 띄우고 잠시 기다리기
		}

		//  삭제된 후 수정된 내용으로 창 다시 띄우기
		Stage membershipForm = new Stage();
		root=comServ.showWindow(membershipForm, "../ManagePage.fxml");
	}


}
