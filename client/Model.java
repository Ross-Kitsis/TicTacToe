package client;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.Random;

import Data.UserBean;
import DirectoryServer.Responder;

public class Model
{
	private View v;
	
	private int board[][] = new int[3][3];
	final private static int directorySocket = 25200;
	
	private String hostName;
	private String userName;
	private ArrayList<UserBean> onlineUsers;
	private String server;
	private String ipAddress;
	
	private final String JOIN = "JOIN";
	private final String LEAVE = "LEAVE";
	private final String LIST = "LIST";
	private final String TEST = "TEST";
	
	private ServerSocket gameData;
	private ServerSocket controlData;
	private static final int gameDataSocketNumber = 25201;
	private static final int controlDataSocketNumber = 25202;
	
	private boolean piece;
	Random r;
	
	private static final int controlServerType = 0;
	private static final int gameDataServerType = 1;
	
	public Model()
	{
		//this.v = v;
		try {
			hostName = InetAddress.getLocalHost().getHostName();
			ipAddress = InetAddress.getLocalHost().getHostAddress();
			//r = new Random(System.currentTimeMillis());
			//P2PServer control = new P2PServer(controlServerType, controlDataSocketNumber,v);
			//control.start();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Could not get hostname");
		}
	}
	public void joinServer(String userName)
	{
		try {
			Socket toServer = new Socket(server, directorySocket);
			ObjectOutputStream outToServer = new ObjectOutputStream(toServer.getOutputStream());
			
			this.userName = userName;
			
			ServerMessage m = new ServerMessage();
			m.setCommand(JOIN);
			m.setUser(new UserBean(this.hostName, userName, ipAddress));
			
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
			Socket toServer = new Socket(server, directorySocket);
			ObjectOutputStream outToServer = new ObjectOutputStream(toServer.getOutputStream());

			ServerMessage m = new ServerMessage();
			m.setCommand(LEAVE);
			m.setUser(new UserBean(this.hostName,this.userName, ipAddress)); //Username could be null but shouldn't read it on server

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
			Socket toServer = new Socket(server,directorySocket);
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
				this.server = server;
				System.out.println("Connecting to: " + server);
				toServer = new Socket(server,directorySocket);
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
	public boolean sendInvite(String userName)
	{
		boolean response = false;
		String user = userName.split(": ")[1];
		UserBean receiver = null;
		
		for(UserBean u: this.onlineUsers)
		{
			if(u.getUserName().equals(user))
			{
				receiver = u;
				System.out.println(user);
				break;
			}
		}
		
		
		
		
		Socket controlSocket;
		try {
			controlSocket = new Socket(receiver.getHostName(), Model.controlDataSocketNumber);
			ObjectOutputStream toPeer = new ObjectOutputStream(controlSocket.getOutputStream());
			ClientMessage c = new ClientMessage();
			c.setCommand("INVITE");
			c.setUser(new UserBean(this.hostName, this.userName, ipAddress));
			this.piece = r.nextBoolean();
			c.setPiece(piece);
			
			
			toPeer.writeObject(c);
			
			ObjectInputStream fromPeer = new ObjectInputStream(controlSocket.getInputStream());
			ClientMessage r = (ClientMessage) fromPeer.readObject();
			
			if(r.isAccept())
			{
				//Remote user accepted the game
				response = true;
			}
			
			controlSocket.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return response;
	}
	public void setView(View v)
	{
		this.v = v;
	}
}
