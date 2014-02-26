package client;

/**
 * Entry point for client application.
 * Instantiates the view and model which create the controller
 *
 */
public class Driver 
{
	public static void main(String args[])
	{
		new View(new Model());
	}
}
