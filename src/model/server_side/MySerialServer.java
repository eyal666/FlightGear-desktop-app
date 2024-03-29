package model.server_side;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;

public class MySerialServer implements Server {

	private volatile boolean stop;
	
	public MySerialServer() {
		this.stop=false;
	}
	

	public void runServer(int port, ClientHandler ch) throws Exception {
		ServerSocket server=new ServerSocket(port);
		server.setSoTimeout(1000);
		while(!stop) {
			try {
				Socket aClient=server.accept();
				try {
					ch.handleClient(aClient.getInputStream(), aClient.getOutputStream());
					aClient.getInputStream().close();
					aClient.getOutputStream().close();
					aClient.close();
				} catch (IOException e) {}
			}catch (SocketTimeoutException e) {}
		}
		server.close();
	}
	
	@Override
	public void start(int port, ClientHandler ch) {
		new Thread(()->{
			try {
				runServer(port, ch);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}).start();

	}

	@Override
	public void stop() {
		this.stop=true;
	
	}

	

}
