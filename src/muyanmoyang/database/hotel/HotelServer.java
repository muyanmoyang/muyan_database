package muyanmoyang.database.hotel;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author dyp
 *��HotelServer����������˵ı���
 *ʵ����RMI�ӿ��еĹ��ܺ���
 *
 */
public class HotelServer implements HotelRemote {
	private static Hashtable<String, Hotel> data;
	public Hashtable<Integer, Hashtable<String, Hotel>> back;
	private final ReentrantReadWriteLock rwl = new ReentrantReadWriteLock();
	private final Lock r = rwl.readLock();
	private final Lock w = rwl.writeLock();

	private HotelServer() {
		back = new Hashtable<Integer, Hashtable<String, Hotel>>();
		data = new Hashtable<String, Hotel>();
		data.put("����", new Hotel(200, 100, 100));
		data.put("�Ϻ�", new Hotel(150, 150, 150));
		if(data != null) {
			System.out.println("data constroner not null");
		}
	}

	public static void main(String[] args) {
		try {
			HotelServer hotelServer = new HotelServer();
			if(data == null) {
				System.out.println("data maintain null");
			}
			HotelRemote stub = (HotelRemote) UnicastRemoteObject.exportObject(
					hotelServer, 0);
			Registry registry = LocateRegistry.createRegistry(10001);
			registry.bind("hotelServer", stub);
			System.err.println("hotelServer ready");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public int searchRoomAvail(int xid, String location) {
		r.lock();
		try {
			if (data.get(location) != null) {
				return data.get(location).getNumAvail();
			} else {
				return -1;
			}
		} finally {
			r.unlock();
		}

	}
	public boolean insertHotel(String location,int price,int numRooms) {
		w.lock();
		try{
			if(!data.keySet().contains(location)) {
				data.put(location, new Hotel(price,numRooms,numRooms));
				return true;
			} else {
				return false;
			}
		} finally{
			w.unlock();
		}
	}
	public boolean fixRoom(int xid, String location) {
		Hashtable<String, Hotel> temp = new Hashtable<String, Hotel>();
		w.lock();
		try {
			for (Iterator<String> it = data.keySet().iterator(); it.hasNext();) {
				String str = it.next();
				temp.put(str, data.get(str).clone());
			}
		} finally {
			w.unlock();
		}

		if (temp.get(location) != null) {
			temp.get(location).fixRoom();
			back.put(xid, temp);
			System.out.println("������  " + location + " �ɹ�");
			return true;
		} else {
			System.out.println("������ " + location + " ʧ��");
			return false;
		}
	}

	public void commit(int xid) {
		w.lock();
		try {
			if(back.get(xid) != null) {
				data = back.get(xid);
			}
			

		} finally {
			w.unlock();
		}
		System.out.println(xid + "......commit");
	}

	public void rollback(int xid) {
		back.remove(xid);
		System.out.println(xid + "......rollback");
	}

	public Hashtable<String, Hotel> getInfo() {
		Hashtable<String, Hotel> table;
		r.lock();
		try{
			table = data;
			if(table == null) {
				System.out.println("hotelserver null");
			}
			if(data == null) {
				System.out.println("data null");
			}
		}finally{
			r.unlock();
		}
		return table;
	}
}
