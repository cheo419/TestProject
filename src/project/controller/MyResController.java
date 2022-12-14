package project.controller;

import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

import project.service.CommonServiceImpl;
import project.service.MyResService;
import project.service.MyResServiceImpl;
import project.Member;
import project.dao.MemberDAO;
import project.dao.MemberDAOImpl;
import project.service.CommonService;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;

// <진료 예약 페이지> 로그인한 회원의 진료예약 (입력: 진료과_콤보박스, 진료날짜_데이트피커, 진료시간_콤보박스) (버튼: 확인, 취소)

public class MyResController extends Controller implements Initializable{
	private Parent root;
	private MyResService myResServ;
	private CommonService commonServ;
	private MemberDAO dao;

	private static String id;


	// 진료가능여부 확인을 위해 저장 (예약가능 예약마감 표시 기능)
	private int value1;		// 진료과
	private String value2;		// 진료날짜

	//데이트피커에서 선택된 날짜를 인트형으로 저장할때 사용 (오늘날짜랑 크기비교)
	private int selectedDate;

	// 날짜 표기 변환 (데이트 피커에서 선택된 값을 변환할때 사용)
	String pattern = "yyyyMMdd";	
	DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(pattern);

	@FXML DatePicker cmbDate;
	@FXML Button btnResOk;
	@FXML ComboBox<String> cmbJinryo;
	@FXML ComboBox<String> cmbTime;

	// 오늘 날짜
	LocalDate now = LocalDate.now();	      
	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");         
	String formatedNow = now.format(formatter);  
	int today = Integer.parseInt(formatedNow);

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		dao = new MemberDAOImpl();
		myResServ = new MyResServiceImpl();
		commonServ = new CommonServiceImpl();

		// 시간선택 콤보박스에 넣어줄 값 미리 저장
		String[] items1 = {"09:30", "10:30", "11:30", "13:30", "14:30", "15:30"};

		// 진료과에 마우스 찍었을때 진료과 콤보박스에 예약되지 않은 진료과들을 추가
		cmbJinryo.setOnMouseClicked(new EventHandler<MouseEvent>() 
		{
			@Override
			public void handle(MouseEvent event)  
			{
				// 우선 먼저 콤보박스 아이템들을 초기화해준다. (이전에 추가된 아이템들을 지워줌)
				cmbJinryo.getItems().clear();

				// 해당진료과로 예약내역이 있는지 확인 (추가진료예약시에 사용) 없으면 true
				// 예약내역 없으면 콤보박스에 선택아이템으로 저장
				if(dao.noJinryo(id, 1)) {
					cmbJinryo.getItems().add("정형외과");
				}
				if(dao.noJinryo(id, 2)) {
					cmbJinryo.getItems().add("이비인후과");
				}
				if(dao.noJinryo(id, 3)) {
					cmbJinryo.getItems().add("내과");
				}
			}
		});

		// 진료과 선택되었을때 메서드처리로 해당되는 숫자값으로 저장
		cmbJinryo.setOnAction(new EventHandler<ActionEvent>() 
		{
			@Override
			public void handle(ActionEvent event) 
			{
				
				try {
					// 선택된 진료과를 저장
					value1 = getComboBoxJinryo();
				} catch (NullPointerException e1) {
					System.out.println("진료과가 재선택되었습니다.");
				}
				
			}
		});



