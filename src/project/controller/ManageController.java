package project.controller;

import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import project.dao.MemberDAO;
import project.dao.MemberDAOImpl;
import project.Member;
import project.service.CommonService;
import project.service.CommonServiceImpl;
import project.service.ManageService;
import project.service.ManageServiceImpl;

public class ManageController extends Controller implements Initializable{
	private Parent root;
	private ManageService manageServ;
	private CommonService comServ;
	private MemberDAO dao;
	private String seleted;		// 테이블뷰에서 행 선택시 선택된 행의 아이디값 저장을 위한 변수 선언
	private String seletedRes;		
	private int seletedJinryo;		

	@FXML private TableView<Member> manageTable;
	@FXML private TableColumn<Member, String>  nameCol;
	@FXML private TableColumn<Member, String> numberCol;
	@FXML private TableColumn<Member, String>  idCol;
	@FXML private TableColumn<Member, String>  pwCol;
	@FXML private TableColumn<Member, String>  resCol;

	// <관리자페이지> 관리자가 모든 회원정보 확인 및 관리가능 (버튼: 선택회원 예약취소, 선택회원 삭제, 로그인화면으로) (테이블뷰)

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		comServ = new CommonServiceImpl();	
		manageServ = new ManageServiceImpl();
		dao = new MemberDAOImpl();

		List<Member> memberList = dao.selectAdmin();	// 관리자 예약출력으로 모든 저장된 값을 리스트로 저장

		manageTable.setItems(getProduct(memberList));

