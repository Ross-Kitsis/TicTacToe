package client;

import java.io.*;
import java.net.*;
import java.util.ArrayList;

import Data.UserBean;

public class Model 
{
	private int board[][] = new int[3][3];
	final private static int directorySocket = 25200;
	
	private String hostName;
	private String userName;
	private ArrayList<UserBean> onlineUsers;
	private String server;
	
	private final String JOIN = "JOIN";
	private final String LEAVE = "LEAVE";
	private final String LIST = "LIST";
	private final String TEST = "TEST";
	
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
	public ArrayList<UserBean> listOnlineUsers()
	{
		try{
			Socket toServer = new Socket("localhost",directorySocket);
			ObjectOutputStream outToServer = new ObjectOutputStream(toServer.getOutputStream());
			
			ServerMessage m = new ServerMessage();
			m.setCommand(LIST);
			
			outToServer.writeObject(m);
			
			ObjectInputStream serverInput = new ObjectInputStream(toServer.getInputStream());
			onlineUsers = (ArrayList<UserBean>) serverInput.readObject();
			//Need to receive output fom server
			
			outToServer.close();
			serverInput.close();
			toServer.close();
		}catch(UnknownHostException e)
		{
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return onlineUsers;
	}
	public boolean setServer(String server)
	{
		boolean validServer = false;
		if(server.length() > 0)
		{
			Socket toServer;
			try {
				toServer = new Socket("localhost",directorySocket);
				ObjectOutputStream outToServer = new ObjectOutputStream(toServer.getOutputStream());
				ServerMessage m = new ServerMessage();
				m.setCommand(TEST);
				
				outToServer.writeObject(m);
				
				ObjectInputStream serverInput = new ObjectInputStream(toServer.getInputStream());
				String reply = (String) serverInput.readObject();
				if(reply.equals("ACTIVE"))
				{
					validServer = true;
					System.out.println("Server replied; valid server");
				}
			} catch (UnknownHostException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				 
			}
		}
		return validServer;
	}
}
