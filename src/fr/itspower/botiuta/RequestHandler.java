package fr.itspower.botiuta;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import fr.itspower.botiuta.utils.PrivateData;

public class RequestHandler implements Runnable {

	private static final String GET = "GET";
	private static final String POST = "POST";
	private static final String SPACE = " ";
	private static final String INTERROGATION = "\\?"; // '\\' permet l'acceptation de '?' par regex.
	private static final String ETCOMMERCIAL = "&";
	private static final String EGAL = "=";

	private static final String AUTH_MODE_KEY = "hub_mode";
	private static final String AUTH_TOKEN_KEY = "hub_verify_token";
	private static final String AUTH_CHALLENGE_KEY = "hub_challenge";
	private static final String AUTH_VERIFY = "verify";
	
	

	@SuppressWarnings("unused")
	private Socket request;
	private BufferedReader in;
	private PrintWriter out;

	public RequestHandler(Socket request) {
		this.request = request;
		try {
			in = new BufferedReader(new InputStreamReader(request.getInputStream()));
			out = new PrintWriter(request.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println();
		System.out.println("< NEW REQUESTHANDLER "+request.getInetAddress());
	}

	@Override
	public void run() {
		
		String line;
		
		try {
			line = in.readLine();
			if(!line.startsWith(GET) && !line.startsWith(POST)) {
				sendBody(400);
				close();
				return;
			}
			
			if(line.startsWith(GET)) {
				
				Map<String, String> paires = new HashMap<String, String>();
				
			    // enleve le chemin demandé de la requete HTTP, le protocol et ne garde que les parametres.
				String ligne_params = line.split(SPACE)[1].split(INTERROGATION)[1];
				String[] params_splitted = ligne_params.split(ETCOMMERCIAL);

			    for (String param : params_splitted) {
			    	String[] pair = param.split(EGAL);
			    	paires.put(pair[0], pair[1]);
			    }
			    
			    // Vérifie que le serveur facebook demande bien une association avec l'application.
			    if(!paires.containsKey(AUTH_MODE_KEY) || !paires.get(AUTH_MODE_KEY).equals(AUTH_VERIFY)) {
			    	sendBody(400);
					close();
					return;
			    }
			    
			    // Vérifie si les mots de passe application et serveur sont égaux.
			    if(!paires.containsKey(AUTH_TOKEN_KEY) || !paires.get(AUTH_TOKEN_KEY).equals(PrivateData.AUTH_PASSWORD)) {
			    	sendBody(400);
					close();
					return;
			    }
			    
			    // Vérifie que le serveur a bien envoyé son mot de passe hand-check.
			    if(!paires.containsKey(AUTH_CHALLENGE_KEY)) {
			    	sendBody(400);
					close();
					return;
			    }
			    
		    	sendBody(200);
		    	out.println(paires.get(AUTH_CHALLENGE_KEY));
				
			} else if(line.startsWith(POST)) {
				
				int postDataI = -1;
	            while ((line = in.readLine()) != null && (line.length() != 0)) {
	                System.out.println("H< "+line);
	                if (line.indexOf("Content-Length:") > -1) {
	                    postDataI = new Integer(line.substring(line.indexOf("Content-Length:") + 16,line.length())).intValue();
	                }
	            }

	            String postData = "";
	            if (postDataI > 0) {
	                char[] charArray = new char[postDataI];
	                in.read(charArray, 0, postDataI);
	                postData = new String(charArray);
	                System.out.println("D< "+postData);

	                JSONObject jobj = new JSONObject(postData);

	                System.out.println("JSON "+jobj);
	                //System.out.println("JSON "+jobj.getJSONObject("message"));
	                //System.out.println("JSON "+jobj.getJSONObject("message").getString("text"));

	            }
				
			}
			
            
            

			close();
			
		} catch (IOException e) {
			e.printStackTrace();
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
	private void close() throws IOException {
		out.flush();
		in.close();
		out.close();
	}

	private void sendBody(int code) {
		System.out.println("SENDBODY "+code);
		if(code == 200) {
			out.println("HTTP/1.0 200 OK");
		} else if(code == 400) {
			out.println("HTTP/1.1 400 Bad Request");
		} else {
			out.println("HTTP/1.1 403 Forbidden");
		}
	    out.println("Content-Type: text/html");
	    out.println("Server: @BOTIUTA");
	    out.println("");
	}

}
