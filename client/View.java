package client;

import java.awt.*;
import javax.swing.*;

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
	//Model
	public Model m;
	
	public View(Model m)
	{
		super.setSize(800,800);
		super.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		super.setLayout(new GridBagLayout());
		this.m = m;
		this.setResizable(false);
		this.setNewBoard();
	}
	public void setNewBoard()
	{
		GridBagConstraints c = new GridBagConstraints();
		
		JButton button; 
		ImageIcon x = new ImageIcon("x.jpg");

///////First fow of tictactow
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
		button.addActionListener(new Controller(this,m));
		super.add(button, c);
		
		button = new JButton(LEAVE);
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 0.25;
		c.weighty = 0.05;
		c.gridx = 2;
		c.gridy = 3;
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
		JPanel t = new JPanel();
		t.setLayout(new GridLayout(10,1));
		JScrollPane jsp = new JScrollPane(t);
		//jsp.setLayout(new GridLayout(1,10));
		t.add(new Button("Test1"));
		t.add(new Button("Test2"));
		t.validate();
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
		super.add(button,c);
		
		
	}
	public String getText()
	{
		return this.f.getText();
	}
}
