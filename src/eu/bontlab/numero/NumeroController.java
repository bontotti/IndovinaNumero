package eu.bontlab.numero;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

public class NumeroController {
	
	private final int NMAX = 100;
	private final int TMAX = 8;
			
	private int segreto;
	private int tentativiFatti;
	private boolean inGioco= false;

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
    	this.inGioco = true;
    	this.tentativiFatti= 0;
    	this.segreto = (int)(Math.random() *NMAX)+1;
    	boxControlloPartita.setDisable(true);
    	boxControlloTentativo.setDisable(false);
    	txtMessaggi.clear();
    	txtTentativo.clear();
    	txtRimasti.setText(Integer.toString(this.TMAX));
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
    	if ((numeroTentato<0) || (numeroTentato>100)) {
    		txtMessaggi.appendText("Il numero deve essere un valore intero tra 0 e 100\n");
    		return;
    	}
    	tentativiFatti++;
    	if (numeroTentato==segreto) {
    		txtMessaggi.appendText("Complimenti hai indovinato in "+tentativiFatti+" tentativi!\n");
    		chiudiPartita();
    	} else {
    	
	    	if (tentativiFatti==TMAX) {
	    		txtMessaggi.appendText("Hai perso! Il numero segreto era:"+this.segreto+"\n");
	    		chiudiPartita();
	    	} else {
		    	if (numeroTentato<segreto)  {
		    		txtMessaggi.appendText("I numero segreto e' piu' alto, riprova!\n");
		    		} else {
		    			txtMessaggi.appendText("I numero segreto e' piu' basso, riprova!\n");
		    		}
		    	txtRimasti.setText(Integer.toString(TMAX-tentativiFatti));
	    	}
    	}
    }

    void chiudiPartita() {
    	boxControlloPartita.setDisable(false);
		boxControlloTentativo.setDisable(true);
		this.inGioco=false;
		return;
    }
    
    @FXML
    void initialize() {
        assert boxControlloPartita != null : "fx:id=\"boxControlloPartita\" was not injected: check your FXML file 'Numero.fxml'.";
        assert txtRimasti != null : "fx:id=\"txtRimasti\" was not injected: check your FXML file 'Numero.fxml'.";
        assert boxControlloTentativo != null : "fx:id=\"boxControlloTentativo\" was not injected: check your FXML file 'Numero.fxml'.";
        assert txtTentativo != null : "fx:id=\"txtTentativo\" was not injected: check your FXML file 'Numero.fxml'.";
        assert txtMessaggi != null : "fx:id=\"txtMessaggi\" was not injected: check your FXML file 'Numero.fxml'.";

    }
}

