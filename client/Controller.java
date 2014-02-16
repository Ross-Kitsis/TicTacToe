package client;

import java.awt.event.*;
import java.util.*;

import Data.UserBean;

public class Controller implements ActionListener
{
	private View v;
	private Model m;
	
	private ArrayList<UserBean> onlineUsers;
	
	private String server;
	
	public Controller(View v, Model m) 
	{
		this.v = v;
		this.m = m;
	}
	@Override
	public void actionPerformed(ActionEvent e) 
	{
		String event = e.getActionCommand();
		
		System.out.println(event);
		
		if(event.equals("JOIN"))
		{
			//System.out.println("Join pressed" + v.getText());
			if(v.getText().length() == 0)
			{
				System.out.println("No name entered");
			}
			m.joinServer(v.getText());
		}else if(event.equals("LEAVE"))
		{
			m.leaveServer();
		}else if(event.equals("LIST"))
		{
			onlineUsers = m.listOnlineUsers();
			v.updateOnlineUsers(onlineUsers);
		}else if(event.equals("CANCEL"))
		{
			System.exit(0);
		}else if(event.equals("OKAY"))
		{
			//Need to create a method in model to verify server name is valid and server accepting connections
		}
	}
}
