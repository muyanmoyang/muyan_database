package muyanmoyang.database.car;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.HashSet;
import java.util.Hashtable;


/**
 * @author dyp
 * ��дRmi���ܽӿڣ��ýӿڱ�����չ��java.rmi.Remote��
 * ���ҽӿ���ÿһ�������������׳�һ��java.rmi.RemoteException��
 *
 */
public interface CarRemote extends Remote{
//	��ѯ���õ�car
	public int searchCarAvail(int xid,String location) throws RemoteException;
//	Ԥ��car
	public boolean fixCar(int xid,String location) throws RemoteException;
//	���ص��� ÿ���ص����Ϣ
	public HashSet<String> getLocation() throws RemoteException;
//	�ύ��Ϣ�ͻ���
	public void commit(int xid) throws RemoteException;
	public void rollback(int xid) throws RemoteException;
//	���car����Ϣ
	public Hashtable<String, Car> getInfo() throws RemoteException;
//	����car����Ϣ
	public boolean insertCar(String location,int price,int numCars) throws RemoteException;
}
