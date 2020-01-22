package eu.bontlab.numero;

import java.net.URL;
import java.util.ResourceBundle;

import eu.bontlab.numero.model.NumeroModel;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

public class NumeroController {
	
	private NumeroModel model;
	

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private HBox boxControlloPartita;

    @FXML
    private TextField txtRimasti;

    @FXML
    private HBox boxControlloTentativo;

    @FXML
    private TextField txtTentativo;

    @FXML
    private TextArea txtMessaggi;

    @FXML
    void handleNuovaPartita(ActionEvent event) {
    	
    	boxControlloPartita.setDisable(true);
    	boxControlloTentativo.setDisable(false);
    	txtMessaggi.clear();
    	txtTentativo.clear();
    	//txtRimasti.setText(Integer.toString(model.getTMAX()));
    	
    	//Comunico al modello di inziare una nuova partita
    	model.newGame();
    }

    @FXML
    void handleProvaTentativo(ActionEvent event) {
    	int numeroTentato;
    	try {
    		numeroTentato = Integer.parseInt(txtTentativo.getText());
    	} catch (NumberFormatException e) {
    		txtMessaggi.appendText("Non e' un numero valido\n");
    		return;
    	}
    	
    	if (!model.tentativoValido(numeroTentato)) {
    		txtMessaggi.appendText("Il numero non e' nel range valido\n");
    	}
    	
    	int risultato = model.tentativo(numeroTentato);
    	
    	if (risultato==0) {
    		txtMessaggi.appendText("Complimenti hai indovinato in "+(model.getTMAX()-model.getTentativiRimasti())+" tentativi!\n");
    		chiudiPartita();
    	} else {
		    	if(risultato<0) {
		    		txtMessaggi.appendText("I numero segreto e' piu' alto, riprova!\n");
		    	} else {
					txtMessaggi.appendText("I numero segreto e' piu' basso, riprova!\n");
		    	}
    	}
    	
    	//txtRimasti.setText(Integer.toString(model.getTentativiRimasti()));
	    	
    	if (!model.isInGioco()) {
    		if (risultato!=0) {
    			txtMessaggi.appendText("Hai perso!");
    			txtMessaggi.appendText(String.format("\nIl numero segreto era: %d", model.getSegreto()));
    		}
    		chiudiPartita();
    	}
    }

    void chiudiPartita() {
    	boxControlloPartita.setDisable(false);
		boxControlloTentativo.setDisable(true);
    }
    
    @FXML
    void initialize() {
        assert boxControlloPartita != null : "fx:id=\"boxControlloPartita\" was not injected: check your FXML file 'Numero.fxml'.";
        assert txtRimasti != null : "fx:id=\"txtRimasti\" was not injected: check your FXML file 'Numero.fxml'.";
        assert boxControlloTentativo != null : "fx:id=\"boxControlloTentativo\" was not injected: check your FXML file 'Numero.fxml'.";
        assert txtTentativo != null : "fx:id=\"txtTentativo\" was not injected: check your FXML file 'Numero.fxml'.";
        assert txtMessaggi != null : "fx:id=\"txtMessaggi\" was not injected: check your FXML file 'Numero.fxml'.";
    }
    
    public void setModel(NumeroModel model) {
		this.model = model;
		txtRimasti.textProperty().bind(Bindings.convert(model.tentativiRimastiProperty()));
	}
}

