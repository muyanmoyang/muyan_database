package muyanmoyang.database.reservation;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.TreeMap;

/**
 * @author dyp
 * 
 */
public interface ReserRemote extends Remote {
	/**
	 * @param xid ---ID号
	 * @param custName ---客户姓名
	 * @param flightNum ---预订的航班号
	 * @param location ----到达的地点
	 * @param needCar ----是否需要车
	 * @param needRoom ---是否需要房间
	 * @return
	 * @throws RemoteException
	 */
	public boolean Reserve(int xid, String custName, String flightNum,String location, boolean needCar,boolean needRoom)
											throws RemoteException;
	// 是否信誉
	public boolean dishonour(Hashtable<String, Reservation> temp,String custName, int resvType)throws RemoteException;
	public HashSet<String> getReserKeys() throws RemoteException;
	// 提交操作和回退操作
	public void commit(int xid) throws RemoteException;
	public void rollback(int xid) throws RemoteException;
	// 获得预订信息
	public TreeMap<String, Reservation> getInfo() throws RemoteException;
}
