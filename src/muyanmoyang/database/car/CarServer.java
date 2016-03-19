package muyanmoyang.database.car;

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
 * 1.实现RMI功能
 * 2.编写car服务端程序
 * 3.在将要绑定对象car的端口上创建一个注册器
 *
 */
public class CarServer implements CarRemote {
	private static Hashtable<String, Car> data;
	public Hashtable<Integer, Hashtable<String, Car>> back;
	private final ReentrantReadWriteLock rwl = new ReentrantReadWriteLock();
	private final Lock r = rwl.readLock();
	private final Lock w = rwl.writeLock();

	/**
	 * 构造函数--初始化data
	 */
	private CarServer() {
		back = new Hashtable<Integer, Hashtable<String, Car>>();
		data = new Hashtable<String, Car>();
		data.put("北京", new Car(10, 1000, 1000));
		data.put("上海", new Car(10, 900, 900));
		data.put("广州", new Car(9, 800, 800));
	}

	public static void main(String[] args) {
		try {
//			要导出的远程对象
			CarServer carServer = new CarServer();
//			导出该远程对象
			CarRemote stub = (CarRemote) UnicastRemoteObject.exportObject(carServer, 0);
//			先用java.rmi.registry.LocateRegistry在将要绑定对象的端口上创建一个注册器
			Registry registry = LocateRegistry.createRegistry(10002);
//			将远程对象实例绑定到rmi:///carServer上 
			registry.bind("carServer", stub);
			System.err.println("carServer ready");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public int searchCarAvail(int xid, String location) {
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
	/* (non-Javadoc)
	 * @see car.CarRemote#insertCar(java.lang.String, int, int)
	 * 实现了接口中的insertCar()方法,插入car的信息
	 */
	public boolean insertCar(String location,int price,int numCars) {
		w.lock();
		try{
			if(!data.keySet().contains(location)) {
				data.put(location, new Car(price,numCars,numCars));
				return true;
			} else {
				return false;
			}
			
		}finally{
			w.unlock();
		}
	}

	public boolean fixCar(int xid, String location) {

		Hashtable<String, Car> temp = new Hashtable<String, Car>();
		r.lock();
		try {
//			把所有car（包括地址）的信息放入temp中
			for (Iterator<String> it = data.keySet().iterator(); it.hasNext();) {
				String str = it.next();
				temp.put(str, data.get(str).clone());
			}
		} finally {
			r.unlock();
		}

		if (temp.get(location) != null) {
			temp.get(location).fixCar();
			back.put(xid, temp);
			System.out.println("定出租成功");
			return true;
		} else {
			System.out.println("定出租失败");
			return false;
		}
	}

	public HashSet<String> getLocation() {
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
			if(back.get(xid) != null) {
				data = back.get(xid);
			}
			

		} finally {
			w.unlock();
		}
		System.out.println(xid + ".......commit");
	}

	public void rollback(int xid) {
		back.remove(xid);
		System.out.println(xid + "......rollback");
	}

	/* (non-Javadoc)
	 * @see car.CarRemote#getInfo()
	 * 返回car的信息
	 */
	public Hashtable<String, Car> getInfo() {
		Hashtable<String, Car> table;
		r.lock();
		try {
			table = data;

		} finally {
			r.unlock();
		}
		return table;
	}

}
