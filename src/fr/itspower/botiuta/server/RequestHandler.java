package fr.itspower.botiuta.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class RequestHandler implements Runnable {

    private ServerSocket serverSocket = null;
    private Thread runningThread = null;
    private boolean isStopped = false;

    private RequestHandler() {
    	
    }

    public void run() {
    	
        synchronized(this) {
            this.runningThread = Thread.currentThread();
        }
        
        start();
        
        while(!isStopped()) {
            Socket clientSocket = null;
            try {
                clientSocket = this.serverSocket.accept();
            } catch (IOException e) {
                if(isStopped()) {
                    System.out.println("Server Stopped.") ;
                    return;
                }
                throw new RuntimeException(
                    "Error accepting client connection", e);
            }
            new Thread(
            	//clientSocket
            ).start();
        }
        System.out.println("Server Stopped.") ;
    }


    private synchronized boolean isStopped() {
        return this.isStopped;
    }

    public synchronized void stop(){
        this.isStopped = true;
        try {
            this.serverSocket.close();
        } catch (IOException e) {
            throw new Error("erreur lors de la fermeture", e);
        }
    }

    private void start() {
        try {
            this.serverSocket = new ServerSocket(PORT);
        } catch (IOException e) {
            throw new Error("le port "+PORT+" ne peut etre ouvert.", e);
        }
    }

}
