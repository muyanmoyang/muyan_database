package muyanmoyang.database.flight;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author dyp
 * 1.Flightserver对象远程操作的服务器端代码实现
 * 2.实现了FlightRemote中的RMI接口中的功能函数
 *
 */
public class FlightServer implements FlightRemote {
	private static Hashtable<String, Flight> data;
	public static Hashtable<Integer, Hashtable<String, Flight>> back;
	private final ReentrantReadWriteLock rwl = new ReentrantReadWriteLock();
	private final Lock r = rwl.readLock();
	private final Lock w = rwl.writeLock();

	/**
	 * 构造函数
	 */
	private FlightServer() {
		back = new Hashtable<Integer, Hashtable<String, Flight>>();
		data = new Hashtable<String, Flight>();
		data.put("MH430", new Flight(1000, 300, 300));

	}

	public static void main(String[] args) {
		try {
			FlightServer flightServer = new FlightServer();
//			导出远程操作对象
			FlightRemote stub = (FlightRemote) UnicastRemoteObject
					.exportObject(flightServer, 0);
			Registry registry = LocateRegistry.createRegistry(10000);
			registry.bind("flightServer", stub);
			System.err.println("flightServer ready");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public int searchFlightAvail(int xid, String flightNum) {
		r.lock();
		try {
			if (data.get(flightNum) != null) {
				return data.get(flightNum).getNumAvail();
			} else {
				return -1;
			}
		} finally {
			r.unlock();
		}

	}
	public boolean insertFlight(String flightNum,int price,int numSeats) {
		w.lock();
		try{
			if(!data.keySet().contains(flightNum)) {
				data.put(flightNum, new Flight(price,numSeats,numSeats));
				return true;
			} else{
				return false;
			}
			
		}finally{
			w.unlock();
		}
	}

	public boolean fixFlight(int xid, String flightNum) {
		Hashtable<String, Flight> temp = new Hashtable<String, Flight>();
		w.lock();
		try {
			for (Iterator<String> it = data.keySet().iterator(); it.hasNext();) {
				String str = it.next();
				temp.put(str, data.get(str).clone());
			}
			if (temp.get(flightNum) != null) {
				temp.get(flightNum).fixFlight();
				back.put(xid, temp);
				System.out.println("定火车  " + flightNum + " 成功");
				return true;
			} else {
				System.out.println("定火车 " + flightNum + " 失败");
				return false;
			}
		} finally {
			w.unlock();
		}

	}

	public HashSet<String> getFlightNums() {
		Set<String> set;
		r.lock();
		try {
			set = data.keySet();

		} finally {
			r.unlock();
		}

		return new HashSet<String>(set);
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

	public Hashtable<String, Flight> getInfo() {
		Hashtable<String, Flight> table;
		r.lock();
		try {
			table = data;
		} finally {
			r.unlock();
		}
		return table;
	}

}
