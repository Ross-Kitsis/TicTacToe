package client;

import java.io.*;

import Data.UserBean;

/**
 * 
 * Wrapper class containing messages exchanged by clients and server
 *
 */
public class ServerMessage implements Serializable
{

	private static final long serialVersionUID = 1L;
	private UserBean user;
	private String command;
	public ServerMessage()
	{
		
	}
	/**
	 * Gets the userBean containing client creation information
	 * @return
	 */
	public UserBean getUser() {
		return user;
	}
	/**
	 * Sets the userBean to identify the sender
	 * @param user
	 */
	public void setUser(UserBean user) {
		this.user = user;
	}
	/**
	 * Returns the command contained in the message
	 * @return
	 */
	public String getCommand() {
		return command;
	}
	/**
	 * Sets a command for the receiver to process
	 * @param command
	 */
	public void setCommand(String command) {
		this.command = command;
	}
	
	
}
