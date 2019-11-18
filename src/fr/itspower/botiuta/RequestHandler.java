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
			
            System.out.println("H< " + line);
            
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
                System.out.println("JSON "+jobj.getJSONObject("message"));
                System.out.println("JSON "+jobj.getJSONObject("message").getString("text"));
                
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
