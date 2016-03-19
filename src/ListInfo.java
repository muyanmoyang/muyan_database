import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.TreeMap;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import muyanmoyang.database.car.Car;
import muyanmoyang.database.flight.Flight;
import muyanmoyang.database.hotel.Hotel;
import muyanmoyang.database.reservation.Reservation;

public class ListInfo extends JFrame implements ActionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JButton flightJB;
	private JButton hotelJB;
	private JButton carJB;
	private JButton customerJB;
	private JButton resvJB;
	private JScrollPane jsp;
	private JTable info;
	ServerRemote stub;

	public ListInfo(){
		try {
			Registry registry = LocateRegistry.getRegistry("202.193.74.243",
					9999);
			stub = (ServerRemote) registry.lookup("Server");

		} catch (Exception e) {
			e.printStackTrace();
		}

		init();
		flightJB.addActionListener(this);
		hotelJB.addActionListener(this);
		carJB.addActionListener(this);
		customerJB.addActionListener(this);
		resvJB.addActionListener(this);
	}

	public void init() {

		flightJB = new JButton("航班");
		hotelJB = new JButton("酒店");
		carJB = new JButton("出租车");
		customerJB = new JButton("顾客");
		resvJB = new JButton("预订情况");
		jsp = new JScrollPane();
		this.setLayout(null);
		this.add(flightJB);
		this.add(hotelJB);
		this.add(carJB);
		this.add(customerJB);
		this.add(resvJB);
		flightJB.setBounds(10, 10, 100, 20);
		hotelJB.setBounds(120, 10, 100, 20);
		carJB.setBounds(230, 10, 100, 20);
		customerJB.setBounds(340, 10, 100, 20);
		resvJB.setBounds(450, 10, 100, 20);

		this.setBounds(400, 320, 600, 600);
		this.setVisible(true);
		this.setTitle("查询");
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

	}

	public void actionPerformed(ActionEvent ae) {
		if (ae.getSource() == flightJB) {
			String[] colNames = { "航班", "票价", "总座位", "剩余座位" };
			String[][] rowData = null;
			try {
				Hashtable<String, Flight> datas = stub.getFlightInfo();
				rowData = new String[datas.size()][4];
				int i = 0;
				for (Iterator<String> it = datas.keySet().iterator(); it
						.hasNext();) {
					String str = it.next();

					rowData[i][0] = str;
					rowData[i][1] = String.valueOf(datas.get(str).getPrice());
					rowData[i][2] = String
							.valueOf(datas.get(str).getNumSeats());
					rowData[i][3] = String
							.valueOf(datas.get(str).getNumAvail());
					i++;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			this.remove(jsp);
			info = new JTable(rowData, colNames);
			jsp = new JScrollPane(info);
			jsp.setBounds(10, 40, 570, 500);
			this.add(jsp);
		} else if (ae.getSource() == carJB) {
			String[] colNames = { "城市", "价格", "总车辆", "剩余车辆" };
			String[][] rowData = null;
			try {
				Hashtable<String, Car> datas = stub.getCarInfo();
				rowData = new String[datas.size()][4];
				int i = 0;
				for (Iterator<String> it = datas.keySet().iterator(); it
						.hasNext();) {
					String str = it.next();
					rowData[i][0] = str;
					rowData[i][1] = String.valueOf(datas.get(str).getPrice());
					rowData[i][2] = String.valueOf(datas.get(str).getNumCars());
					rowData[i][3] = String
							.valueOf(datas.get(str).getNumAvail());
					i++;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			this.remove(jsp);
			info = new JTable(rowData, colNames);
			jsp = new JScrollPane(info);
			jsp.setBounds(10, 40, 570, 500);
			this.add(jsp);
		} else if (ae.getSource() == hotelJB) {
			String[] colNames = { "城市", "价格", "总房间", "剩余房间" };
			String[][] rowData = null;
			try {
				Hashtable<String, Hotel> datas = stub.getHotelInfo();
				datas.size();
				rowData = new String[datas.size()][4];
				int i = 0;
				for (Iterator<String> it = datas.keySet().iterator(); it
						.hasNext();) {
					String str = it.next();
					rowData[i][0] = str;
					rowData[i][1] = String.valueOf(datas.get(str).getPrice());
					rowData[i][2] = String
							.valueOf(datas.get(str).getNumRooms());
					rowData[i][3] = String
							.valueOf(datas.get(str).getNumAvail());
					i++;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			this.remove(jsp);
			info = new JTable(rowData, colNames);
			jsp = new JScrollPane(info);
			jsp.setBounds(10, 40, 570, 500);
			this.add(jsp);
		} else if (ae.getSource() == customerJB) {
			String[] colNames = {"姓名"};
			String[][] rowData = null;
			try{
				HashSet<String> datas = stub.getCustomers();
				rowData = new String[datas.size()][1];
				int i=0;
				for(Iterator<String> it=datas.iterator();it.hasNext();) {
					rowData[i][0] = it.next();
					i++;
				}
			}catch(Exception e) {
				e.printStackTrace();
			}
			this.remove(jsp);
			info = new JTable(rowData, colNames);
			jsp = new JScrollPane(info);
			jsp.setBounds(10, 40, 570, 500);
			this.add(jsp);
		} else if(ae.getSource() == resvJB) {
			String[] colNames = {"姓名","类型","资料"};
			String[][] rowData = null;
			try{
				TreeMap<String ,Reservation> datas = stub.getResvInfo();
				rowData = new String[datas.size()][3];
				int i=0;
				for(Iterator<String> it=datas.keySet().iterator();it.hasNext();) {
					String str = it.next();
					rowData[i][0] = datas.get(str).getCustName();
					if(datas.get(str).getResvType() == 1) {
						rowData[i][1] = "航班";
					} else if(datas.get(str).getResvType() ==2) {
						rowData[i][1] = "旅店";
					} else if(datas.get(str).getResvType() ==3) {
						rowData[i][1] = "出租车";
					}
					rowData[i][2] = datas.get(str).getDetail();
					i++;
				}
				
			}catch (Exception e) {
				e.printStackTrace();
			}
			this.remove(jsp);
			info = new JTable(rowData, colNames);
			jsp = new JScrollPane(info);
			jsp.setBounds(10, 40, 570, 500);
			this.add(jsp);
		}
	}
	public static void main(String[] args) {
		new ListInfo();
	}
}