		// 시간 콤보박스에 마우스 클릭할때 (아이템을 지우고 새로 넣어주고 한다)
		cmbTime.setOnMouseClicked(new EventHandler<MouseEvent>() 
		{
			@Override
			public void handle(MouseEvent event) 
			{
				// 우선 먼저 콤보박스 아이템들을 초기화해준다. (이전에 추가된 아이템들을 지워줌)
				cmbTime.getItems().clear();

				// 이전에 모든시간마감된 경우를 고려해서 버튼을 활성화시킴
				if(selectedDate<=today) {
					System.out.println("오늘 또는 과거날짜를 선택되어있습니다.");
					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setTitle("날짜 선택 오류");
					alert.setHeaderText("오늘날짜 또는 과거날짜가 선택되어있는 경우 시간조회 불가.");
					alert.setContentText("내일 이후날짜로 진료 날짜를 먼저 선택해주세요.");
					alert.showAndWait();

				} else {
					if(value1!=0&&value2!=null) {	// 진료과와 진료날짜가 모두 선택되어있는 경우
						//선택된 날짜에 문제가없고 진료과도 선택되어있으므로 버튼 활성화
						btnResOk.setDisable(false);

						int cnt=0;	// 숫자 더해가면서 모든 시간 마감된것 카운트용
						for(int i=1;i<7;i++) {

							// 진료 가능여부 확인. : 예약 가능시 true
							if(dao.findSameRes(value1,value2,i)) {	
								// 예약가능으로 조회된 숫자의 순서에맞는 진료시간 배열주머니에서 가져온 값을 시간콤보박스에 저장 및 출력
								cmbTime.getItems().add("예약가능: "+items1[i-1]);
							} else {
								// 진료가능여부 확인할때 예약불가능한 경우가 6번이되면
								// for문 빠져나갔을때 모든시간 마감표시하기 위함.
								cnt+=1;	

								// 예약불가능으로 조회된 숫자의 순서에맞는 진료시간 배열주머니에서 가져온 값을 시간콤보박스에 저장 및 출력
								cmbTime.getItems().add("예약마감: "+items1[i-1]);
							}
						}
						// 예약가능한 시간이 없는경우 모든시간 마감 표기하면서
						// 예약하기 확인버튼을 비활성화.
						if(cnt>=6) {
							cmbTime.getItems().add("모든시간 마감입니다.");
							btnResOk.setDisable(true);

						}
					} 
				}
			}
		});

		// 데이트 피커에서 날짜 선택시 출력
		cmbDate.setOnAction(new EventHandler<ActionEvent>() 
		{
			@Override
			public void handle(ActionEvent event) 
			{
				LocalDate date = cmbDate.getValue();
				System.out.println("선택한 날짜: " + dateFormatter.format(date));
				selectedDate = Integer.parseInt(dateFormatter.format(date));
				System.out.println("오늘 날짜 : " + today);

				//선택된 날짜가 오늘 또는 과거날짜인경우
				if(selectedDate<=today) {
					System.out.println("오늘 또는 과거날짜를 선택하셨습니다.");
					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setTitle("날짜 선택 오류");
					alert.setHeaderText("오늘날짜 또는 과거날짜는 선택 불가능합니다.");
					alert.setContentText("원하시는 진료 날짜를 다시 선택해주세요.");
					alert.showAndWait();
					btnResOk.setDisable(true);	// 버튼 비활성화 (불가능한 날짜로 예약방지)
					cmbDate.requestFocus();

				} else {	// 내일이후 날짜 선택한경우 콘솔에 출력
					System.out.println("내일 이후 날짜를 선택하셨습니다. 확인 버튼이 활성화됩니다.");
					// 진료가능여부 확인을 위해 저장 (예약가능 예약마감 표시 기능)
					value2 = getDatePicker();	
					btnResOk.setDisable(false);	// 버튼 활성화
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
		Stage page = (Stage) root.getScene().getWindow();
		page.close();	

		// 마이페이지(진료예약,예약확인버튼 있는페이지) 다시 띄우기 
		Stage s = new Stage(); 
		root=commonServ.showWindow(s, "../fxml/Mypage.fxml");
		s.setX(450);
		s.setY(110);

		// <마이페이지> 좌측 상단 표기
		Label myPageId = (Label) root.lookup("#myPageId");

		// 마이페이지 좌상단에 이름을 출력해주기위해서 이름을 가져옴.
		Member member = dao.select(id);
		String userName=member.getUserName();

		myPageId.setText(userName+"님 환영합니다!");


	}

	// 콤보박스_ 선택된 진료과를 이름이아니라 숫자로 반환해주는 메서드
	public int getComboBoxJinryo() {
		if(cmbJinryo==null) {
			return -1;
		}
		String value = cmbJinryo.getValue().toString();
		if(value.equals("정형외과")) {
			return 1;
		} else if(value.equals("이비인후과")){
			return 2;
		}else if(value.equals("내과")){
			return 3;
		}else {
			return 4;
		}
	}

	// 데이트피커 (예약날짜)
	public String getDatePicker() {

		DatePicker cmbDate = (DatePicker)root.lookup("#cmbDate");

		String value = cmbDate.getValue().toString();
		return value;
	}


	public static String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}







}
