package Data;

import java.io.*;

/**
 * A wrapper class to hold user information
 * Information includes host name, user name and IP address
 *
 */
public class UserBean implements Serializable
{
	private static final long serialVersionUID = 1L;
	private String hostName;
	private String userName;
	private String ipAddress;
	
	/**
	 * Constructor for the UserBean
	 * @param hostName
	 * @param userName
	 * @param ipAddress
	 */
	public UserBean(String hostName, String userName, String ipAddress) 
	{
		this.hostName = hostName;
		this.userName = userName;
		this.ipAddress = ipAddress;
	}
	/**
	 * Returns the user host name
	 * @return
	 */
	public String getHostName()
	{
		return this.hostName;
	}
	/**
	 * Returns the host user name
	 * @return
	 */
	public String getUserName()
	{
		return this.userName;
	}
	/**
	 * Returns the host IP address
	 * @return
	 */
	public String getIpAddress()
	{
		return this.ipAddress;
	}
}
