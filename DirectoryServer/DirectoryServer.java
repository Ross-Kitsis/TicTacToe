package DirectoryServer;

import java.io.IOException;
import java.net.ServerSocket;

public class DirectoryServer extends Thread
{
	private ServerSocket directorySocket;
	
	public DirectoryServer(int socketNumber) throws IOException
	{
		directorySocket = new ServerSocket(socketNumber);
		directorySocket.close();
		System.out.println("Done");
	}
}
