
/*
 * 14-4347	Ali Asgher
 * 14-4027	Usman Nazir
 * 14-4225	Sara Tanzeel
 * 14-4048	Muhammad Fahad Zafar
 */

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ServerInterface extends Remote {	
	public void connect (ClientInterface c, String name) throws RemoteException ;
	public void disConnect (ClientInterface c) throws RemoteException;
	public void sendMessage (ClientInterface c, String msg) throws RemoteException ;
}