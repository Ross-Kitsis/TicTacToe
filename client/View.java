package client;

import java.awt.*;
import java.util.*;

import javax.swing.*;

import Data.UserBean;

/**
 * Handles all view related functionality for the TicTacTow game
 * @author rkitsis
 *  
 */


public class View extends JFrame
{
	//Button variables
	public static final long serialVersionUID = 1L;
	public static final String JOIN = "JOIN";
	public static final String REFRESH = "REFRESH";
	public static final String LEAVE = "LEAVE";
	public static final String LIST = "LIST";
	public static final String OKAY = "OKAY";
	public static final String CANCEL = "CANCEL";
	
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
	
	//Model
	public Model m;
	
	private Controller control;
	
	public View(Model m)
	{
		super.setSize(800,800);
		super.setDefaultCloseOperation(EXIT_ON_CLOSE);
		super.setLayout(new GridBagLayout());
		this.m = m;
		super.setResizable(false);
		super.setLocationRelativeTo(null);
		control = new Controller(this,m);
		this.initialWindow();
	}
	public void setNewBoard()
	{
		initial.setVisible(false);
		initial.dispose();
		
		GridBagConstraints c = new GridBagConstraints();
		
		JButton button; 
		ImageIcon x = new ImageIcon("x.jpg");

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
		super.add(button, c);
		
		button = new JButton(b01);
		c.fill = GridBagConstraints.BOTH;
		c.weightx = 0.25;
		c.weighty = 0.25;
		c.gridx = 1;
		c.gridy = 0;
		board[0][1] = button;
		//button.setIcon(x);
		super.add(button, c);
		
		button = new JButton(b02);
		c.fill = GridBagConstraints.BOTH;
		c.weightx = 0.25;
		c.weighty = 0.25;
		c.gridx = 2;
		c.gridy = 0;
		board[0][2] = button;
		//button.setIcon(x);
		super.add(button, c);
		
///////Second row
		
		button = new JButton(b10);
		c.fill = GridBagConstraints.BOTH;
		c.weightx = 0.25;
		c.weighty = 0.25;
		c.gridx = 0;
		c.gridy = 1;
		board[1][0] = button;
		super.add(button, c);
		
		button = new JButton(b11);
		c.fill = GridBagConstraints.BOTH;
		c.weightx = 0.25;
		c.weighty = 0.25;
		c.gridx = 1;
		c.gridy = 1;
		board[1][1] = button;
		super.add(button, c);
		
		button = new JButton(b12);
		c.fill = GridBagConstraints.BOTH;
		c.weightx = 0.25;
		c.weighty = 0.25;
		c.gridx = 2;
		c.gridy = 1;
		board[1][2] = button;
		super.add(button, c);
		
///////Thid row
		
		
		button = new JButton(b20);
		c.fill = GridBagConstraints.BOTH;
		c.weightx = 0.25;
		c.weighty = 0.25;
		c.gridx = 0;
		c.gridy = 2;
		board[2][0] = button;
		super.add(button, c);
		
		button = new JButton(b21);
		c.fill = GridBagConstraints.BOTH;
		c.weightx = 0.25;
		c.weighty = 0.25;
		c.gridx = 1;
		c.gridy = 2;
		board[2][1] = button;
		super.add(button, c);
		
		button = new JButton(b22);
		c.fill = GridBagConstraints.BOTH;
		c.weightx = 0.25;
		c.weighty = 0.25;
		c.gridx = 2;
		c.gridy = 2;
		board[2][2] = button;
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
		//button = new JButton("test");
		c.fill = GridBagConstraints.BOTH;
		c.weightx = 0.25;
		c.weighty = 0.05;
		c.gridx = 0;
		c.gridy = 4;
		c.gridwidth = 3;
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
	public String getText()
	{
		return this.f.getText();
	}
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
	public String getServerEntry()
	{
		return this.server.getText();
	}
	public void setInvalidServerError()
	{
		System.out.println("Invalid server");
		ins.setText("Unable to contact directory server. Try again.");
		ins.setForeground(Color.RED);
	}
	public void setModel(Model m)
	{
		this.m = m;
	}
}
