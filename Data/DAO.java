package Data;

import java.util.*;
import java.util.concurrent.Semaphore;

/**
 * A data access object used for reading and writing list data structure
 * A single point of access for accessing data
 *
 */
public class DAO 
{
	private int MAX_CONCURRENT_CONNECTIONS = 1;
	private final Semaphore semaphore = new Semaphore(MAX_CONCURRENT_CONNECTIONS);
	private List<UserBean> users;
	
	public DAO()
	{
		users = new ArrayList<UserBean>();
	}
	/**
	 * Adds a host to the list of online hosts
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
	}
	/**
	 * Removes a host from the list of online hosts if it exists in the list of online hosts
	 * @param u
	 */
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

	}
	/**
	 * Returns the list of online users
	 * @return
	 */
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
