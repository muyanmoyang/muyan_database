import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.TreeMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import muyanmoyang.database.car.Car;
import muyanmoyang.database.car.CarRemote;
import muyanmoyang.database.customer.CustomerRemote;
import muyanmoyang.database.flight.Flight;
import muyanmoyang.database.flight.FlightRemote;
import muyanmoyang.database.hotel.Hotel;
import muyanmoyang.database.hotel.HotelRemote;
import muyanmoyang.database.reservation.ReserRemote;
import muyanmoyang.database.reservation.Reservation;

public class Server implements ServerRemote {
	private CarRemote carStub;
	private FlightRemote flightStub;
	private HotelRemote hotelStub;
	private CustomerRemote customerStub;
	private ReserRemote reserStub;

	private TreeMap<Integer, Boolean> treeMap = new TreeMap<Integer, Boolean>();
	private static HashSet<Integer> ids;

	public Server() {
		ids = new HashSet<Integer>();
		for (int i = 1; i <= 10; i++) {
			treeMap.put(i, false);
		}
		getRemoteObject();
	}

	public Hashtable<String, Car> getCarInfo() throws RemoteException {
		Hashtable<String, Car> data = null;
		try {
			data = carStub.getInfo();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return data;
	}

	public Hashtable<String, Flight> getFlightInfo() throws RemoteException {
		Hashtable<String,Flight> data = null;
		try{
			data = flightStub.getInfo();
		}catch(Exception e) {
			e.printStackTrace();
		}
		return data;
	}

	public Hashtable<String, Hotel> getHotelInfo() throws RemoteException {
		Hashtable<String,Hotel> data = null;
		try{
			data = hotelStub.getInfo();
			if(data == null) {
				System.out.println("hotel Œﬁ–≈œ¢");
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return data;
	}

	public TreeMap<String, Reservation> getResvInfo() throws RemoteException {
		TreeMap<String,Reservation> data = null;
		try{
			data = reserStub.getInfo();
		}catch(Exception e) {
			e.printStackTrace();
		}
		return data;
	}

	public static void main(String[] args) {
		Server s = new Server();
		try {
			ServerRemote stub = (ServerRemote) UnicastRemoteObject.exportObject(s, 0);
			Registry registry = LocateRegistry.createRegistry(9999);
			System.out.println("*******************************************"); 
			registry.bind("Server", stub);
			System.err.println("Server ready");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void getRemoteObject() {
		try {
			Registry registry = LocateRegistry.getRegistry("202.193.74.243",10002);
			carStub = (CarRemote) registry.lookup("carServer");
			registry = LocateRegistry.getRegistry("202.193.74.243", 10000);
			flightStub = (FlightRemote) registry.lookup("flightServer");
			registry = LocateRegistry.getRegistry("202.193.74.243", 10001);
			hotelStub = (HotelRemote) registry.lookup("hotelServer");
			registry = LocateRegistry.getRegistry("202.193.74.243", 10003);
			customerStub = (CustomerRemote) registry.lookup("customerServer");
			registry = LocateRegistry.getRegistry("202.193.74.243", 10004);
			reserStub = (ReserRemote) registry.lookup("reserServer");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public HashSet<String> getFlightNums() {
		HashSet<String> set = null;
		try {
			set = flightStub.getFlightNums();
			if (set == null) {
				System.out.println("null");
			} else {
				System.out.println(set.size());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return set;

	}

	public HashSet<String> getLocation() {
		HashSet<String> set = null;
		try {
			set = carStub.getLocation();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return set;
	}

	public boolean reserveItinerary(String custName, String flightNum,
			String location, boolean needCar, boolean needRoom) throws RemoteException,RuntimeException{
		int xid = getXid();
		Boolean result = true;
		ExecutorService threadPool =Executors.newSingleThreadExecutor();
		Future<Object> future = threadPool.submit(new ReserveThread(xid, custName, flightNum,
					location, needCar, needRoom));
		try {
			 
			result = (Boolean)future.get();
			
			
		} catch(Exception e) {
			result = false;
		}
		threadPool.shutdown();
		
		System.out.println(result);
		return result;
	}

	public int getXid() {
		int i = 0;
		while (ids.contains(i)) {
			i++;
		}
		ids.add(i);
		return i;
	}

	public boolean insertCar(String location, int price, int numCars)
			throws RemoteException {
		return carStub.insertCar(location, price, numCars);
	}

	public boolean insertCust(String custName) throws RemoteException {
		return customerStub.insertCust(custName);
	}

	public boolean insertHotel(String location, int price, int numRooms)
			throws RemoteException {
		return hotelStub.insertHotel(location, price, numRooms);
	}

	public boolean insertFlight(String flightNum, int price, int numSeats)
			throws RemoteException {
		
		return flightStub.insertFlight(flightNum, price, numSeats);
	}

	public HashSet<String> getCustomers() throws RemoteException {

		return customerStub.getCustomers();
	}

}
