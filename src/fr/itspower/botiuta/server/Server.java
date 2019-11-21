package fr.itspower.botiuta.server;

import java.io.IOException;
import java.net.BindException;
import java.net.ServerSocket;

public abstract class Server implements Runnable {

    private final int PORT;
    
	protected ServerSocket serverSocket = null;
	protected Thread runningThread = null;
    protected boolean isStopped = false;

    /**
	 * Construit le serveur héritable qui va écouter le port spécifié
	 * 
	 * @param id - l'identifiant utilisateur
	 * @param groupe - le groupe si l'utilisateur en a déjà choisi un
	 */
    protected Server(int port) {
    	this.PORT = port;
    }

    /**
     * Permet de saovir si le sokcet d'écoute est fermé
     * @return boolean
     */
    protected synchronized boolean isStopped() {
        return this.isStopped;
    }
    
    /**
     * Ferme le socket d'écoute du serveur
     */
    protected synchronized void stop() {
        this.isStopped = true;
        try {
            this.serverSocket.close();
        } catch (IOException e) {
            throw new Error("erreur lors de la fermeture du serveur", e);
        }
    }
    
    /**
     * Ouvre le socket d'écoute du serveur sur le port spécifié
     */
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
