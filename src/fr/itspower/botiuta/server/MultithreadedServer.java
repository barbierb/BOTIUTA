package fr.itspower.botiuta.server;

import java.io.IOException;
import java.net.Socket;

import fr.itspower.botiuta.RequestHandler;

public class MultithreadedServer extends Server {
	
	public MultithreadedServer(int port) {
		super(port);
	}
	
    public void run() {
    	
        synchronized(this) {
            this.runningThread = Thread.currentThread();
        }
        
        start();
        
        while(!isStopped()) {
            Socket request = null;
            try {
            	request = serverSocket.accept();
            	new Thread(new RequestHandler(request)).start();
            } catch (IOException e) {
                if(isStopped()) {
                	throw new Error("le serveur est arrete", e);
                }
                throw new Error("probleme lors de l initialisation d une connexion", e);
            }
            
        }
        System.out.println("serveur stoppe");
    }

}
