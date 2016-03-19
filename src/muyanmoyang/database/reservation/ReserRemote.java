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
	 * @param xid ---ID��
	 * @param custName ---�ͻ�����
	 * @param flightNum ---Ԥ���ĺ����
	 * @param location ----����ĵص�
	 * @param needCar ----�Ƿ���Ҫ��
	 * @param needRoom ---�Ƿ���Ҫ����
	 * @return
	 * @throws RemoteException
	 */
	public boolean Reserve(int xid, String custName, String flightNum,String location, boolean needCar,boolean needRoom)
											throws RemoteException;
	// �Ƿ�����
	public boolean dishonour(Hashtable<String, Reservation> temp,String custName, int resvType)throws RemoteException;
	public HashSet<String> getReserKeys() throws RemoteException;
	// �ύ�����ͻ��˲���
	public void commit(int xid) throws RemoteException;
	public void rollback(int xid) throws RemoteException;
	// ���Ԥ����Ϣ
	public TreeMap<String, Reservation> getInfo() throws RemoteException;
}
