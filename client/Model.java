package client;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.Enumeration;
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
	
	private int piece;
	private Random r;
	
	private static final int controlServerType = 0;
	private static final int gameDataServerType = 1;
	
	private boolean sentInvite = false;
	private UserBean opponent;
	private boolean haveGame = false;
	
	private boolean isTurn = false;
	
	public Model()
	{
		//this.v = v;
		try {
			hostName = InetAddress.getLocalHost().getHostName();
			this.ipAddress = InetAddress.getByName(hostName).getHostAddress();
			
			System.out.println("Starting up client with hostname: " + hostName + " and IP " + ipAddress);
			
			r = new Random(System.currentTimeMillis());
			//P2PServer control = new P2PServer(controlServerType, controlDataSocketNumber,v);
			//control.start();
		} catch (UnknownHostException e) {
			
			try {
				ipAddress = this.getIPAddress();
			} catch (SocketException e1) {
				
				e1.printStackTrace();
				System.out.println("Failed to get IP address from both DNS lookup and local adapters, exiting...");
				System.exit(-4);
			}
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

			//outToServer.flush();
			//outToServer.
			//toServer.close();
			
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
			
			
			
			//outToServer.close();
			//toServer.close();

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
				toServer.close();
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
	public void sendInvite(String userName)
	{
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
			System.out.println("Setting up connection to: " + receiver.getIpAddress());
			controlSocket = new Socket(receiver.getIpAddress(), Model.controlDataSocketNumber);
			ObjectOutputStream toPeer = new ObjectOutputStream(controlSocket.getOutputStream());
			ClientMessage c = new ClientMessage();
			c.setCommand("INVITE");
			c.setUser(new UserBean(this.hostName, this.userName, this.ipAddress));
			this.piece = this.generatePiece();
			c.setPiece(piece);
			
			this.sentInvite = true;
			this.opponent = receiver;
			
			toPeer.writeObject(c);
			
			controlSocket.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			
			e.printStackTrace();
		} 

	}
	public void acceptInvite(UserBean possibleOpponent, int possiblePiece)
	{
		this.opponent = possibleOpponent;
		this.piece = possiblePiece;
		Socket controlSocket;
		try {
			controlSocket = new Socket(opponent.getIpAddress(), Model.controlDataSocketNumber);
			ObjectOutputStream toPeer = new ObjectOutputStream(controlSocket.getOutputStream());
			ClientMessage c = new ClientMessage();
			c.setCommand("ACCEPT");
			c.setUser(new UserBean(this.hostName, this.userName, this.ipAddress));

			System.out.println("Accepting invite");
			
			toPeer.writeObject(c);

			controlSocket.close();
			this.haveGame = true;
			System.out.println("Starting game");
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			
			e.printStackTrace();
		} 
	}
	public void setHaveGame()
	{
		this.haveGame = true;
		this.isTurn = true;
		System.out.println("Setting have game");
	}
	public boolean getHaveGame()
	{
		return this.haveGame;
	}
	public String getIPAddress() throws SocketException
	{
		Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces();
		while(en.hasMoreElements())
		{
			NetworkInterface i = en.nextElement();
			Enumeration<InetAddress> addresses = i.getInetAddresses();
			while(addresses.hasMoreElements())
			{
				InetAddress ad = addresses.nextElement();
				if(!ad.isLoopbackAddress() && ad instanceof Inet4Address)
				{
					System.out.println(ad.getHostAddress());
					return ad.getHostAddress();
				}
			}
		}
		return null;
		
	}
	public void rejectInvite(UserBean possibleOpponent)
	{
		Socket controlSocket;
		try {
			controlSocket = new Socket(possibleOpponent.getIpAddress(), Model.controlDataSocketNumber);
			ObjectOutputStream toPeer = new ObjectOutputStream(controlSocket.getOutputStream());
			ClientMessage c = new ClientMessage();
			c.setCommand("REJECT");
			c.setUser(new UserBean(this.hostName, this.userName, this.ipAddress));
			System.out.println("Rejecting invite");
			
			toPeer.writeObject(c);
		
			controlSocket.close();
			
		} catch (IOException e) 
		{
			e.printStackTrace();
		} 
	}
	public boolean makeMove(int row, int col)
	{
		boolean canMove = false;
		if(this.haveGame && isTurn)
		{
			if(board[row][col] == 0)
			{
				canMove = true;
				board[row][col] = piece;
				
				
				///Send move
				
				Socket controlSocket;
				try {
					System.out.println("Setting up connection to: " + opponent.getIpAddress());
					controlSocket = new Socket(opponent.getIpAddress(), Model.gameDataSocketNumber);
					ObjectOutputStream toPeer = new ObjectOutputStream(controlSocket.getOutputStream());
					ClientMessage c = new ClientMessage();
					c.setCommand("MOVE");
					c.setRow(row);
					c.setColumn(col);
					c.setPiece(this.piece);
					c.setUser(new UserBean(this.hostName, this.userName, this.ipAddress));
					
					toPeer.writeObject(c);

					controlSocket.close();
					
				} catch (IOException e) 
				{					
					e.printStackTrace();
				} 
			}
		}
		this.isTurn = false;
		return canMove;
	}
	public int getPiece()
	{
		return this.piece;
	}
	private int generatePiece()
	{
		int toReturn = 0;
		int max = 1;
		int min = -1;
		
		while(toReturn == 0)
		{
			toReturn = r.nextInt((max - min) + 1) + min;
		}
		System.out.println(toReturn);
		return toReturn;
	}
	public void setOpBoardMove(int row, int col, int oppiece)
	{
		board[row][col] = oppiece;
	}
	public int haveWinCondition()
	{
		int win = 0;
		
		//Horizontal
		for(int i = 0; i < 3; i++)
		{
			int total = 0;
			
			for(int j = 0; j < 3; j++)
			{
				total += board[i][j];
			}
			
			if(total == (this.piece * 3))
			{
				//Win
				win = 1;
			}else if(total == (this.piece * -3))
			{
				//lose
				win = -1;
			}
		}
		
		//Vertical
		for(int i = 0; i < 3; i++)
		{
			int total = 0;
			
			for(int j = 0; j < 3; j++)
			{
				total += board[j][i];
			}
			
			if(total == (this.piece * 3))
			{
				//Win
				win = 1;
			}else if(total == (this.piece * -3))
			{
				//lose
				win = -1;
			}
		}
		
		//Diagonal
		int rightDiag =  board[0][0] + board[1][1] + board[2][2];
		int leftDiag =  board[0][2] + board[1][1] + board[2][0];
		
		if(rightDiag == (this.piece * 3))
		{
			win = 1;
		}else if(rightDiag == (this.piece * -3))
		{
			win = -1;
		}else if(leftDiag == (this.piece * 3))
		{
			win = 1;
		}else if(leftDiag == (this.piece * -3))
		{
			win = -1;
		}
		
		return win;
	}
	public void endGame()
	{
		this.opponent = null;
		this.haveGame = false;
		this.isTurn = false;
		for(int i = 0; i < 3; i++)
		{
			for(int j = 0; j < 3; j++)
			{
				board[i][j] = 0;
			}
		}
	}
	public boolean isTurn()
	{
		return isTurn;
	}
	public void setIsTurn()
	{
		this.isTurn = true;
	}
}
