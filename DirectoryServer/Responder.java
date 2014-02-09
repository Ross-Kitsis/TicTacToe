package DirectoryServer;

import java.io.*;
import java.net.*;

public class Responder extends Thread
{
	private Socket socket;
	private InputStreamReader input;
	private DataOutputStream output;
	
	
	public Responder(Socket socket)
	{
		try{
			this.socket = socket;
			this.input = new InputStreamReader(socket.getInputStream());
			this.output = new DataOutputStream(socket.getOutputStream()); 
		}catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
	}
	
	public void run()
	{
		BufferedReader inFromClient = new BufferedReader(input);
		try {
			String message = inFromClient.readLine();
			
			if(message.equals("JOIN"))
			{
				//Add to the list of users
			}else if(message.equals("LEAVE"))
			{
				//remove from the list of users (Let clients know somehow?)
				
			}else if(message.equals("LIST"))
			{
				//Send client a serialized list of all online players
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
