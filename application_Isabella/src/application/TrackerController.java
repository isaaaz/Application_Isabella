package application;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class TrackerController implements Initializable {

    @FXML
    private Button btnEffacer;

    @FXML
    private TableColumn<?, Double> tempsColumn;

    @FXML
    private TableColumn<?, String> noteColumn;

    @FXML
    private TextField txtsujet;

    @FXML
    private Button btnModifier;

    @FXML
    private TextField txttemps;

    @FXML
    private ComboBox<String> cbotype;

    @FXML
    private Button btnAjouter;

    @FXML
    private Button btn;

    @FXML
    private TableColumn<?, String> sujetColumn;

    @FXML
    private TableColumn<?, String> travailsColumn;
    
    
    // Liste pour les types de travails - cette liste permettra de donner les valeurs du comboBox

 	private ObservableList<String> list = (ObservableList<String>) FXCollections.observableArrayList("Projets","Devoirs","Étudier","Autres");

 	

	// Placer les étudiants dans un observable list 

	public ObservableList<Tracker> travailsData = FXCollections.observableArrayList();


	// Creer une methode pour accéder à la liste des étudiants

	public ObservableList<Tracker> gettravailsData()
	{
		return travailsData;
	}


	@Override
	public void initialize(URL location, ResourceBundle resources) {
		cbotype.setItems(list);
		//attribuer les valeurs aux colonnes du tableView
		sujetColumn.setCellValueFactory(new PropertyValueFactory<>("sujet"));
		travailsColumn.setCellValueFactory(new PropertyValueFactory<>("travails"));
		tempsColumn.setCellValueFactory(new PropertyValueFactory<>("temps"));
		noteColumn.setCellValueFactory(new PropertyValueFactory<>("note"));
		
		
	}


}


