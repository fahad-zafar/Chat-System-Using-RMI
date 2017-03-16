
/*
 * 14-4347	Ali Asgher
 * 14-4027	Usman Nazir
 * 14-4225	Sara Tanzeel
 * 14-4048	Muhammad Fahad Zafar
 */

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ClientInterface extends Remote {
	public void receiveMessage (String name, String msg) throws RemoteException ;
	public String getName () throws RemoteException;	
	public void setGUI (GUI g) throws RemoteException;
}
