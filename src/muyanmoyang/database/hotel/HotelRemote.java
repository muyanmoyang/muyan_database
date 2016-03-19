package muyanmoyang.database.hotel;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Hashtable;

/**
 * @author dyp 1.RMI���ܽӿڼ̳���remote�ӿ�
 * 
 */
public interface HotelRemote extends Remote {
	// ��ѯ��Ԥ���ķ�����
	public int searchRoomAvail(int xid, String location) throws RemoteException;

	// Ԥ������
	public boolean fixRoom(int xid, String location) throws RemoteException;

	// �ύ�����ͻ��˲���
	public void commit(int xid) throws RemoteException;

	public void rollback(int xid) throws RemoteException;

	//��ȡ����hotal�ľ�����Ϣ
	public Hashtable<String, Hotel> getInfo() throws RemoteException;

	// �����µ�hotal��Ϣ
	public boolean insertHotel(String location, int price, int numRooms)
											throws RemoteException;
}
