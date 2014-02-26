package client;

import java.awt.event.*;
import java.io.*;
import java.net.*;
import java.util.*;

import Data.UserBean;

/**
 * 
 * Primary controller of client application. 
 * Handles GUI events in the form of button clicks
 * Runs 2 servers in separate threads to handle P2P communication 
 *
 */

public class Controller implements ActionListener, Runnable
{
	private View v;
	private Model m;
	
	private ArrayList<UserBean> onlineUsers;
	
	
	private int serverType = 0;
	
	private ServerSocket controlServerSocket = null;
	private Socket controlSocket = null;
	private ObjectInputStream controlInput = null;
	
	private ServerSocket gameDataServerSocket = null;
	private Socket gameDataSocket = null;
	
	private ObjectInputStream gameInput = null;
	
	private static final int gameDataSocketNumber = 25201;
	private static final int controlDataSocketNumber = 25202;
	
	private UserBean possibleOpponent;
	private int possiblePiece;
	
	/**
	 * Controler constructor, creates server threads
	 * @param View v
	 * @param Model m
	 */
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
	/**
	 * Action listener catching button click events
	 * Does not implement any game logic, rather calls model to implement game logic
	 * based on the event
	 */
	public void actionPerformed(ActionEvent e) 
	{
		String event = e.getActionCommand();
		
		System.out.println("Event is: " + event);
		
		if(event.equals("JOIN"))
		{
			//User pressed JOIN
			if(v.getText().length() == 0)
			{
				System.out.println("No name entered");
			}
			m.joinServer(v.getText());
		}else if(event.equals("LEAVE"))
		{
			//User pressed leave
			m.leaveServer();
		}else if(event.equals("LIST"))
		{
			//User pressed LIST
			onlineUsers = m.listOnlineUsers();
			v.updateOnlineUsers(onlineUsers);
		}else if(event.equals("CANCEL"))
		{
			//User canceled initial window, exit application
			System.exit(0);
		}else if(event.equals("OKAY"))
		{
			//User entered directory server name/IP, model validates directory server
			//Based on validation start server or display an error
			if(m.setServer(v.getServerEntry()))
			{
				//Server replied and was valid, display the game board
				v.setNewBoard();
			}else
			{
				//Server entered was not valid, display an error message
				v.setInvalidServerError();
				
			}
		}else if(event.contains("Invite:"))
		{
			//User entered invite
			m.sendInvite(event);
		}else if(event.equals("ACCEPT"))
		{
			//Invite accepted, start a new game
			System.out.println("Invite accepted, need to send remote user accept");
			v.closeInvite();
			m.acceptInvite(possibleOpponent, possiblePiece);
			v.resetGameBoard();
			v.setGameStartMessage();
			
		}else if(event.equals("REJECT"))
		{
			//Reject user request for game
			System.out.println("Invite rejected, need to send remote user reject");
			v.closeInvite();
		}else if(event.equals("00"))
		{
			//button press at x,y
			//Validate move with model and update GUI
			//Check win conditions, if have win/lose/tie end the game and set message
			if(m.makeMove(0, 0))
			{
				int p = m.getPiece();
				v.setPieceIcon(0, 0, p);
				int win = m.haveWinCondition();
				if(win == -1)
				{
					m.endGame();
					v.setLoseMessage();
				}else if(win == 1)
				{
					m.endGame();
					v.setWinMessage();
				}else if(win == -2)
				{
					m.endGame();
					v.setTieMessage();
				}
			}
		}else if(event.equals("01"))
		{
			if(m.makeMove(0, 1))
			{
				int p = m.getPiece();
				v.setPieceIcon(0, 1, p);
				int win = m.haveWinCondition();
				if(win == -1)
				{
					m.endGame();
					v.setLoseMessage();
				}else if(win == 1)
				{
					m.endGame();
					v.setWinMessage();
				}else if(win == -2)
				{
					m.endGame();
					v.setTieMessage();
				}
			}
		}else if(event.equals("02"))
		{
			if(m.makeMove(0, 2))
			{
				int p = m.getPiece();
				v.setPieceIcon(0, 2, p);
				int win = m.haveWinCondition();
				if(win == -1)
				{
					m.endGame();
					v.setLoseMessage();
					
				}else if(win == 1)
				{
					m.endGame();
					v.setWinMessage();
				}else if(win == -2)
				{
					m.endGame();
					v.setTieMessage();
				}
			}
		}else if(event.equals("10"))
		{
			if(m.makeMove(1, 0))
			{
				int p = m.getPiece();
				v.setPieceIcon(1, 0, p);
				int win = m.haveWinCondition();
				if(win == -1)
				{
					m.endGame();
					v.setLoseMessage();
				}else if(win == 1)
				{
					m.endGame();
					v.setWinMessage();
				}else if(win == -2)
				{
					m.endGame();
					v.setTieMessage();
				}
			}
		}else if(event.equals("11"))
		{
			if(m.makeMove(1, 1))
			{
				int p = m.getPiece();
				v.setPieceIcon(1, 1, p);
				int win = m.haveWinCondition();
				if(win == -1)
				{
					m.endGame();
					v.setLoseMessage();
				}else if(win == 1)
				{
					m.endGame();
					v.setWinMessage();
				}else if(win == -2)
				{
					m.endGame();
					v.setTieMessage();
				}
			}
		}else if(event.equals("12"))
		{
			if(m.makeMove(1, 2))
			{
				int p = m.getPiece();
				v.setPieceIcon(1, 2, p);
				int win = m.haveWinCondition();
				if(win == -1)
				{
					m.endGame();
					v.setLoseMessage();
				}else if(win == 1)
				{
					m.endGame();
					v.setWinMessage();
				}else if(win == -2)
				{
					m.endGame();
					v.setTieMessage();
				}
			}
		}else if(event.equals("20"))
		{
			if(m.makeMove(2, 0))
			{
				int p = m.getPiece();
				v.setPieceIcon(2, 0, p);
				int win = m.haveWinCondition();
				if(win == -1)
				{
					m.endGame();
					v.setLoseMessage();
				}else if(win == 1)
				{
					m.endGame();
					v.setWinMessage();
				}else if(win == -2)
				{
					m.endGame();
					v.setTieMessage();
				}
			}
		}else if(event.equals("21"))
		{
			if(m.makeMove(2, 1))
			{
				int p = m.getPiece();
				v.setPieceIcon(2, 1, p);
				int win = m.haveWinCondition();
				if(win == -1)
				{
					m.endGame();
					v.setLoseMessage();
				}else if(win == 1)
				{
					m.endGame();
					v.setWinMessage();
				}else if(win == -2)
				{
					m.endGame();
					v.setTieMessage();
				}
			}
		}else if(event.equals("22"))
		{
			if(m.makeMove(2, 2))
			{
				int p = m.getPiece();
				v.setPieceIcon(2, 2, p);
				int win = m.haveWinCondition();
				if(win == -1)
				{
					m.endGame();
					v.setLoseMessage();
				}else if(win == 1)
				{
					m.endGame();
					v.setWinMessage();
				}else if(win == -2)
				{
					m.endGame();
					v.setTieMessage();
				}
			}
		}
	}
	/**
	 * Thread start point. Start the control server and game data server.
	 */
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
	/**
	 * Incement the server type, used to change type f server thread creates
	 */
	private synchronized void changeServerType()
	{
		this.serverType++;
	}
	/**
	 * Runs the control server
	 * Control server is responsible for accepting game invites and receiving game invite rejections
	 */
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
					if(!m.getHaveGame())
					{
						//Dont have a game, may accept or reject
						this.possibleOpponent = c.getUser();
						this.possiblePiece = c.getPiece() * -1;
						v.setInviteView(c.getUser().getUserName());
					}else
					{
						//Have a game already, automatically reject
						m.rejectInvite(c.getUser());
					}
				}else if(c.getCommand().equals("ACCEPT"))
				{
					//Accept invite request
					m.setIsTurn();
					m.setHaveGame();
					v.resetGameBoard();
					v.setGameStartMessage();
					System.out.println("Got accept back");
				}else if(c.getCommand().equals("REJECT"))
				{
					//Rejected invite
					v.setGameRejectedMessage();
				}
			 }
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Could not start open P2P control socket on port 25201, exiting...");
			System.exit(-3);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}		 
	}
	/**
	 * Runs the game data server.
	 * Game data server is responsible for accepting game data; specifically moves
	 * After each move checks win conditions and ends game if win/lose/tie
	 */
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
					m.setIsTurn();
					m.setOpBoardMove(c.getRow(), c.getColumn(),c.getPiece());
					v.setPieceIcon(c.getRow(), c.getColumn(), c.getPiece());
					int win = m.haveWinCondition();
					if(win == -1)
					{
						m.endGame();
						v.setLoseMessage();	
					}else if(win == 1)
					{
						m.endGame();
						v.setWinMessage();
					}else if(win == -2)
					{
						m.endGame();
						v.setTieMessage();
					}
				}
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
}
