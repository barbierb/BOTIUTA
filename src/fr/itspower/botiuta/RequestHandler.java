package fr.itspower.botiuta;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

import org.json.JSONObject;

public class RequestHandler implements Runnable {

	private final String GET = "GET";
	private final String POST = "POST";

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
	}

	@Override
	public void run() {
		StringBuilder sb = new StringBuilder();

		try {
			
			String str;
			while (!(str = in.readLine()).isEmpty()) {
				sb.append(str).append('\n');
			}
			
			if(str.startsWith(GET)) {
				sendBody(200);
			    out.println("42");
			    
			} else if(str.startsWith(POST)) {
				sendBody(200);
				
				JSONObject jobj = new JSONObject();
				
			    
			} else {
				sendBody(403);
			}
			
		    out.flush();
			in.close();
			out.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		System.out.println(sb.toString());
	}
	
	private void sendBody(int code) {
		System.out.println("SENDBODY "+code);
		if(code == 200) {
			out.println("HTTP/1.0 200 OK");
		} else {
			out.println("HTTP/1.1 403 Forbidden");
		}
	    out.println("Content-Type: text/html");
	    out.println("Server: @BOTIUTA");
	    out.println("");
	}

}
