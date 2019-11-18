package fr.itspower.botiuta;

import fr.itspower.botiuta.server.ThreadPooledServer;

public class BOTIUTA {
	
	public static void main(String[] args) throws InterruptedException {
		//MailSender.getInstance().testMail();
		
		ThreadPooledServer mts = new ThreadPooledServer(2000);
		Thread t = new Thread(mts);
		t.start();
		Thread.sleep(1000 * 700);
		System.exit(1);
	}
	
	private static final BOTIUTA instance = new BOTIUTA();
	
	private BOTIUTA() {
		
	}
	
	public static BOTIUTA getInstance() {
		return instance;
	}
}
