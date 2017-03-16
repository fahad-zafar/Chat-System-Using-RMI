
/*
 * 14-4347	Ali Asgher
 * 14-4027	Usman Nazir
 * 14-4225	Sara Tanzeel
 * 14-4048	Muhammad Fahad Zafar
 */

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

public class Server extends UnicastRemoteObject implements ServerInterface {

	public static class Admin extends Thread {

		private final Object lock = new Object();
		public Admin (ServerSocket s) {
			server = s;
		}

		public void run () {
			
			// only 1 thread can access this at a time
			synchronized (lock) {
				Socket socket = null;
				BufferedWriter bufferedWriter = null;
				try {
					socket = server.accept();
					bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
					for (String msg : history) {
						bufferedWriter.write((msg + "\n"));
					}
					bufferedWriter.flush();
				} catch (IOException e1) {
					e1.printStackTrace();
				} finally {
					try {
						socket.close();
						bufferedWriter.close();
					} catch (Exception e) {
						e.printStackTrace();
					}				
				}
			}
		}
	}
	
	static ServerSocket server  = null;
	private Map <String, ClientInterface> hm = new ConcurrentHashMap <String, ClientInterface> ();
	public static ArrayList <String> history = new ArrayList <String> ();
	
	public Server () throws RemoteException {} 	

	public void connect (ClientInterface c, String name) throws RemoteException {
		c.receiveMessage (name, " has Connected");					// to print in client's own textArea
		sendMessage (c, " has connected");							// to print in other clients' textAreas
		hm.put(name, c);											// put record in hashmap
	}

	public void disConnect (ClientInterface c) throws RemoteException {
		hm.remove(c.getName(), c);									// remove client from Hashmap
		c.receiveMessage (c.getName() , " has Disconnected");		// to print in client's own textArea
		sendMessage (c, " has Disconnected");						// to print in other clients' textAreas
	}

	public void sendMessage (ClientInterface c, String msg) throws RemoteException {
		
		// to save only last 10 messages
		if (history.size() == 10)				
			history.remove(0);
		history.add (c.getName() + ": " + msg);
		new Admin(server).start();									// start a new admin thread with each new addition

		// invoke "receiveMessage" of all clients so that they print the provided message
		for (Entry<String, ClientInterface> entry : hm.entrySet()) {
			ClientInterface temp = entry.getValue();
			temp.receiveMessage(c.getName(), msg);
		}
	}
}
