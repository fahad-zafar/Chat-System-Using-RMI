
/*
 * 14-4347	Ali Asgher
 * 14-4027	Usman Nazir
 * 14-4225	Sara Tanzeel
 * 14-4048	Muhammad Fahad Zafar
 */

import java.net.ServerSocket;
import java.rmi.Naming;

public class Start {
	
	final static int BROWSER_PORT = 9090;
	
	public static void main(String[] args) {
		try {
			ServerSocket server = new ServerSocket (BROWSER_PORT);		// connection with browser
			ServerInterface s = new Server ();
			Naming.rebind("rmi://localhost/obj", s);					// unique name of server object
			System.out.println("Server Has Started");
			new Server.Admin(server).start();							// start admin thread
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}