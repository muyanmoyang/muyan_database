import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.TreeMap;

import muyanmoyang.database.car.Car;
import muyanmoyang.database.flight.Flight;
import muyanmoyang.database.hotel.Hotel;
import muyanmoyang.database.reservation.Reservation;

public interface ServerRemote extends Remote {
	public HashSet<String> getFlightNums() throws RemoteException;

	public HashSet<String> getLocation() throws RemoteException;


	public HashSet<String> getCustomers() throws RemoteException;

	public Hashtable<String, Car> getCarInfo() throws RemoteException;

	public Hashtable<String, Flight> getFlightInfo() throws RemoteException;

	public TreeMap<String, Reservation> getResvInfo() throws RemoteException;

	public Hashtable<String, Hotel> getHotelInfo() throws RemoteException;

	// 插入操作---航班、客户、汽车、酒店
	public boolean insertFlight(String flightNum, int price, int numSeats)
											throws RemoteException;

	public boolean insertCust(String custName) throws RemoteException;

	public boolean insertCar(String location, int price, int numCars)
											throws RemoteException;

	public boolean insertHotel(String location, int price, int numRooms)
											throws RemoteException;
	// 进行预订操作
	public boolean reserveItinerary(String custName, String flightNum,
											String location, boolean needCar,
											boolean needRoom)
											throws RemoteException,
											RuntimeException;
}
