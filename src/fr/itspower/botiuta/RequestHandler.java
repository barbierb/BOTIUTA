package fr.itspower.botiuta;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

public class RequestHandler implements Runnable {

	private Socket request;

	public RequestHandler(Socket request) {
		this.request = request;
	}

	@Override
	public void run() {
		StringBuilder sb = new StringBuilder();

		try {
			
			BufferedReader bis = new BufferedReader(new InputStreamReader(request.getInputStream()));
			PrintWriter out = new PrintWriter(request.getOutputStream());
			
			String str;
			while (!(str = bis.readLine()).isEmpty()) {
				sb.append(str).append('\n');
			}
			
		    out.println("HTTP/1.0 200 OK");
		    out.println("Content-Type: text/html");
		    out.println("Server: SimpleWebServer");
		    out.println("");
		    out.println("<CENTER><H1>hello world<H1></CENTER>");
		    out.flush();
			
			out.close();
			bis.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		System.out.println(sb.toString());
	}

}
