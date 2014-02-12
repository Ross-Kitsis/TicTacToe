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
	private static final long serialVersionUID = 1L;
	private static final String JOIN = "JOIN";
	private static final String REFRESH = "REFRESH";
	private static final String LEAVE = "LEAVE";
	private static final String LIST = "LIST";
	
	//Game board buttoms
	private static final String b00 = "00";
	private static final String b01 = "01";
	private static final String b02 = "02";
	private static final String b10 = "10";
	private static final String b11 = "11";
	private static final String b12 = "12";
	private static final String b20 = "20";
	private static final String b21 = "21";
	private static final String b22 = "22";
	
	//Model
	private Model m;
	
	public View(Model m)
	{
		super.setSize(800,800);
		super.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		super.setLayout(new GridBagLayout());
		this.m = m;
		this.setNewBoard();
	}
	private void setNewBoard()
	{
		GridBagConstraints c = new GridBagConstraints();
		
		JButton button; 

///////First fow of tictactow
		button = new JButton(b00);
		c.fill = GridBagConstraints.BOTH;
		c.weightx = 0.25;
		c.weighty = 0.3;
		c.gridx = 0;
		c.gridy = 0;
		super.add(button, c);
		
		button = new JButton(b01);
		c.fill = GridBagConstraints.BOTH;
		c.weightx = 0.25;
		c.weighty = 0.3;
		c.gridx = 1;
		c.gridy = 0;
		super.add(button, c);
		
		button = new JButton(b02);
		c.fill = GridBagConstraints.BOTH;
		c.weightx = 0.25;
		c.weighty = 0.3;
		c.gridx = 2;
		c.gridy = 0;
		super.add(button, c);
		
///////Second row
		
		button = new JButton(b10);
		c.fill = GridBagConstraints.BOTH;
		c.weightx = 0.25;
		c.weighty = 0.3;
		c.gridx = 0;
		c.gridy = 1;
		super.add(button, c);
		
		button = new JButton(b11);
		c.fill = GridBagConstraints.BOTH;
		c.weightx = 0.25;
		c.weighty = 0.3;
		c.gridx = 1;
		c.gridy = 1;
		super.add(button, c);
		
		button = new JButton(b12);
		c.fill = GridBagConstraints.BOTH;
		c.weightx = 0.25;
		c.weighty = 0.3;
		c.gridx = 2;
		c.gridy = 1;
		super.add(button, c);
		
///////Thid row
		
		
		button = new JButton(b20);
		c.fill = GridBagConstraints.BOTH;
		c.weightx = 0.25;
		c.weighty = 0.3;
		c.gridx = 0;
		c.gridy = 2;
		super.add(button, c);
		
		button = new JButton(b21);
		c.fill = GridBagConstraints.BOTH;
		c.weightx = 0.25;
		c.weighty = 0.3;
		c.gridx = 1;
		c.gridy = 2;
		super.add(button, c);
		
		button = new JButton(b22);
		c.fill = GridBagConstraints.BOTH;
		c.weightx = 0.25;
		c.weighty = 0.3;
		c.gridx = 2;
		c.gridy = 2;
		super.add(button, c);
	
///////Fouth row
		
		JTextField f = new JTextField("Name");
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
		super.add(button, c);
		
		button = new JButton(LEAVE);
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 0.25;
		c.weighty = 0.05;
		c.gridx = 2;
		c.gridy = 3;
		super.add(button, c);
		
		
//Scroll panel displaying other hosts
		c.fill = GridBagConstraints.BOTH;
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
		
	}
}
