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
	private int row;
	private int column;
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

	public int getRow() {
		return row;
	}

	public void setRow(int row) {
		this.row = row;
	}

	public int getColumn() {
		return column;
	}

	public void setColumn(int column) {
		this.column = column;
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
