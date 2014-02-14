package Data;

import java.io.*;

public class UserBean implements Serializable
{
	private String hostName;
	private String userName;
	
	public UserBean(String hostName, String userName) 
	{
		this.hostName = hostName;
		this.userName = userName;
	}
	
	public String getHostName()
	{
		return this.hostName;
	}
	public String getUserName()
	{
		return this.userName;
	}
}
