package muyanmoyang.database.customer;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author dyp
 * 提供二个函数 insertCust(String custName)和getCustomers()
 * 
 */
public class CustomerServer implements CustomerRemote {
	private static HashSet<String> data;
	public Hashtable<Integer, HashSet<String>> back;
	private final ReentrantReadWriteLock rwl = new ReentrantReadWriteLock();
	private final Lock r = rwl.readLock();
	private final Lock w = rwl.writeLock();

	private CustomerServer() {
		back = new Hashtable<Integer, HashSet<String>>();
		data = new HashSet<String>();
		data.add("张晓云");
		data.add("何敏");
		data.add("李大龙");
		data.add("曹爽");
		data.add("刘备");
	}

	public static void main(String[] args) {
		try {
			// 要导出的远程调用对象
			CustomerServer customerServer = new CustomerServer();
			// 导出远程调用对象
			CustomerRemote stub = (CustomerRemote) UnicastRemoteObject.exportObject(customerServer,0);
			// 在将要绑定对象的端口上创建一个注册器
			Registry registry = LocateRegistry.createRegistry(10003);
			// 将远程对象实例绑定到rmi://customerServer上
			registry.bind("customerServer", stub);
			System.err.println("customerServer ready");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public boolean insertCust(String custName) {
		w.lock();
		try {
			if (!data.contains(custName)) {
				data.add(custName);
				return true;
			} else {
				return false;
			}

		} finally {
			w.unlock();
		}
	}

	public HashSet<String> getCustomers() {
		HashSet<String> set;
		r.lock();
		try {
			set = data;
		} finally {
			r.unlock();
		}
		return set;
	}

}
