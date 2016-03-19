package muyanmoyang.database.flight;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.HashSet;
import java.util.Hashtable;


/**
 * @author dyp
 *RMI���ܽӿ���չ��remote�ӿ�
 */
public interface FlightRemote extends Remote{
//	��ѯĳ�ܷɻ��Ŀ�Ԥ������λ��
	public int searchFlightAvail(int xid,String flightNum) throws RemoteException;
//	Ԥ��ĳ�ܷɻ�
	public boolean fixFlight(int xid,String flightNum) throws RemoteException;
//	��ȡ�ɻ��ĺ���İ����Ϣ
	public HashSet<String> getFlightNums() throws RemoteException;
//	�����ύ�ͻ��˲���
	public void commit(int xid) throws RemoteException;
	public void rollback(int xid) throws RemoteException;
//	��ȡ����ľ�����Ϣ
	public Hashtable<String, Flight> getInfo() throws RemoteException;
//	�����·ɻ�����Ϣ
	public boolean insertFlight(String flightNum,int price,int numSeats) throws RemoteException;
}
