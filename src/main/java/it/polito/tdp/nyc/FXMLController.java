package it.polito.tdp.nyc;

import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import it.polito.tdp.nyc.model.Model;
import it.polito.tdp.nyc.model.NTA;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;


import it.polito.tdp.nyc.model.Arco;

public class FXMLController {

	Model model;
	
    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="btnAdiacenti"
    private Button btnAdiacenti; // Value injected by FXMLLoader

    @FXML // fx:id="btnCreaGrafo"
    private Button btnCreaGrafo; // Value injected by FXMLLoader

    @FXML // fx:id="btnCreaLista"
    private Button btnCreaLista; // Value injected by FXMLLoader

    @FXML // fx:id="clPeso"
    private TableColumn<?, ?> clPeso; // Value injected by FXMLLoader

    @FXML // fx:id="clV1"
    private TableColumn<?, ?> clV1; // Value injected by FXMLLoader

    @FXML // fx:id="clV2"
    private TableColumn<?, ?> clV2; // Value injected by FXMLLoader

    @FXML // fx:id="cmbBorough"
    private ComboBox<String> cmbBorough; // Value injected by FXMLLoader

    @FXML // fx:id="tblArchi"
    private TableView<?> tblArchi; // Value injected by FXMLLoader

    @FXML // fx:id="txtDurata"
    private TextField txtDurata; // Value injected by FXMLLoader

    @FXML // fx:id="txtProb"
    private TextField txtProb; // Value injected by FXMLLoader

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader

    @FXML
    void doAnalisiArchi(ActionEvent event) {
    	
    	this.txtResult.clear();
    	
    	if(!this.model.isGrafoLoaded()) {
    		
    		this.txtResult.setText("Crea grafo prima!");
    		return;
    		
    	}
    	
    	List<Arco> soluzione = this.model.analisiArchi();

    	this.txtResult.appendText("PESO MEDIO: " + this.model.getMedia() + "\n");
    	this.txtResult.appendText("ARCHI CON PESO MAGGIORE DEL PESO MEDIO: " + soluzione.size() + "\n");
    	
    	for(Arco a : soluzione) {
    		
    		this.txtResult.appendText(a + "\n");
    	}
    	
    	
    }

    @FXML
    void doCreaGrafo(ActionEvent event) {
	
    	this.txtResult.clear();
        
    	String borough = this.cmbBorough.getValue();
    	
    	if(borough == null) {
    		
    		this.txtResult.appendText("Seleziona una voce! \n");
    		return;
    		
    	}

    	this.model.creaGrafo(borough);
    	
    	this.txtResult.appendText("Grafo creato! \n");
    	this.txtResult.appendText("# Vertici: " + this.model.getNNodes() + "\n");
    	this.txtResult.appendText("# Archi: " + this.model.getNArchi() + "\n");
        
        
    }

    @FXML
    void doSimula(ActionEvent event) {
    	
    	this.txtResult.clear();
    	
    	String inputProb = this.txtProb.getText();
    	
    	double probShare = 0.0;
    	
    	try {
    		
    		probShare = Double.parseDouble(inputProb);
    		
    	} catch(NumberFormatException e) {
    		
    		this.txtResult.appendText("Inserisci un valore double alla probabilità");
    		
    		return;
    		
    	}
    	
    	
    	
    	String inputDurata = this.txtDurata.getText();
    	
    	int durata = 0;
    	
    	try {
    		
    		durata = Integer.parseInt(inputDurata);
    		
    	} catch(NumberFormatException e) {
    		
    		this.txtResult.appendText("Inserisci un valore intero alla durata");
    		
    		return;
    		
    	}
    	
    	Map<NTA, Integer> condivisioni = this.model.Simula(probShare, durata);
    	
    	for(NTA n : condivisioni.keySet()) {
    		
    		this.txtResult.appendText(n.getNTACode() + " " + condivisioni.get(n) + "\n");
    		
    	}
    	

    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert btnAdiacenti != null : "fx:id=\"btnAdiacenti\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnCreaGrafo != null : "fx:id=\"btnCreaGrafo\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnCreaLista != null : "fx:id=\"btnCreaLista\" was not injected: check your FXML file 'Scene.fxml'.";
        assert clPeso != null : "fx:id=\"clPeso\" was not injected: check your FXML file 'Scene.fxml'.";
        assert clV1 != null : "fx:id=\"clV1\" was not injected: check your FXML file 'Scene.fxml'.";
        assert clV2 != null : "fx:id=\"clV2\" was not injected: check your FXML file 'Scene.fxml'.";
        assert cmbBorough != null : "fx:id=\"cmbBorough\" was not injected: check your FXML file 'Scene.fxml'.";
        assert tblArchi != null : "fx:id=\"tblArchi\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtDurata != null : "fx:id=\"txtDurata\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtProb != null : "fx:id=\"txtProb\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Scene.fxml'.";

        
    }
    
    public void setModel(Model model) {
    	this.model = model;
    	
    	this.cmbBorough.getItems().addAll(this.model.getListaBorough());
    }

}
