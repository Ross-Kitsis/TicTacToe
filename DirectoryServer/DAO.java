package DirectoryServer;

import java.io.File;

/**
 * A data access object used for reading and writing to serialized data.
 * A single point of access for accessing data
 * @author rkitsis
 *
 */
public class DAO 
{
	private static final String filename = "users.ser";
	public DAO()
	{
		File f = new File(filename);
		if(f.exists())
		{
			
		}else
		{
			
		}
	}
}
