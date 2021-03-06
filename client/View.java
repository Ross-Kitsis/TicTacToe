package client;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

import javax.swing.*;
import javax.swing.Timer;

import Data.UserBean;

/**
 * Handles all view related functionality for the TicTacTow game
 *  
 */

public class View extends JFrame implements ActionListener
{
	//Button variables
	public static final long serialVersionUID = 1L;
	public static final String JOIN = "JOIN";
	public static final String REFRESH = "REFRESH";
	public static final String LEAVE = "LEAVE";
	public static final String LIST = "LIST";
	public static final String OKAY = "OKAY";
	public static final String CANCEL = "CANCEL";
	public static final String ACCEPT = "ACCEPT";
	public static final String REJECT = "REJECT";
	
	//Game board buttoms
	public static final String b00 = "00";
	public static final String b01 = "01";
	public static final String b02 = "02";
	public static final String b10 = "10";
	public static final String b11 = "11";
	public static final String b12 = "12";
	public static final String b20 = "20";
	public static final String b21 = "21";
	public static final String b22 = "22";
	
	private JButton board[][] = new JButton[3][3];
	private JTextField f;
	private JTextField error;
	private JPanel scrollPanel;
	private JScrollPane jsp;
	private JLabel ins;
	private JTextField server;
	private JFrame initial;
	
	private JFrame invite;
	private JLabel inviteMessage;
	
	private ImageIcon x = new ImageIcon("x.jpg");
	private ImageIcon o = new ImageIcon("circle.jpg");
	
	private JFrame gameStarted;
	private JLabel startMessage;
	
	private JFrame gameRejected;
	private JLabel rejectedMessage;
	
	private JFrame lose;
	private JLabel loseMessage;
	
	private JFrame win;
	private JLabel winMessage;
	
	private JFrame tie;
	private JLabel tieMessage;
	
	//Model
	public Model m;
	
	private Controller control;
	
	Timer t = new Timer(2500,this);
	
