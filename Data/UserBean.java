package Data;

import java.io.*;

public class UserBean implements Serializable
{
	private String hostName;
	
	public UserBean(String hostName) 
	{
		this.hostName = hostName;
	}
	
	public String getHostName()
	{
		return this.hostName;
	}
}
