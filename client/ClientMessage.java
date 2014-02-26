package client;

import java.io.Serializable;

import Data.UserBean;

/**
 * A wrapper class containing information exchanged in P2P messages
 *
 */
public class ClientMessage implements Serializable
{
	private static final long serialVersionUID = 1L;
	private String command;
	private int row;
	private int column;
	private int piece;
	private UserBean user;
	private boolean accept;
	
	/**
	 * Returns accept field indicating if a client accepted an invitation
	 * @return 
	 */
	public boolean isAccept() {
		return accept;
	}
	/**
	 * Sets the accept field to ture to accept and invite or false to reject
	 * @param accept
	 */
	public void setAccept(boolean accept) {
		this.accept = accept;
	}
	/**
	 * Returns the userBean of the user that sent the message
	 * @return
	 */
	public UserBean getUser() {
		return user;
	}
	/**
	 * Sets the userBean of the message to identify the sender
	 * @param user
	 */
	public void setUser(UserBean user) {
		this.user = user;
	}
	/**
	 * Returns the message command signifying the purpose of the message
	 * @return
	 */
	public String getCommand() {
		return command;
	}
	/**
	 * Sets the command so that reciver knows the purpose of the message
	 * @param command
	 */
	public void setCommand(String command) {
		this.command = command;
	}
	/**
	 * Gets the row parameter of a move
	 * @return
	 */
	public int getRow() {
		return row;
	}
	/**
	 * Sets the row parameter of the move
	 * @param row
	 */
	public void setRow(int row) {
		this.row = row;
	}
	/**
	 * Returns the column of the move
	 * @return
	 */
	public int getColumn() {
		return column;
	}
	/**
	 * Sets the column of a move
	 * @param column
	 */
	public void setColumn(int column) {
		this.column = column;
	}
	/**
	 * Returns the game piece
	 * @return
	 */
	public int getPiece() {
		return piece;
	}
	/**
	 * Sets a game piece
	 * @param piece
	 */
	public void setPiece(int piece) {
		this.piece = piece;
	}
	/**
	 * Default constructor
	 */
	public ClientMessage()
	{
		
	}
}
