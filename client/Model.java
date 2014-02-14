package client;

import java.io.*;
import java.net.*;

import Data.UserBean;

public class Model 
{
	private int board[][] = new int[3][3];
	final private static int directorySocket = 25200;
	
	private String hostName;
	private String userName;
	
	private final String JOIN = "JOIN";
	private final String LEAVE = "LEAVE";
	
	public Model()
	{
		try {
			hostName = InetAddress.getLocalHost().getHostName();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Could not get hostname");
		}
	}
	public void joinServer(String userName)
	{
		try {
			Socket toServer = new Socket("localhost", directorySocket);
			ObjectOutputStream outToServer = new ObjectOutputStream(toServer.getOutputStream());
			
			this.userName = userName;
			
			ServerMessage m = new ServerMessage();
			m.setCommand(JOIN);
			m.setUser(new UserBean(this.hostName, userName));
			
			outToServer.writeObject(m);

			outToServer.close();
			toServer.close();
			
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void leaveServer()
	{
		try {
				Socket toServer = new Socket("localhost", directorySocket);
				ObjectOutputStream outToServer = new ObjectOutputStream(toServer.getOutputStream());
				
				ServerMessage m = new ServerMessage();
				m.setCommand(LEAVE);
				m.setUser(new UserBean(this.hostName,this.userName)); //Username could be null but shouldn't read it on server
				
				outToServer.writeObject(m);
				
				outToServer.close();
				toServer.close();
				
			} catch (UnknownHostException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
	}
}
