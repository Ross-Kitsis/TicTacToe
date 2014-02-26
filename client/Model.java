package client;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Random;

import Data.UserBean;

/**
 * 
 * The game data model, handle all game logic decisions and message transmission
 *
 */

public class Model
{
	
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
	

	private static final int gameDataSocketNumber = 25201;
	private static final int controlDataSocketNumber = 25202;
	
	private int piece;
	private Random r;
	
	
	private UserBean opponent;
	private boolean haveGame = false;
	
	private boolean isTurn = false;
	
	/**
	 * Default constructor of model.
	 * Sets the hostname to the computer hostname
	 * Sets the IP address based on a DNS lookup of the computer, if DNS lookup fails sets based on first non-loopback
	 * IP in the network adapters of the client
	 * 
	 */
	public Model()
	{
		//this.v = v;
		try {
			hostName = InetAddress.getLocalHost().getHostName();
			this.ipAddress = InetAddress.getByName(hostName).getHostAddress();
			if(this.ipAddress.startsWith("127"))
			{
				//Found the loopback address; need to test local adapters
				throw new UnknownHostException();
			}
			
			System.out.println("Starting up client with hostname: " + hostName + " and IP " + ipAddress);
			
			r = new Random(System.currentTimeMillis());

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
	/**
	 * Sends a JOIN command to the directory server
	 * @param userName
	 */
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
			
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * Sends a LEAVE command to the directory server
	 */
	public void leaveServer()
	{
		try {
			Socket toServer = new Socket(server, directorySocket);
			ObjectOutputStream outToServer = new ObjectOutputStream(toServer.getOutputStream());

			ServerMessage m = new ServerMessage();
			m.setCommand(LEAVE);
			m.setUser(new UserBean(this.hostName,this.userName, ipAddress)); //Username could be null but shouldn't read it on server

			outToServer.writeObject(m);
			

		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * Sends a LIST command to the directory server
	 * Returns the list of online users
	 * @return 
	 */
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
			
			outToServer.close();
			serverInput.close();
			toServer.close();
		}catch(UnknownHostException e)
		{
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return onlineUsers;
	}
	/**
	 * Sets Directory server
	 * Attempts to open a socket to the directory server; if successful accepts the server otherwise server is rejected
	 * @param server
	 * @return true if able to make connection to server
	 */
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
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace(); 
			}
		}
		return validServer;
	}
	/**
	 * Searches the local list of online users for the desired user information.
	 * Once user is found sends an invitation message to the users server
	 * @param userName
	 */
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
			
			this.opponent = receiver;
			
			toPeer.writeObject(c);
			
			controlSocket.close();
			
		} catch (IOException e) {			
			e.printStackTrace();
		} 

	}
	/**
	 * Accepts an invite from a peer by sending an accept message to the peer
	 * Sets the game piece and starts the game
	 * @param possibleOpponent
	 * @param possiblePiece
	 */
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
	/**
	 * Sets the haveGame parameter indicating the user is in a game
	 */
	public void setHaveGame()
	{
		this.haveGame = true;
		this.isTurn = true;
		System.out.println("Setting have game");
	}
	/**
	 * Returns the haveGame parameter to determine if the user is in a game
	 * @return
	 */
	public boolean getHaveGame()
	{
		return this.haveGame;
	}
	/**
	 * Auxiliary method for finding a host IP if DNS unavailable
	 * Loops through network adapters searching for first non-loopback IP
	 * @return String representation of IP
	 * @throws SocketException
	 */
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
	/**
	 * Sends a REJECT message to a peer if user does not wish to start a game
	 * @param possibleOpponent
	 */
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
	/**
	 * Validates a user move; if valid sends the move to the peer to update their game board
	 * @param row - Row where move was made
	 * @param col - Column where move was made
	 * @return True if the move is valid
	 */
	public boolean makeMove(int row, int col)
	{
		boolean canMove = false;
		if(this.haveGame && isTurn)
		{
			if(board[row][col] == 0)
			{
				canMove = true;
				board[row][col] = piece;
				
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
	/**
	 * Returns the users game piece
	 * @return
	 */
	public int getPiece()
	{
		return this.piece;
	}
	/**
	 * Generates a game piece, if -1 then x if 1 then o
	 * @return
	 */
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
	/**
	 * Sets an opponents move to the current game board to keep boards consistent
	 * @param row
	 * @param col
	 * @param oppiece
	 */
	public void setOpBoardMove(int row, int col, int oppiece)
	{
		board[row][col] = oppiece;
	}
	/**
	 * Checks win/lose/tie conditions
	 * @return 1 if win, -1 if lose, -2 if tie, 0 otherwise
	 */
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
		
		if(win == 0)
		{
			int numFill = 0;
			for(int i = 0; i < 3; i++)
			{
				for(int j = 0; j < 3; j++)
				{
					if(board[i][j] != 0)
					{
						numFill++;
					}
				}
			}
			if(numFill == 9)
			{
				win = -2;
			}
		}
		
		return win;
	}
	/**
	 * Ends game by reseting all game and peer parameters
	 */
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
	/**
	 * Returns the value of the users turn
	 * @return true if users turn
	 */
	public boolean isTurn()
	{
		return isTurn;
	}
	/**
	 * Sets the turn variable
	 */
	public void setIsTurn()
	{
		this.isTurn = true;
	}
}