	/**
	 * Creates the primary view and sets to visible
	 * Creates popup windows but does not set them to visible
	 * @param m
	 */
	public View(Model m)
	{
		super.setSize(800,800);
		super.setDefaultCloseOperation(EXIT_ON_CLOSE);
		super.setLayout(new GridBagLayout());
		this.m = m;
		super.setResizable(false);
		super.setLocationRelativeTo(null);
		control = new Controller(this,m);
		
		this.lose = new JFrame();
		lose.setSize(400,100);
		lose.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		lose.setResizable(false);
		lose.setLocationRelativeTo(null);
		this.loseMessage = new JLabel();
		loseMessage.setText("You lose, ending game...");
		lose.add(loseMessage);
		
		this.win = new JFrame();
		win.setSize(400,100);
		win.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		win.setResizable(false);
		win.setLocationRelativeTo(null);
		this.winMessage = new JLabel();
		winMessage.setText("You win, ending game...");
		win.add(winMessage);
		
		this.gameRejected = new JFrame();
		gameRejected.setSize(400,100);
		gameRejected.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		gameRejected.setResizable(false);
		gameRejected.setLocationRelativeTo(null);
		this.rejectedMessage = new JLabel();
		rejectedMessage.setText("Invite rejected");
		gameRejected.add(rejectedMessage);
		
		this.gameStarted = new JFrame();
		gameStarted.setSize(400,100);
		gameStarted.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		gameStarted.setResizable(false);
		gameStarted.setLocationRelativeTo(null);
		this.startMessage = new JLabel();
		startMessage.setText("Invite accepted, game starting in 2 seconds");
		gameStarted.add(startMessage);
		
		this.tie = new JFrame();
		tie.setSize(400,100);
		tie.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		tie.setResizable(false);
		tie.setLocationRelativeTo(null);
		this.tieMessage = new JLabel();
		tieMessage.setText("Game tied, ending game...");
		tie.add(tieMessage);
		
		this.initialWindow();
	}
	/**
	 * Sets a new board, setting all elements in the main frame
	 */
	public void setNewBoard()
	{
		initial.setVisible(false);
		initial.dispose();
		
		GridBagConstraints c = new GridBagConstraints();
		
		JButton button; 
		

///////First fow of tictactoe
		button = new JButton(b00);

		c.fill = GridBagConstraints.BOTH;
		c.weightx = 0.25;
		c.weighty = 0.25;
		c.gridx = 0;
		c.gridy = 0;
		//button.setIcon(x);
		board[0][0] = button;
		//button.addActionListener(l)
		button.addActionListener(control);
		super.add(button, c);
		
		button = new JButton(b01);
		c.fill = GridBagConstraints.BOTH;
		c.weightx = 0.25;
		c.weighty = 0.25;
		c.gridx = 1;
		c.gridy = 0;
		board[0][1] = button;
		button.addActionListener(control);
		super.add(button, c);
		
		button = new JButton(b02);
		c.fill = GridBagConstraints.BOTH;
		c.weightx = 0.25;
		c.weighty = 0.25;
		c.gridx = 2;
		c.gridy = 0;
		board[0][2] = button;
		button.addActionListener(control);
		super.add(button, c);
		
///////Second row
		
		button = new JButton(b10);
		c.fill = GridBagConstraints.BOTH;
		c.weightx = 0.25;
		c.weighty = 0.25;
		c.gridx = 0;
		c.gridy = 1;
		board[1][0] = button;
		button.addActionListener(control);
		super.add(button, c);
		
		button = new JButton(b11);
		c.fill = GridBagConstraints.BOTH;
		c.weightx = 0.25;
		c.weighty = 0.25;
		c.gridx = 1;
		c.gridy = 1;
		board[1][1] = button;
		button.addActionListener(control);
		super.add(button, c);
		
		button = new JButton(b12);
		c.fill = GridBagConstraints.BOTH;
		c.weightx = 0.25;
		c.weighty = 0.25;
		c.gridx = 2;
		c.gridy = 1;
		board[1][2] = button;
		button.addActionListener(control);
		super.add(button, c);
		
///////Thid row
		
		
		button = new JButton(b20);
		c.fill = GridBagConstraints.BOTH;
		c.weightx = 0.25;
		c.weighty = 0.25;
		c.gridx = 0;
		c.gridy = 2;
		board[2][0] = button;
		button.addActionListener(control);
		super.add(button, c);
		
		button = new JButton(b21);
		c.fill = GridBagConstraints.BOTH;
		c.weightx = 0.25;
		c.weighty = 0.25;
		c.gridx = 1;
		c.gridy = 2;
		board[2][1] = button;
		button.addActionListener(control);
		super.add(button, c);
		
		button = new JButton(b22);
		c.fill = GridBagConstraints.BOTH;
		c.weightx = 0.25;
		c.weighty = 0.25;
		c.gridx = 2;
		c.gridy = 2;
		board[2][2] = button;
		button.addActionListener(control);
		super.add(button, c);
	
///////Fouth row
		
		f = new JTextField();
		c.fill = GridBagConstraints.BOTH;
		c.weightx = 0.25;
		c.weighty = 0.05;
		c.gridx = 0;
		c.gridy = 3;
		super.add(f, c);
		
		button = new JButton(JOIN);
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 0.25;
		c.weighty = 0.05;
		c.gridx = 1;
		c.gridy = 3;
		button.addActionListener(control);
		super.add(button, c);
		
		button = new JButton(LEAVE);
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 0.25;
		c.weighty = 0.05;
		c.gridx = 2;
		c.gridy = 3;
		button.addActionListener(control);
		super.add(button, c);
///////Fifth row for errors
		error = new JTextField();
		c.fill = GridBagConstraints.BOTH;
		c.weightx = 0.25;
		c.weighty = 0.05;
		c.gridx = 0;
		c.gridy = 4;
		c.gridwidth = 3;
		error.setEditable(false);
		super.add(error,c);
	
		
//Scroll panel displaying other hosts
		c.fill = GridBagConstraints.BOTH;
		c.weightx = 0.25;
		c.weighty = 0.9;
		c.gridx = 3;
		c.gridy = 0;
		c.gridheight = 4;
		scrollPanel = new JPanel();
		scrollPanel.setLayout(new GridLayout(10,1));
		jsp = new JScrollPane(scrollPanel);
		super.add(jsp,c);
		
		
		jsp.validate();
		jsp.repaint();
		
//Button at bottom right to list online players
		
		button = new JButton(LIST);
		c.weightx = 0.25;
		c.weighty = 0.05;
		c.gridx = 4;
		c.gridy = 4;
		c.gridheight = 1;
		c.gridwidth = 1;
		button.addActionListener(control);
		super.add(button,c);
		
		this.setVisible(true);
	}
	/**
	 * Returns the text entered in the text field by the client
	 * @return
	 */
	public String getText()
	{
		return this.f.getText();
	}
	/**
	 * Sets online user buttons in the scroll pane
	 * @param onlineUsers
	 */
	public void updateOnlineUsers(ArrayList<UserBean> onlineUsers)
	{
		JButton user;
		if(onlineUsers == null)
		{
			//No users online, clear all buttons and users from the pane
			scrollPanel.removeAll();
		}else
		{
			scrollPanel.removeAll();
			//Have users to add
			for(UserBean b: onlineUsers)
			{
				user = new JButton("Invite: " + b.getUserName());
				user.addActionListener(control);
				scrollPanel.add(user);
				System.out.println("Adding " + b.getUserName());
			}
		}
		
		scrollPanel.validate();
		
		jsp.repaint();
		scrollPanel.repaint();
		super.repaint();
	}
	/**
	 * Sets the initial window which requests the directory server name/IP
	 * Allows the user to input the server or cancel and quit the application
	 */
	public void initialWindow()
	{
		initial = new JFrame();
		initial.setSize(400,100);
		initial.setDefaultCloseOperation(EXIT_ON_CLOSE);
		initial.setLayout(new GridBagLayout());
		initial.setResizable(false);
		initial.setLocationRelativeTo(null);
		
		GridBagConstraints c = new GridBagConstraints();
		JButton button; 
		
		ins = new JLabel();
		ins.setText("Enter the Directory server name or IP");
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 1.0;
		c.weighty = 0.5;
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 3;
		initial.add(ins,c);
		
		server = new JTextField();
		c.weightx = 0.5;
		c.weighty = 0.5;
		c.gridy = 1;
		c.gridwidth = 1;
		initial.add(server,c);
		
		button = new JButton(OKAY);
		c.gridx = 1;
		c.weightx = 0.15;
		button.addActionListener(control);
		initial.add(button, c);
		
		button = new JButton(CANCEL);
		c.gridx = 2;
		button.addActionListener(control);
		initial.add(button, c);
		
		initial.setVisible(true);
	}
	/**
	 * Returns the users directory server entry
	 * @return Text in the initial window text field
	 */
	public String getServerEntry()
	{
		return this.server.getText();
	}
	/**
	 * Sets an error message in the initial window in case entered directory server
	 * Could not be contacted
	 */
	public void setInvalidServerError()
	{
		System.out.println("Invalid server");
		ins.setText("Unable to contact directory server. Try again.");
		ins.setForeground(Color.RED);
	}
	/**
	 * Sets the incoming invite view, allows user to accept or reject an invite
	 * @param userName
	 */
	public void setInviteView(String userName)
	{
		invite = new JFrame();
		invite.setSize(400,100);
		invite.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		invite.setLayout(new GridBagLayout());
		invite.setResizable(false);
		invite.setLocationRelativeTo(null);
		
		GridBagConstraints c = new GridBagConstraints();
		JButton button; 
		
		this.inviteMessage = new JLabel();
		inviteMessage.setText("Game invite from: " + userName );
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 1.0;
		c.weighty = 0.5;
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 2;
		invite.add(inviteMessage,c);
		
		button = new JButton(ACCEPT);
		c.gridy = 1;
		c.gridx = 1;
		c.weightx = 0.15;
		button.addActionListener(control);
		invite.add(button, c);
		
		button = new JButton(REJECT);
		c.gridx = 2;
		button.addActionListener(control);
		invite.add(button, c);
		
		invite.setVisible(true);
	}
	/**
	 * Closes the invite view
	 */
	public void closeInvite()
	{
		invite.setVisible(false);
		invite.dispose();
	}
	/**
	 * Sets a button icon to x or o based on the piece passed
	 * @param row
	 * @param col
	 * @param piece
	 */
	public void setPieceIcon(int row, int col, int piece)
	{
		System.out.println("changing icon: ");
		JButton toChange = board[row][col];
		if(piece == -1)
		{
			toChange.setText("");
			toChange.setIcon(x);
			toChange.invalidate();
			super.invalidate();
		}else
		{
			toChange.setText("");
			toChange.setIcon(o);
			toChange.invalidate();
			super.invalidate();
		}
		super.validate();
		super.repaint();
	}
	/**
	 * Resets the game board to default values
	 */
	public void resetGameBoard()
	{
		for(int i = 0; i < 3; i++)
		{
			for(int j = 0; j < 3; j++)
			{
				JButton toChange = this.board[i][j];
				toChange.setIcon(null);
				toChange.setText(i + "" + j);
			}
		}
	}
	/**
	 * Sets the game starting message to visible and starts a 2.5 second timer to close the window
	 */
	public void setGameStartMessage()
	{
		System.out.println("Setting game start message");
		gameStarted.setVisible(true);
		t.restart();
		t.start();
	}
	/**
	 * Closes the start window by setting its visibility to false
	 */
	public void closeStartWindow()
	{
		gameStarted.setVisible(false);
	}
	/**
	 * Sets the game rejected message and starts a timer to close the window
	 */
	public void setGameRejectedMessage()
	{		
		gameRejected.setVisible(true);
		t.restart();
		t.start();
	}
	/**
	 * Closes the game rejected window by setting its visibility to false
	 */
	public void closeRejectedWindow()
	{
		gameRejected.setVisible(false);
	}
	/**
	 * Sets the win message and starts a timer to close the window
	 */
	public void setWinMessage()
	{
		System.out.println("Setting win message");
		win.setVisible(true);
		t.restart();
		t.start();
	}
	/**
	 * Closes the win window by setting its visibility to false
	 */
	public void closeWinWindow()
	{
		win.setVisible(false);
	}
	/**
	 * Sets the lose message window and starts a timer to close the window
	 */
	public void setLoseMessage()
	{
		System.out.println("Setting lose message");

		lose.setVisible(true);
		t.restart();
		t.start();
	}
	/**
	 * Closes the lose window by setting its visibility to false
	 */
	public void closeLoseWindow()
	{
		lose.setVisible(false);
	}
	/**
	 * Sets the tie message and starts a timer to close the window
	 */
	public void setTieMessage()
	{
		System.out.println("Setting tie message");

		tie.setVisible(true);
		t.restart();
		t.start();
	}
	/**
	 * Closes the tie window by setting its visibility to false
	 */
	public void closeTieWindow()
	{
		tie.setVisible(false);
	}
	/**
	 * Catches timer events and closes popup window, also stops the timer to prevent further
	 * timer event from firing
	 */
	public void actionPerformed(ActionEvent e) 
	{
		t.stop();
		closeLoseWindow();
		closeWinWindow();
		closeStartWindow();
		closeRejectedWindow();
		closeTieWindow();
	}
}
