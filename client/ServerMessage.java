package client;

import java.io.*;

import Data.UserBean;

public class ServerMessage implements Serializable
{
	private UserBean user;
	private String command;
	public ServerMessage()
	{
		
	}
	public UserBean getUser() {
		return user;
	}
	public void setUser(UserBean user) {
		this.user = user;
	}
	public String getCommand() {
		return command;
	}
	public void setCommand(String command) {
		this.command = command;
	}
	
	
}
