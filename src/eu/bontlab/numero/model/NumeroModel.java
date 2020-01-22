package eu.bontlab.numero.model;

import java.security.InvalidParameterException;

import eu.bontlab.numero.NumeroController;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class NumeroModel {

	private final int NMAX = 100;
	private final int TMAX = 8;	
	private int segreto;
	private IntegerProperty tentativiRimasti;
	private boolean inGioco= false;
	
	public NumeroModel() {
		inGioco = false;
		tentativiRimasti = new SimpleIntegerProperty();
	}
	/**
	 * Avvia nuova partita
	 */
	public void newGame( ) {
		this.inGioco = true;
    	this.tentativiRimasti.set(TMAX);
    	this.segreto = (int)(Math.random() *NMAX)+1;
	}
	
	/**
	 * Metodo per effettuare un tentativo
	 * 
	 * @param numeroTentato
	 * @return 1 se il tentativo e' troppo alto, -1 se e' troppo basso, 0 se l'utente ha indovinato
	 */
	public int tentativo(int numeroTentato) {
		//controllo se la partita e' in corso
		if (!inGioco) {
			throw new IllegalStateException("La partita e' terminata");
		}
		
		//controllo se l'input e' nel range valido
		if (!tentativoValido(numeroTentato)) {
    		//txtMessaggi.appendText("Il numero deve essere un valore intero tra 0 e 100\n");
			throw new InvalidParameterException(String.format("Il numero deve essere un valore intero tra %d e %d", 1, NMAX));
    	}
		
		//gestisci tentativo
		this.tentativiRimasti.set(this.tentativiRimasti.get()-1);
		if (numeroTentato==this.segreto) {
			this.inGioco = false;
			return 0;
		} else if(this.tentativiRimasti.get()==0) {
			this.inGioco=false;
		}
		
		
		if (numeroTentato>this.segreto) {
			return 1;
		} else {return -1;}
	}
	
	public int getTMAX() {
		return TMAX;
	}
	public boolean isInGioco() {
		return inGioco;
	}
	
	public int getSegreto() {
		return segreto;
	}
	
	public boolean tentativoValido(int t) {
		if ((t<1) || (t>NMAX)) {return false;} else {return true;}
	}
	
	public final IntegerProperty tentativiRimastiProperty() {
		return this.tentativiRimasti;
	}
	
	public final int getTentativiRimasti() {
		return this.tentativiRimastiProperty().get();
	}
	
	public final void setTentativiRimasti(final int tentativiRimasti) {
		this.tentativiRimastiProperty().set(tentativiRimasti);
	}
	
}
