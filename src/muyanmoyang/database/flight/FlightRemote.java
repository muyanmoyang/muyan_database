package muyanmoyang.database.flight;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.HashSet;
import java.util.Hashtable;


/**
 * @author dyp
 *RMI功能接口扩展了remote接口
 */
public interface FlightRemote extends Remote{
//	查询某架飞机的可预订的座位数
	public int searchFlightAvail(int xid,String flightNum) throws RemoteException;
//	预订某架飞机
	public boolean fixFlight(int xid,String flightNum) throws RemoteException;
//	获取飞机的航班的班次信息
	public HashSet<String> getFlightNums() throws RemoteException;
//	进行提交和回退操作
	public void commit(int xid) throws RemoteException;
	public void rollback(int xid) throws RemoteException;
//	获取航班的具体信息
	public Hashtable<String, Flight> getInfo() throws RemoteException;
//	插入新飞机的信息
	public boolean insertFlight(String flightNum,int price,int numSeats) throws RemoteException;
}
