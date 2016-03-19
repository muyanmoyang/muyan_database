package muyanmoyang.database.customer;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.HashSet;

/**
 * @author dyp
 *
 */
public interface CustomerRemote extends Remote{
	public HashSet<String> getCustomers() throws RemoteException;
	public boolean insertCust(String custName) throws RemoteException;
}
