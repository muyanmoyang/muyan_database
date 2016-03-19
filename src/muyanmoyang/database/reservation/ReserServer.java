package muyanmoyang.database.reservation;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author dyp 
 * 1.服务器端的实现 
 * 2.实现接口中功能的实现
 */
public class ReserServer implements ReserRemote {
	private static TreeMap<String, Reservation> data;
	public Hashtable<Integer, TreeMap<String, Reservation>> back;
	private final ReentrantReadWriteLock rwl = new ReentrantReadWriteLock();
	private final Lock r = rwl.readLock();
	private final Lock w = rwl.writeLock();

	/**
	 * 构造函数
	 */
	private ReserServer() {
		back = new Hashtable<Integer, TreeMap<String, Reservation>>();
		data = new TreeMap<String, Reservation>();
	}

	public static void main(String[] args) {
		try {
			ReserServer reserServer = new ReserServer();
			ReserRemote stub = (ReserRemote) UnicastRemoteObject.exportObject(
													reserServer, 0);
			Registry registry = LocateRegistry.createRegistry(10004);
			registry.bind("reserServer", stub);
			System.err.println("reserServer ready");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public boolean Reserve(int xid, String custName, String flightNum,
											String location, boolean needCar,
											boolean needRoom) {
		TreeMap<String, Reservation> temp = new TreeMap<String, Reservation>();
		w.lock();
		try {
			for (Iterator<String> it = data.keySet().iterator(); it.hasNext();) {
				String str = it.next();
				temp.put(str, data.get(str).clone());
			}
		} finally {
			w.unlock();
		}

		boolean result = true;
		if (!reserve(temp, custName, 1, flightNum)) {
			result = false;

			return result;
		}
		if (needCar) {
			if (!reserve(temp, custName, 3, location)) {
				System.out.println(custName + " car " + location);
				result = false;
				return result;
			}
		}
		if (needRoom) {
			if (!reserve(temp, custName, 2, location)) {
				System.out.println(custName + " hotel " + location);
				result = false;
				return result;
			}
		}
		back.put(xid, temp);

		return result;
	}

	public void commit(int xid) {
		w.lock();
		try {
			if (back.get(xid) != null) {
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

	public boolean reserve(TreeMap<String, Reservation> temp, String custName,
											int resvType, String detail) {
		// resvType
		// 1: flight
		// 2: hotel
		// 3: car
		// 这个put()方法用来插入，且判断是否已经被插入，如果插入则返回的将是先去的值
		boolean result = temp.put(custName + resvType, new Reservation(
												custName, resvType, detail)) == null;
		if (resvType == 1) {
			if (result == false) {
				System.out.println(custName + " flight " + detail + " failed");
			} else {
				System.out.println(custName + "flight " + detail + " success");
			}
		} else if (resvType == 2) {
			if (result == false) {
				System.out.println(custName + " hotel " + detail + " failed");
			} else {
				System.out.println(custName + " hotel " + detail + " success");
			}
		} else {
			if (result == false) {
				System.out.println(custName + " car " + detail + " failed");
			} else {
				System.out.println(custName + " car " + detail + " success");
			}
		}
		return result;
	}

	public boolean dishonour(Hashtable<String, Reservation> temp,String custName, int resvType) {

		return temp.remove((String) (custName + resvType)) != null;

	}

	public HashSet<String> getReserKeys() {

		Set<String> set;
		r.lock();
		try {
			set = data.keySet();
		} finally {
			r.unlock();
		}

		return new HashSet<String>(set);
	}

	public TreeMap<String, Reservation> getInfo() {
		return data;
	}
}
