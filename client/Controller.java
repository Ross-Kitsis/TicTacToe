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
		if(event.equals("JOIN"))
		{
			System.out.println("Join pressed" + v.getText());
			if(v.getText().length() == 0)
			{
				System.out.println("No name entered");
			}
		}
	}
}
