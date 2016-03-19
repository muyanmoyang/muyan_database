package muyanmoyang.database.hotel;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Hashtable;

/**
 * @author dyp 1.RMI功能接口继承了remote接口
 * 
 */
public interface HotelRemote extends Remote {
	// 查询可预订的房间数
	public int searchRoomAvail(int xid, String location) throws RemoteException;

	// 预订房间
	public boolean fixRoom(int xid, String location) throws RemoteException;

	// 提交操作和回退操作
	public void commit(int xid) throws RemoteException;

	public void rollback(int xid) throws RemoteException;

	//获取所以hotal的具体信息
	public Hashtable<String, Hotel> getInfo() throws RemoteException;

	// 插入新的hotal信息
	public boolean insertHotel(String location, int price, int numRooms)
											throws RemoteException;
}
