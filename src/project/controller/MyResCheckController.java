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
import javafx.scene.control.ButtonType;
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
import project.service.MyResCheckService;
import project.service.MyResCheckServiceImpl;

public class MyResCheckController extends Controller implements Initializable{
	private Parent root;
	private CommonService commonServ;
	private MemberDAO dao;
	private MyResCheckService myResCheckServ;
	private static String id;
	private String seleted;		// 테이블뷰에서 행 선택시 선택된 행의 아이디값 저장을 위한 변수 선언
	private int seletedJinryo;
	private String seletedRes;
	
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
		myResCheckServ = new MyResCheckServiceImpl();
		dao = new MemberDAOImpl();

		// 유저의 모든 예약내용 출력하여 Member 리스트에 저장
		List<Member> memberList = dao.selectIdRes(id);	// 관리자 예약출력으로 모든 저장된 값을 리스트로 저장

		// 테이블뷰
		myResTable.setItems(getProduct(memberList));

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
			// 비어있는 행이 클릭되면 콘솔에 널포인트 에러가나는것을 방지
			try {
				seleted= myResTable.getSelectionModel().getSelectedItem().getId();
				seletedRes=myResTable.getSelectionModel().getSelectedItem().getRes();
				
				// 선택된 행의 진료과를 인트형으로 저장
				if(seletedRes.contains("정형외과")) {
					seletedJinryo=1;
				} else if(seletedRes.contains("이비인후과")) {
					seletedJinryo=2;
				} else if(seletedRes.contains("내과")) {
					seletedJinryo=3;
				}
				System.out.println(myResTable.getSelectionModel().getSelectedItem().getId());
				
			} catch (NullPointerException e1) {
				System.out.println("테이블뷰에서 빈 행이 선택되었습니다.");
			}
		});
	}
	private ObservableList<Member> getProduct(List<Member> memberList) {
		ObservableList<Member> adminList =
				FXCollections.observableArrayList(memberList);
		return adminList;
	}

	// [뒤로가기 버튼] 로그인한 회원의 예약내역을 보는 현재 페이지에서 뒤로가서 마이페이지로 
	public void backMypage2() {
		myResCheckServ.backMypage2(root,id);
	}
	
	// [추가 진료 예약 버튼] 예약내역보는 페이지에서 추가진료예약 진행
	public void addRes() {
		myResCheckServ.addRes(root,id);
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
					if(dao.deleteResSelect(seleted,seletedJinryo)){
						System.out.println("아이디: "+seleted+"의 회원님 예약내역삭제");

						Alert alert = new Alert(AlertType.INFORMATION);
						alert.setContentText("아이디: "+seleted+" 회원님의 예약내역이 삭제되었습니다.");
						alert.showAndWait();	//알림창 띄우고 잠시 기다리기
					}
					//  예약내역삭제된 후 수정된 내용으로 예약내역보기창 다시 띄우기
					Stage s = new Stage();
					root=commonServ.showWindow(s, "../fxml/MyResCheck.fxml");
					s.setX(450);
					s.setY(110);
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
