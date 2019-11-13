package fr.itspower.botiuta.server;

import java.io.IOException;
import java.net.BindException;
import java.net.ServerSocket;

import fr.itspower.botiuta.utils.PrivateData;

public abstract class Server implements Runnable {

	protected ServerSocket serverSocket = null;
	protected Thread runningThread = null;
	
    protected boolean isStopped = false;
    private final int PORT;

    protected Server(int port) {
    	this.PORT = port;
    }

    protected synchronized boolean isStopped() {
        return this.isStopped;
    }

    protected synchronized void stop() {
        this.isStopped = true;
        try {
            this.serverSocket.close();
        } catch (IOException e) {
            throw new Error("erreur lors de la fermeture du serveur", e);
        }
    }

    protected void start() {
        try {
            this.serverSocket = new ServerSocket(PORT);
        } catch(BindException e) {
            throw new Error("le port "+PORT+" est deja utilise.", e);
        } catch(IOException e) {
            throw new Error("le port "+PORT+" ne peut etre ouvert.", e);
        }
    }

}
