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
	public void leave(String hostName)
	{
		ArrayList<UserBean> users;
		try{
			while(semaphore.tryAcquire() == false)
			{
				Thread.sleep(5);
			}
			users = this.deserialize();
			UserBean toRemove = null;
			if(users != null)
			{
				boolean canRemove = false;
				for(UserBean b: users)
				{
					if(b.getHostName().equals(hostName))
					{
						canRemove = true;
						toRemove = b;
					}
				}
				if(canRemove)
				{
					users.remove(toRemove);
				}
				this.serialize(users);
				this.semaphore.release();
			}
		}catch (Exception e)
		{
			System.out.println("Error Leaving");
			this.semaphore.release();
			System.exit(-3);
		}
	}
	/**
	 * Deserializes and returns object
	 * WILL ONLY BE CALLED BY 1 of JOIN, LEAVE or LIST which are protected by a semaphore. File will not be accessed concurrently
	 * @return
	 * @throws IOException 
	 * @throws ClassNotFoundException 
	 */
	private ArrayList<UserBean> deserialize() throws ClassNotFoundException, IOException
	{
		ArrayList<UserBean> users = null;
	
		FileInputStream fis = new FileInputStream(fileName);
		ObjectInputStream ois = new ObjectInputStream(fis);
		users = (ArrayList<UserBean>) ois.readObject();
		
		return users;
		
	}
	/**
	 * Serializes the passed object
	 * Protected by semaphores
	 * @param allHosts
	 * @throws IOException
	 */
	private void serialize(List<UserBean> allHosts) throws IOException
	{
		FileOutputStream out = new FileOutputStream(fileName);
		ObjectOutputStream oos = new ObjectOutputStream(out);
		oos.writeObject(allHosts);
		oos.close();
		out.close();
		
	}
}
