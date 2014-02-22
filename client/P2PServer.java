package client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class P2PServer extends Thread
{
	private int serverType;
	private int socketNumber;
	private View v;
	
	public P2PServer(int serverType, int socketNumber, View v)
	{
		this.serverType = serverType;
		this.socketNumber = socketNumber;
		this.v = v;
	}
	
	/**
	 * Thread entry point
	 */
	public synchronized void run()
	{
		if(serverType == 0)
		{
			//Control
			runControlServer();
		}else if(serverType == 1)
		{
			//Game server
		}
	}
	
	private void runControlServer()
	{
		ServerSocket controlSocket;
		try {
			controlSocket = new ServerSocket(socketNumber);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
