package application;

import java.io.File;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.prefs.Preferences;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class TrackerController implements Initializable {

    @FXML
    private Button btnEffacer;
    
    @FXML
    private Button btnClear;

    @FXML
    private TableColumn<Tracker, Double> tempsColumn;

    @FXML
    private TableColumn<Tracker, String> noteColumn;

    @FXML
    private TableView<Tracker> travailsTable;
    
    @FXML
    private TextField txtsujet;
    
    @FXML
    private TextField txtnote;

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
    private TableColumn<Tracker, String> sujetColumn;

    @FXML
    private TableColumn<Tracker, String> travailsColumn;
    
    
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
		travailsTable.setItems(travailsData);
		
		btnModifier.setDisable(true);
		btnEffacer.setDisable(true);
		btnAjouter.setDisable(true);


		showTracker(null);

		travailsTable.getSelectionModel().selectedItemProperty().addListener((
				observable, oldValue, newValue) -> showTracker (newValue));
	}
	

	// Ajouter
	@FXML
	void ajouter()
	{
		// Vérifier si un champ n'est pas vide
		if(noEmptyInput())
		{
			Tracker tmp=new Tracker();
			tmp.setSujet(txtsujet.getText());
			
			tmp.setTravails(cbotype.getValue());
			
			tmp.setTemps(Double.parseDouble(txttemps.getText()));
			
			
			tmp.setNote(txtnote.getText());
			travailsData.add(tmp);
			clearFields();
		}
		
	}
		// Effacer le contenu des champs
		@FXML
		void clearFields()
		{
			cbotype.setValue(null);
			txtsujet.setText("");
			txttemps.setText("");
			txtnote.setText("");
		}
		

		// Afficher

		public void showTracker(Tracker tracker)
		{
			if(tracker !=null)
			{
				cbotype.setValue(tracker.getTravails());
				txtsujet.setText(tracker.getSujet());
				txtnote.setText(tracker.getNote());
				txttemps.setText(Double.toString(tracker.getTemps()));
				btnModifier.setDisable(false);
				btnEffacer.setDisable(false);
				btnClear.setDisable(false);
			}
			
			else 
			{
				clearFields();
			}
		}
		

		// Mise à jour le Tracker
		@FXML
		public void updateTracker()
		{
			// Vérifier si un champ n'est pas vide
			if(noEmptyInput())
			{
				Tracker tracker = travailsTable.getSelectionModel().getSelectedItem();

				tracker.setSujet(txtsujet.getText());
				tracker.setNote(txtnote.getText());
				tracker.setTemps(Double.parseDouble(txttemps.getText()));
				tracker.setTravails(cbotype.getValue());
				travailsTable.refresh();
			}
		}
		

		// Effacer un travail
		@FXML
		public void deleteTravails()
		{
			int selectedIndex = travailsTable.getSelectionModel().getSelectedIndex();
			if (selectedIndex >= 0)
			{
				Alert alert = new Alert(AlertType.CONFIRMATION);
				alert.setTitle("Effacer");
				alert.setContentText("Confirmer la suppression!");
				Optional<ButtonType> result = alert.showAndWait();
				if(result.get()==ButtonType.OK)
					travailsTable.getItems().remove(selectedIndex);
			}
		}
		
		
		
		// Vérifier champs vides 
		private boolean noEmptyInput()
		{
			String errorMessage="";
			if(txtsujet.getText().trim().equals(""))
			{
				errorMessage+="Le champ sujet ne doit pas être vide! \n";
			}
			if(txtnote.getText()==null||txtnote.getText().length()==0)
			{
				errorMessage+="Le champ note ne doit pas être vide! \n";
			}
			if(txttemps.getText()==null||txttemps.getText().length()==0)
			{
				errorMessage+="Le champ temps ne doit pas être vide! \n";
			}
			if(cbotype.getValue()==null)
			{
				errorMessage+="Le champ type de travails ne doit pas être vide! \n";
			}
			
			if(errorMessage.length()==0) 
			{
				return true;
			}
			else
			{
				Alert alert=new Alert(AlertType.ERROR);
				alert.setTitle("Champs manquants");
				alert.setHeaderText("Compléter les champs manquants");
				alert.setContentText(errorMessage);
				alert.showAndWait();
				return false;
			}
				
		}
		
		

		// Sauvegarde de données -------
		
		//Recuperer le chemin (path) des fichiers si ca existe 
		public File getTravailstFilePath()
		{
			Preferences prefs = Preferences.userNodeForPackage(Main.class);
			String filePath = prefs.get("filePath", null);
			
			if (filePath != null)
			{
				return new File(filePath);
			}
			else
			{
				return null;
			}
		}
		

		// Attribuer un chemin de fichiers
		public void setTravailsFilePath(File file)
		{
			Preferences prefs = Preferences.userNodeForPackage(Main.class);

			if (file != null)
			{
				prefs.put("filePath", file.getPath());
			}
			else
			{
				prefs.remove("filePath");
			}
		}
		

		// Prendre les données de type XML et les convertir en donnés de type JavaFx
		public void loadEtudiantDataFromFile(File file)
		{
			try {
				JAXBContext context = JAXBContext.newInstance(TravailsListWrapper.class);
				Unmarshaller um = context.createUnmarshaller();
				
				TravailsListWrapper wrapper = (TravailsListWrapper) um.unmarshal(file);
				travailsData.clear();
				travailsData.addAll(wrapper.getEtudiants());
				setTravailsFilePath(file);
				
				// Donner le titre du fichier chargé
				Stage pStage=(Stage) travailsTable.getScene().getWindow();
				pStage.setTitle(file.getName());
				
			} catch (Exception e) {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Erreur");
				alert.setHeaderText("Les données n'ont pas été trouées");
				alert.setContentText("Les données ne pouvaient pas être trouvées dans le fichier : \n" + file.getPath());
				alert.showAndWait();
			}
		}
		
		
		
		
		
		
		

	}




