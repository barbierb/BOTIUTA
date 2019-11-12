package fr.itspower.botiuta;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
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
			OutputStream out = request.getOutputStream();
			
			String str;
			while ((str = bis.readLine()) != null) {
				sb.append(str);
				System.out.println(str);
			}
			out.write(("HTTP/1.1 200 OK\n\n").getBytes());
			
			out.close();
			bis.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		System.out.println(sb.toString());
	}

}
