
/*
 * 14-4347	Ali Asgher
 * 14-4027	Usman Nazir
 * 14-4225	Sara Tanzeel
 * 14-4048	Muhammad Fahad Zafar
 */

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class Client extends UnicastRemoteObject implements ClientInterface {

	private String name;
	private GUI gui;

	public Client (String name) throws RemoteException {
		this.name = name;
	}

	// to sow each recevied message to client's own textArea
	public void receiveMessage (String name, String msg) throws RemoteException {
		this.gui.textArea.append(name + ": " + msg + "\n");
	} 

	// to return the name of each client
	public String getName () throws RemoteException {
		return name;
	}
	
	// to set the GUI of each client
	public void setGUI (GUI cg) throws RemoteException { 
		this.gui = cg;
	} 
}
