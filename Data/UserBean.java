package Data;

import java.io.*;

public class UserBean implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String hostName;
	private String userName;
	private String ipAddress;
	
	public UserBean(String hostName, String userName, String ipAddress) 
	{
		this.hostName = hostName;
		this.userName = userName;
		this.ipAddress = ipAddress;
	}
	
	public String getHostName()
	{
		return this.hostName;
	}
	public String getUserName()
	{
		return this.userName;
	}
	public String getIpAddress()
	{
		return this.ipAddress;
	}
}
