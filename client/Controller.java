package client;

import java.awt.event.*;
import java.io.*;
import java.net.*;
import java.util.*;

import Data.UserBean;

public class Controller implements ActionListener, Runnable
{
	private View v;
	private Model m;
	
	private ArrayList<UserBean> onlineUsers;
	
	private String server;
	
	private int serverType = 0;
	
	private ServerSocket controlServerSocket = null;
	private Socket controlSocket = null;
	private ObjectInputStream controlInput = null;
	private ObjectOutputStream controlOutput = null;
	
	private ServerSocket gameDataServerSocket = null;
	private Socket gameDataSocket = null;
	
	private ObjectInputStream gameInput = null;
	private ObjectOutputStream gameOutput = null;
	
	private static final int gameDataSocketNumber = 25201;
	private static final int controlDataSocketNumber = 25202;
	
	private UserBean possibleOpponent;
	private int possiblePiece;
	
	public Controller(View v, Model m) 
	{
		this.v = v;
		this.m = m;
		
		Thread control = new Thread(this);
		control.start();
		
		while(serverType == 0)
		{
			try {
				System.out.println("Sleeping");
				Thread.sleep(5);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		Thread gameData = new Thread(this);
		gameData.start();
	}
	@Override
	public void actionPerformed(ActionEvent e) 
	{
		String event = e.getActionCommand();
		
		System.out.println("Event is: " + event);
		
		if(event.equals("JOIN"))
		{
			//System.out.println("Join pressed" + v.getText());
			if(v.getText().length() == 0)
			{
				System.out.println("No name entered");
			}
			m.joinServer(v.getText());
		}else if(event.equals("LEAVE"))
		{
			m.leaveServer();
		}else if(event.equals("LIST"))
		{
			onlineUsers = m.listOnlineUsers();
			v.updateOnlineUsers(onlineUsers);
		}else if(event.equals("CANCEL"))
		{
			System.exit(0);
		}else if(event.equals("OKAY"))
		{
			if(m.setServer(v.getServerEntry()))
			{
				//Server replied and was valid, display the game board
				v.setNewBoard();
			}else
			{
				//Server entered was not valid, display an error message
				v.setInvalidServerError();
				
			}
			//Need to create a method in model to verify server name is valid and server accepting connections
		}else if(event.contains("Invite:"))
		{
			m.sendInvite(event);
		}else if(event.equals("ACCEPT"))
		{
			//Start a new game with a users
			System.out.println("Invite accepted, need to send remote user accept");
			v.closeInvite();
			m.acceptInvite(possibleOpponent, possiblePiece);
			v.resetGameBoard();
			
		}else if(event.equals("REJECT"))
		{
			//Reject user request for game
			System.out.println("Invite rejected, need to send remote user reject");
			v.closeInvite();
		}else if(event.equals("00"))
		{
			//button at 0,0 presses
			int piece = m.getPiece();
			v.setPieceIcon(0, 0, piece);
		}
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		if(serverType == 0)
		{
			//Control message server	
			System.out.println("Running control server");
			runControlServer();
		}else if(serverType == 1)
		{
			System.out.println("Running game data server");
			this.runGameDataServer();
		}
	}
	
	private synchronized void changeServerType()
	{
		this.serverType++;
	}
	
	public void runControlServer()
	{
		try {
			controlServerSocket= new ServerSocket(controlDataSocketNumber);
			this.changeServerType();
			 while(true) 
			 {
				 System.out.println("Here");
				controlSocket = controlServerSocket.accept();
				controlInput = new ObjectInputStream(controlSocket.getInputStream());
				ClientMessage c = (ClientMessage) controlInput.readObject();
				System.out.println("Got client message with command: " + c.getCommand() );
				if(c.getCommand().equals("INVITE"))
				{
					this.possibleOpponent = c.getUser();
					this.possiblePiece = c.getPiece() * -1;
					v.setInviteView(c.getUser().getUserName());
				}else if(c.getCommand().equals("ACCEPT"))
				{
					//Accept invite request
					m.setHaveGame();
					v.resetGameBoard();
					System.out.println("Got accept back");
				}else if(c.getCommand().equals("REJECT"))
				{
					//Rejected invite
				}
			 }
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Could not start open P2P control socket on port 25201, exiting...");
			System.exit(-3);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		 
	}
	public void runGameDataServer()
	{
		try {
			
			gameDataServerSocket = new ServerSocket(gameDataSocketNumber);
			while(true)
			{
				System.out.println("Game data server");
				gameDataSocket = gameDataServerSocket.accept();
				gameInput = new ObjectInputStream(gameDataSocket.getInputStream());
				ClientMessage c = (ClientMessage) gameInput.readObject();
				System.out.println("Got game data message with command" + c.getCommand());
				if(c.getCommand().equals("MOVE"))
				{
					m.setOpBoardMove(c.getRow(), c.getColumn(),c.getPiece());
					v.setPieceIcon(c.getRow(), c.getColumn(), c.getPiece());
				}
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
