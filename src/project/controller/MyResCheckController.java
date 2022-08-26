package project.controller;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import project.dao.MemberDAO;
import project.dao.MemberDAOImpl;
import project.Member;
import project.service.CommonService;
import project.service.CommonServiceImpl;

public class MyResCheckController extends Controller implements Initializable{
	private Parent root;
	private CommonService commonServ;
	private MemberDAO dao;
	private static String id;
	private String seleted;		// 테이블뷰에서 행 선택시 선택된 행의 아이디값 저장을 위한 변수 선언

	@FXML private TableView<Member> myResTable;
	@FXML private TableColumn<Member, String>  nameCol;
	@FXML private TableColumn<Member, String> numberCol;
	@FXML private TableColumn<Member, String>  idCol;
	@FXML private TableColumn<Member, String>  pwCol;
	@FXML private TableColumn<Member, String>  resCol;

	// <회원용 예약확인 페이지> 로그인한 회원의 예약확인 및 예약취소 가능 (버튼: 선택 예약취소, 뒤로가기) (테이블뷰)

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		commonServ = new CommonServiceImpl();
		dao = new MemberDAOImpl();

		// 유저의 예약내용 출력하여 Member클래스에 저장
		Member m= dao.select(id);	

		// 테이블뷰
		myResTable.setItems(getProduct(m));

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
		myResTable.setOnMouseClicked(e->{
			seleted= myResTable.getSelectionModel().getSelectedItem().getId();
			System.out.println(myResTable.getSelectionModel().getSelectedItem().getId());
		});
	}
	private ObservableList<Member> getProduct(Member m) {
		ObservableList<Member> adminList =
				FXCollections.observableArrayList(m);
		return adminList;
	}

	// [뒤로가기 버튼] 로그인한 회원의 예약내역을 보는 현재 페이지에서 뒤로가서 마이페이지로 
	public void backMypage2() {
		// 내 예약내역 확인 페이지 닫힘
		Stage myPage = (Stage) root.getScene().getWindow();
		myPage.close();	

		// 마이페이지(진료예약,예약확인버튼 있는페이지) 다시 띄우기 
		Stage membershipForm = new Stage(); 
		root=commonServ.showWindow(membershipForm, "../fxml/Mypage.fxml");
		membershipForm.setX(300);
		membershipForm.setY(80);

		// <마이페이지> 좌측 상단에 아이디 표기
		Label myPageId = (Label) root.lookup("#myPageId");
		myPageId.setText(id+"님 환영합니다!");

		// 예약된 내역이있는지 boolean으로 체크하고 버튼비활성화 처리
		if(dao.checkRes(id)){
			System.out.println("예약내용있음");
			Button myResBtn = (Button) root.lookup("#myResBtn");
			myResBtn.setDisable(true);
		}
	}

	// [선택 예약 삭제 버튼]
	public void deleteMyRes() {
		System.out.println("예약삭제버튼클릭");
		System.out.println("예약내역 삭제하려는 회원아이디: "+seleted);

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

					// 삭제되어 테이블뷰 업데이트를 위해 창 닫기
					Stage myPage = (Stage) root.getScene().getWindow();
					myPage.close();

					// 예약내역보기 테이블뷰에서 선택된 회원 예약내역 삭제
					if(dao.deleteUserRes(seleted)){
						System.out.println("아이디: "+seleted+"의 회원님 예약내역삭제");

						Alert alert = new Alert(AlertType.INFORMATION);
						alert.setContentText("아이디: "+seleted+" 회원님의 예약내역이 삭제되었습니다.");
						alert.showAndWait();	//알림창 띄우고 잠시 기다리기
					}
					//  예약내역삭제된 후 수정된 내용으로 예약내역보기창 다시 띄우기
					Stage membershipForm = new Stage();
					root=commonServ.showWindow(membershipForm, "../fxml/MyResCheck.fxml");
					membershipForm.setX(300);
					membershipForm.setY(80);
				} else {	// 예약여부확인했는데 false : 예약내역없음
					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setContentText("아이디: "+seleted+" 회원님은 삭제할 예약내역이 없습니다.");
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

	public void setId(String id) {
		this.id = id;
	}
	public String getId() {
		return id;
	}
	@Override
	public void setRoot(Parent root) {
		this.root = root;
	}






}
