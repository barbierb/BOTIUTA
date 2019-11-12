package fr.itspower.botiuta.server;

import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import fr.itspower.botiuta.RequestHandler;

public class ThreadPooledServer extends Server {
	
	protected ExecutorService threadPool = Executors.newFixedThreadPool(1);
	
	public ThreadPooledServer(int port) {
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
            	this.threadPool.execute(new RequestHandler(request));
            } catch (IOException e) {
                if(isStopped()) {
                	throw new Error("le serveur est arrete", e);
                }
                throw new Error("probleme lors de l initialisation d une connexion", e);
            }
            
        }
        System.out.println("serveur stoppe");
        this.threadPool.shutdown();
    }

}
