package fr.itspower.botiuta;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import org.json.JSONException;
import org.json.JSONObject;

public class RequestHandler implements Runnable {

	private final String GET = "GET";
	private final String POST = "POST";

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
				
				
				
			} else if(line.startsWith(POST)) {
				
				
				
			}
			
			
            System.out.println("H< " + line);
            
            

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
