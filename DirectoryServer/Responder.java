package DirectoryServer;

import java.io.*;
import java.net.*;
import java.util.ArrayList;

import Data.DAO;
import Data.UserBean;

import client.ServerMessage;

/*
 * A multi-threaded class responsible for responding to client messages. 
 * Responds to JOIN, LEAVE and LIST messages
 * 
 */
public class Responder extends Thread
{
	private Socket socket;
	private ObjectOutputStream output;
	private ObjectInputStream input;
	private DAO dataAccess;
	
	public Responder(Socket socket, DAO dataAccess)
	{
		this.dataAccess = dataAccess;
		try{
			this.socket = socket;
			this.output = new ObjectOutputStream(socket.getOutputStream());
			this.input = new ObjectInputStream(socket.getInputStream());
		}catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
	}
	/*
	 * Method automatically run when thread starts. Reads incoming message and responds accordingly
	 */
	public void run() 
	{
		ServerMessage m;
		try {
			m = (ServerMessage) input.readObject();
			
			String message = m.getCommand();
			
			if(message.equals("JOIN"))
			{
				//Add to the list of users
				this.dataAccess.join(m.getUser());
				System.out.println("got join request");
			}else if(message.equals("LEAVE"))
			{
				//Remove from list of users
				this.dataAccess.leave(m.getUser());
				System.out.println("got leave request");				
			}else if(message.equals("LIST"))
			{
				//Returns a list of online users
				System.out.println("Got list request");
				ArrayList<UserBean> toSend = this.dataAccess.list();
				output.writeObject(toSend);
			}else if(message.equals("TEST"))
			{
				//Responds to connection tests when a client starts
				System.out.println("Got connection test msg");
				output.writeObject("ACTIVE");
			}
			socket.close();
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
			System.out.println("Class not found exception");
		} catch (IOException e1) {
			e1.printStackTrace();
		}catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
