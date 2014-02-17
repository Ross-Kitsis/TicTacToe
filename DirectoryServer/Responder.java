package DirectoryServer;

import java.io.*;
import java.net.*;
import java.util.ArrayList;

import Data.DAO;
import Data.UserBean;

import client.ServerMessage;

public class Responder extends Thread
{
	private Socket socket;
	//private InputStreamReader input;
	private ObjectOutputStream output;
	private ObjectInputStream input;
	private DAO dataAccess;
	
	public Responder(Socket socket, DAO dataAccess)
	{
		this.dataAccess = dataAccess;
		try{
			this.socket = socket;
			//this.input = new InputStreamReader(socket.getInputStream());
			//this.output = new DataOutputStream(socket.getOutputStream()); 
			this.output = new ObjectOutputStream(socket.getOutputStream());
			this.input = new ObjectInputStream(socket.getInputStream());
		}catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
	}
	
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
				this.dataAccess.leave(m.getUser());
				System.out.println("got leave request");
				//remove from the list of users (Let clients know somehow?)
				
			}else if(message.equals("LIST"))
			{
				System.out.println("Got list request");
				ArrayList<UserBean> toSend = this.dataAccess.list();
				output.writeObject(toSend);
				//Send client a serialized list of all online players
			}else if(message.equals("TEST"))
			{
				output.writeObject("ACTIVE");
			}
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		//BufferedReader inFromClient = new BufferedReader(input);
 catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
