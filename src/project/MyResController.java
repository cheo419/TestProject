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
import javafx.stage.Stage;
import project.DAO.MemberDAO;
import project.DAO.MemberDAOImpl;
import project.service.CommonService;
import project.service.LoginService;

public class MyResController extends Controller implements Initializable{
	
	private Parent root;
	private LoginService loginServ;
	private CommonService commonServ;
	MemberDAO dao;
//	static String id;
	
	@FXML private TableView<Member> myResTable;
	@FXML private TableColumn<Member, String>  nameCol;
	@FXML private TableColumn<Member, String> numberCol;
	@FXML private TableColumn<Member, String>  idCol;
	@FXML private TableColumn<Member, String>  pwCol;
	@FXML private TableColumn<Member, String>  resCol;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		dao = new MemberDAOImpl();
		List<Member> memberList = dao.selectAdmin();
		
		myResTable.setItems(getProduct(memberList));

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

	private ObservableList<Member> getProduct(List<Member> memberList) {
		ObservableList<Member> adminList =
				FXCollections.observableArrayList(memberList);
		return adminList;
	}

	@Override
	public void setRoot(Parent root) {
		this.root = root;
		
	}

	public void resCheck(Parent root, ActionEvent event) {
		
		Stage stage = (Stage) root.getScene().getWindow();
		stage.close();
	}
	
	

}
