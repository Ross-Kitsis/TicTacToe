package DirectoryServer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

import Data.DAO;

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
 