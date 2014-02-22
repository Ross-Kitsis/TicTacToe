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
	
	public Controller(View v, Model m) 
	{
		this.v = v;
		this.m = m;
		Thread control = new Thread(this);
		control.run();
		while(serverType == 0)
		{
			try {
				Thread.sleep(5);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	@Override
	public void actionPerformed(ActionEvent e) 
	{
		String event = e.getActionCommand();
		
		System.out.println(event);
		
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
		}
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		if(serverType == 0)
		{
			//Control message server	
			runControlServer();
		}else if(serverType == 1)
		{
			
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
				controlSocket = controlServerSocket.accept();
				controlInput = new ObjectInputStream(controlSocket.getInputStream());
				ClientMessage c = (ClientMessage) controlInput.readObject();
				System.out.println("Got client message with command: " + c.getCommand() );
				//BufferedReader inFromClient = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
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
}
