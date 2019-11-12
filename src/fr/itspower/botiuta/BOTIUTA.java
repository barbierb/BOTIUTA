package fr.itspower.botiuta;

import fr.itspower.botiuta.mail.MailSender;

public class BOTIUTA {
	
	public static void main(String[] args) {
		MailSender.getInstance().testMail();
	}
	
	private static final BOTIUTA instance = new BOTIUTA();
	
	private BOTIUTA() {
		
	}
	
	public static BOTIUTA getInstance() {
		return instance;
	}
}
