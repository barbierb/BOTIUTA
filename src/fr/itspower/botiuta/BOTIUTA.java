package fr.itspower.botiuta;

public class BOTIUTA {
	
	private static final BOTIUTA instance = new BOTIUTA();
	
	private BOTIUTA() {
		
	}
	
	public static BOTIUTA getInstance() {
		return instance;
	}
}
