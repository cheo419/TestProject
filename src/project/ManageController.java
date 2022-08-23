package project;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import project.DAO.MemberDAO;
import project.DAO.MemberDAOImpl;
import project.service.LoginService;

public class ManageController extends Controller implements Initializable{

	private Parent root;
	private LoginService loginServ;
	MemberDAO dao;
//	static String id;

	@FXML private TableView<Member> manageTable;
	@FXML private TableColumn<Member, String>  nameCol;
	@FXML private TableColumn<Member, String> numberCol;
	@FXML private TableColumn<Member, String>  idCol;
	@FXML private TableColumn<Member, String>  pwCol;
	@FXML private TableColumn<Member, String>  resCol;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
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
		public void backLogin(ActionEvent event) {
			loginServ.backLogin(root,event);
		}

	

}
