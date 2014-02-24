package client;

import java.io.Serializable;

import Data.UserBean;

public class ClientMessage implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String command;
	private int x;
	private int y;
	private int piece;
	private UserBean user;
	private boolean accept;
	
	public boolean isAccept() {
		return accept;
	}

	public void setAccept(boolean accept) {
		this.accept = accept;
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

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getPiece() {
		return piece;
	}

	public void setPiece(int piece) {
		this.piece = piece;
	}

	public ClientMessage()
	{
		
	}
}
