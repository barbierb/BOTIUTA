package fr.itspower.botiuta;

import fr.itspower.botiuta.server.ThreadPooledServer;

public class BOTIUTA {
	
	public static void main(String[] args) {
		//MailSender.getInstance().testMail();
		
		ThreadPooledServer mts = new ThreadPooledServer(2000);
		new Thread(mts).start();
	}
	
	private static final BOTIUTA instance = new BOTIUTA();
	
	private BOTIUTA() {
		
	}
	
	public static BOTIUTA getInstance() {
		return instance;
	}
}
