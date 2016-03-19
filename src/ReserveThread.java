import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.concurrent.Callable;

import muyanmoyang.database.car.CarRemote;
import muyanmoyang.database.flight.FlightRemote;
import muyanmoyang.database.hotel.HotelRemote;
import muyanmoyang.database.reservation.ReserRemote;

public class ReserveThread implements Callable<Object>{
	private final int xid;

	private final String custName;
	private final String flightNum;
	private final String location;
	private final boolean needCar;
	private final boolean needRoom;
	private CarRemote carStub;
	private FlightRemote flightStub;
	private HotelRemote hotelStub;
	private ReserRemote reserStub;

	public ReserveThread(int xid,String custName, String flightNum,
			String location, boolean needCar, boolean needRoom) {
		this.xid = xid;
		this.custName = custName;
		this.flightNum = flightNum;
		this.location = location;
		this.needCar = needCar;
		this.needRoom = needRoom;
	}
	public Object call() throws Exception {
		Boolean result;
		try {
			Registry registry = LocateRegistry.getRegistry("202.193.74.243",10002);
			carStub = (CarRemote) registry.lookup("carServer");
			registry = LocateRegistry.getRegistry("202.193.74.243", 10000);
			flightStub = (FlightRemote) registry.lookup("flightServer");
			registry = LocateRegistry.getRegistry("202.193.74.243", 10001);
			hotelStub = (HotelRemote) registry.lookup("hotelServer");
			registry = LocateRegistry.getRegistry("202.193.74.243", 10004);
			reserStub = (ReserRemote) registry.lookup("reserServer");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			boolean flightFlag = true;
			boolean carFlag = true;
			boolean hotelFlag = true;
			boolean reserFlag = true;
			
			flightFlag = flightStub.fixFlight(xid, flightNum);
			if(needCar == true) {
				
				carFlag = carStub.fixCar(xid, location);
			}
			if(needRoom == true) {
				hotelFlag = hotelStub.fixRoom(xid, location);
				
			}
			reserFlag = reserStub.Reserve(xid, custName, flightNum, location, needCar, needRoom);
			
			if(flightFlag && carFlag && hotelFlag && reserFlag) {
				flightStub.commit(xid);
				carStub.commit(xid);
				hotelStub.commit(xid);
				reserStub.commit(xid);
				result = true;
			} else {
				flightStub.rollback(xid);
				carStub.rollback(xid);
				hotelStub.rollback(xid);
				reserStub.rollback(xid);
				result = false;
				System.out.println("before throw");
			}
			
		}  catch(RemoteException RemoteE) {
			RemoteE.printStackTrace();
			result = false;
		}
		return result;
	}


	public void run() throws RuntimeException{
		
	}
}
