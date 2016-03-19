package muyanmoyang.database.car;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.HashSet;
import java.util.Hashtable;


/**
 * @author dyp
 * 编写Rmi功能接口，该接口必须扩展自java.rmi.Remote，
 * 而且接口中每一个方法都必须抛出一个java.rmi.RemoteException。
 *
 */
public interface CarRemote extends Remote{
//	查询可用的car
	public int searchCarAvail(int xid,String location) throws RemoteException;
//	预订car
	public boolean fixCar(int xid,String location) throws RemoteException;
//	返回的是 每个地点的信息
	public HashSet<String> getLocation() throws RemoteException;
//	提交信息和回退
	public void commit(int xid) throws RemoteException;
	public void rollback(int xid) throws RemoteException;
//	获得car的信息
	public Hashtable<String, Car> getInfo() throws RemoteException;
//	插入car的信息
	public boolean insertCar(String location,int price,int numCars) throws RemoteException;
}
