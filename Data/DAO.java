package Data;

import java.io.*;
import java.util.*;
import java.util.concurrent.Semaphore;

/**
 * A data access object used for reading and writing to serialized data.
 * A single point of access for accessing data
 * @author rkitsis
 *
 */
public class DAO 
{
	private static final String fileName = "users.ser";
	
	private int MAX_CONCURRENT_CONNECTIONS = 1;
	private final Semaphore semaphore = new Semaphore(
			MAX_CONCURRENT_CONNECTIONS);
	private List<UserBean> users;
	
	public DAO()
	{
		users = new ArrayList<UserBean>();
//		File f = new File(fileName);
//		if(!f.exists())
//		{
//			
//			try 
//			{
//				f.createNewFile();
//				serialize(allHosts);
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//				System.out.println("Fatal server error: Could not create file to store online users, exiting....");
//				System.exit(-1);
//			}
//		}
	}
	/**
	 * Allows a host to join the list of online users
	 * @param hostName
	 * @throws InterruptedException 
	 */
	public void join(UserBean u) throws InterruptedException
	{
		while(semaphore.tryAcquire() == false)
		{
			Thread.sleep(5);
		}
		
		boolean canAdd = true;
		for(UserBean b: users)
		{
			if(b.getHostName().equals(u.getHostName()))
			{
				System.out.println("Already in list");
				canAdd = false;
			}
		}
		
		if(canAdd)
		{
			users.add(u);
		}
		
		semaphore.release();
		
//		ArrayList<UserBean> users;
//		try{
//			while(semaphore.tryAcquire() == false)
//			{
//				Thread.sleep(5);
//			}
//			users = this.deserialize();
//			if(users != null)
//			{
//				//Check to see if user has already joined; no point in joining twice
//				boolean canAdd = true;
//				for(UserBean b: users)
//				{
//					if(b.getHostName().equals(hostName))
//					{
//						canAdd = false;
//					}
//				}
//				
//				if(canAdd == true)
//				{
//					users.add(new UserBean(hostName, userName));
//				}
//				this.serialize(users);
//				semaphore.release();
//			}else
//			{
//				System.out.println("Should not be here");
//				System.exit(-2);
//			}
//			
//		}catch(Exception e)
//		{
//			e.printStackTrace();
//			System.out.println("Error joining");
//			semaphore.release();
//		}
	}
	public void leave(UserBean u)
	{
		System.out.println("Leaving.....");
		try{
			while(semaphore.tryAcquire() == false)
			{
				Thread.sleep(5);
			}
			String hostName = u.getHostName();
			for(int i = 0; i < users.size(); i++)
			{
				System.out.println(hostName);
				if(users.get(i).getHostName().equals(hostName))
				{
					System.out.println("Setting to null");
					users.set(i, null);
					break;
				}
			}
			users.removeAll(Collections.singleton(null));
			
		}catch(Exception e)
		{
			System.out.println("Error leaving");
		}
		
		semaphore.release();
//		ArrayList<UserBean> users;
//		try{
//			while(semaphore.tryAcquire() == false)
//			{
//				Thread.sleep(5);
//			}
//			users = this.deserialize();
//			UserBean toRemove = null;
//			if(users != null)
//			{
//				boolean canRemove = false;
//				for(UserBean b: users)
//				{
//					if(b.getHostName().equals(hostName))
//					{
//						canRemove = true;
//						toRemove = b;
//					}
//				}
//				if(canRemove)
//				{
//					users.remove(toRemove);
//				}
//				this.serialize(users);
//				this.semaphore.release();
//			}
//		}catch (Exception e)
//		{
//			System.out.println("Error Leaving");
//			this.semaphore.release();
//			System.exit(-3);
//		}
	}
	public ArrayList<UserBean> list()
	{
		ArrayList<UserBean> toReturn = new ArrayList<UserBean>();
		try{
		while(semaphore.tryAcquire())
		{
			Thread.sleep(5);
		}
			toReturn.addAll(users);
		}catch(Exception e)
		{
			System.out.println("Error listing");
		}
		semaphore.release();
		return toReturn;
	}
}
