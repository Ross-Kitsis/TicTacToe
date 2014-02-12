package client;

import java.awt.event.*;

public class Controller implements ActionListener
{
	private View v;
	private Model m;
	
	public Controller(View v, Model m) 
	{
		this.v = v;
		this.m = m;
	}
	@Override
	public void actionPerformed(ActionEvent e) 
	{
		String event = e.getActionCommand();
	}
}
