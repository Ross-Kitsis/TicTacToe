package DirectoryServer;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import Data.DAO;

/**
 * Main server class that accepts incoming socket connections. When a connection is made
 * a new responder thread is created allowing the server to accept new connections while the responder
 * thread responds to the request. 
 */
public class DirectoryServerDriver
{
	
	final private static int socketNumber = 25200;
	
	public static void main(String args[]) throws IOException 
	{
		//Server Parameters
		DAO d = new DAO();
		try
		{
			ServerSocket mainSocket = new ServerSocket(socketNumber);
			while(true)
			{
				Socket socket = mainSocket.accept();
				Responder r = new Responder(socket,d);
				r.start();
			}
		}catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
	}
}
 