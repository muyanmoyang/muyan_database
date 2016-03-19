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
 * �ṩ�������� insertCust(String custName)��getCustomers()
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
		data.add("������");
		data.add("����");
		data.add("�����");
		data.add("��ˬ");
		data.add("����");
	}

	public static void main(String[] args) {
		try {
			// Ҫ������Զ�̵��ö���
			CustomerServer customerServer = new CustomerServer();
			// ����Զ�̵��ö���
			CustomerRemote stub = (CustomerRemote) UnicastRemoteObject.exportObject(customerServer,0);
			// �ڽ�Ҫ�󶨶���Ķ˿��ϴ���һ��ע����
			Registry registry = LocateRegistry.createRegistry(10003);
			// ��Զ�̶���ʵ���󶨵�rmi://customerServer��
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
