package DirectoryServer;

import java.io.IOException;

public class DirectoryServerDriver 
{
	
	
	public static void main(String[] args) throws IOException
	{
		//Server Parameters
		int socketNumber = 25200;
		
		
		
		DirectoryServer d = new DirectoryServer(socketNumber);
	}
}