		// 각 칼럼과 매칭되는 클래스 변수명을 지정해 준다
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
			// 비어있는 행이 클릭되면 콘솔에 널포인트 에러가나는것을 방지
			try {
				// 선택된 행의 아이디를 저장
				seleted= manageTable.getSelectionModel().getSelectedItem().getId();

				// 선택된 행의 진료과를 인트형으로 변경 저장
				seletedRes=manageTable.getSelectionModel().getSelectedItem().getRes();
				if(seletedRes.contains("정형외과")) {
					seletedJinryo=1;
				} else if(seletedRes.contains("이비인후과")) {
					seletedJinryo=2;
				} else if(seletedRes.contains("내과")) {
					seletedJinryo=3;
				}
				System.out.println(manageTable.getSelectionModel().getSelectedItem().getId());
				System.out.println("선택된 진료예약내용 :"+seletedRes);
				System.out.println("선택된 진료과 :"+seletedJinryo);

			} catch (NullPointerException e1) {
				System.out.println("비어있는 행이 선택되었습니다.");
			}
		});
	}

	@Override
	public void setRoot(Parent root) {
		this.root = root;
	}

	// 테이블뷰 통합
	private ObservableList<Member> getProduct(List<Member> memberList) {
		ObservableList<Member> adminList =
				FXCollections.observableArrayList(memberList);
		return adminList;
	}

	// [로그인화면으로 버튼] 관리자 페이지에서 다시 처음 로그인 화면으로
	public void backLogin() {
		manageServ.backLogin(root);
	}

	// [선택회원 예약취소 버튼] 관리자 페이지에서 선택된 행의 회원 예약내역 삭제
	public void deleteRes() {
		System.out.println("예약삭제버튼클릭");
		System.out.println("예약내역 삭제시키려는 회원아이디: "+seleted);

		// 삭제하려는 행이 선택된경우
		if(seleted!=null) {

			// 예약내역삭제 전에 정말로 삭제할것인지 경고창 (잘못누른경우 취소할 수 있도록) 
			Alert alertWarn = new Alert(AlertType.CONFIRMATION);
			alertWarn.setTitle(seleted+"회원님의 예약내역삭제");
			alertWarn.setHeaderText(seleted+"회원님의 예약내역을 삭제합니다.");
			alertWarn.setContentText("삭제 복구가 불가능합니다. 삭제하시겠습니까?");
			Optional<ButtonType>result = alertWarn.showAndWait();

			// 예약내역 삭제하기 경고창에서 확인버튼 눌러 삭제 진행
			if(result.get()==ButtonType.OK) {
				// 예약여부확인 : 예약내역있음true
				if(dao.checkRes(seleted)) {
					System.out.println("삭제진료과: "+seletedJinryo);

					// 관리자페이지에서 테이블뷰에서 선택된 회원 예약내역 삭제
					if(dao.deleteResSelect(seleted,seletedJinryo)){
						System.out.println("아이디: "+seleted+"의 회원님 예약내역삭제");
						Alert alert = new Alert(AlertType.INFORMATION);
						alert.setContentText("아이디: "+seleted+"의 회원님의 예약내역이 삭제되었습니다.");
						alert.showAndWait();	//알림창 띄우고 잠시 기다리기
					}
					// 삭제되어 테이블뷰 업데이트를 위해 창 닫기
					Stage myPage = (Stage) root.getScene().getWindow();
					myPage.close();

					//  예약내역삭제된 후 수정된 내용으로 창 다시 띄우기
					Stage s = new Stage();
					root=comServ.showWindow(s, "../fxml/Manage.fxml");
					s.setX(450);
					s.setY(110);
				} else {
					// 예약여부확인했는데 false : 예약내역없음
					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setContentText("선택된 회원아이디: "+seleted+"는 삭제할 예약내역이 없습니다.");
					alert.show();	
				}
			} else { 	// 예약내역 삭제하기 경고창에서 취소버튼 눌러 탈퇴진행되지 않음.
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setContentText("아이디: "+seleted+"의 회원님이 예약내역삭제가 취소되었습니다.");
				alert.show();	
			}
		} else {	//삭제하려는 행이 선택되지 않은 경우
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setContentText("선택된 행이 없습니다. 행을 선택 후 삭제해주세요.");
			alert.show();
		}
	}

	// [선택회원 삭제 버튼] 관리자 페이지에서 선택된 회원 강제 탈퇴
	public void deleteUser() {
		System.out.println("삭제버튼클릭");
		System.out.println("선택된 회원아이디: "+seleted);

		// 삭제하려는 행이 선택된경우
		if(seleted!=null) {

			// 회원강제탈퇴 전에 정말로 탈퇴시킬것인지 경고창 (잘못누른경우 취소할 수 있도록) 
			Alert alertWarn = new Alert(AlertType.CONFIRMATION);
			alertWarn.setTitle(seleted+"회원님의 강제탈퇴");
			alertWarn.setHeaderText(seleted+"회원님을 강제탈퇴 시킵니다.");
			alertWarn.setContentText("탈퇴시 복구가 불가능합니다. 탈퇴시키겠습니까?");
			Optional<ButtonType>result = alertWarn.showAndWait();

			// 강제탈퇴 경고창에서 확인버튼 눌러 강제탈퇴 진행
			if(result.get()==ButtonType.OK) {

				// 강제탈퇴시 테이블뷰 업데이트를 위해 창 닫기
				Stage page = (Stage) root.getScene().getWindow();
				page.close();

				// 관리자페이지에서 테이블뷰에서 선택된 회원 강제탈퇴
				// 반드시 예약내역 삭제가 선행되어야함.

				// 예약여부 확인하고 예약이 있으면 예약 삭제
				if(dao.checkRes(seleted)) {
					dao.deleteUserResAll(seleted);
				}
				// 회원 탈퇴
				if(dao.deleteUser(seleted)){
					System.out.println("아이디: "+seleted+"의 회원님 강제탈퇴");

					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setContentText("아이디: "+seleted+"의 회원님이 강제탈퇴되었습니다.");
					alert.showAndWait();	//알림창 띄우고 잠시 기다리기
				}
				//  삭제된 후 수정된 내용으로 창 다시 띄우기
				Stage s = new Stage();
				root=comServ.showWindow(s, "../fxml/Manage.fxml");
				s.setX(450);
				s.setY(110);

			} else { 	// 강제탈퇴 경고창에서 취소버튼 눌러 탈퇴진행되지 않음.
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setContentText("아이디: "+seleted+"의 회원님이 강제탈퇴가 취소되었습니다.");
				alert.show();	
			}
		} else {	//삭제하려는 행이 선택되지 않은 경우
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setContentText("선택된 행이 없습니다. 행을 선택 후 삭제해주세요.");
			alert.show();
		}
	}


}
