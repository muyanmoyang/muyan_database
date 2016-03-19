import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;


public class insert extends JFrame implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JLabel jlFlight = new JLabel("飞机");
	JLabel jlFlightNum = new JLabel("航班号");
	JLabel jlFPrice = new JLabel("机票票价");
	JLabel jlFNumSeats = new JLabel("座位数");

	JLabel jlHotel = new JLabel("酒店");
	JLabel jlHLocation = new JLabel("酒店地址");
	JLabel jlHPrice = new JLabel("酒店价格");
	JLabel jlHNumRooms = new JLabel("房间数");

	JLabel jlCar = new JLabel("出租车");
	JLabel jlClocation = new JLabel("所属地址");
	JLabel jlCPrice = new JLabel("价格");
	JLabel jlCNumCars = new JLabel("数量");

	JLabel jlCust = new JLabel("消费者");
	JLabel jlCustName = new JLabel("消费者姓名");

	JTextField jtFlightNum = new JTextField();
	JTextField jtFPrice = new JTextField();
	JTextField jtFNum = new JTextField();

	JTextField jtHLocation = new JTextField();
	JTextField jtHPrice = new JTextField();
	JTextField jtHNumRooms = new JTextField();

	JTextField jtCLocation = new JTextField();
	JTextField jtCPrice = new JTextField();
	JTextField jtCNumCars = new JTextField();

	JTextField jtCustName = new JTextField();

	JButton jbFlight = new JButton("添加航班");
	JButton jbHotel = new JButton("添加酒店");
	JButton jbCar = new JButton("添加出租车");
	JButton jbCust = new JButton("添加预订者");
	ServerRemote stub;
	public insert() {
		init();
		addEventHandler();
		try {
			Registry registry = LocateRegistry.getRegistry("202.193.74.243",9999);
			stub = (ServerRemote) registry.lookup("Server");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void addEventHandler() {
		jbFlight.addActionListener(this);
		jbCar.addActionListener(this);
		jbHotel.addActionListener(this);
		jbCust.addActionListener(this);
	}

	public void init() {
		this.add(jlFlight);
		this.add(jlFlightNum);
		this.add(jlFPrice);
		this.add(jlFNumSeats);
		this.add(jlHotel);
		this.add(jlHLocation);
		this.add(jlHPrice);
		this.add(jlHNumRooms);
		this.add(jlCar);
		this.add(jlClocation);
		this.add(jlCPrice);
		this.add(jlCNumCars);
		this.add(jlCust);
		this.add(jlCustName);
		this.add(jtFlightNum);
		this.add(jtFPrice);
		this.add(jtFNum);
		this.add(jtHLocation);
		this.add(jtHPrice);
		this.add(jtHNumRooms);
		this.add(jtCLocation);
		this.add(jtCPrice);
		this.add(jtCNumCars);
		this.add(jtCustName);
		this.add(jbFlight);
		this.add(jbHotel);
		this.add(jbCar);
		this.add(jbCust);

		this.setLayout(null);

		jlFlight.setBounds(10, 30, 50, 20);
		jlFlightNum.setBounds(70, 20, 100, 20);
		jtFlightNum.setBounds(70, 40, 100, 20);
		jlFPrice.setBounds(180, 20, 100, 20);
		jtFPrice.setBounds(180, 40, 100, 20);
		jlFNumSeats.setBounds(290, 20, 100, 20);
		jtFNum.setBounds(290, 40, 100, 20);
		jbFlight.setBounds(400, 40, 140, 20);

		jlHotel.setBounds(10, 80, 50, 20);
		jlHLocation.setBounds(70, 70, 100, 20);
		jtHLocation.setBounds(70, 90, 100, 20);
		jlHPrice.setBounds(180, 70, 100, 20);
		jtHPrice.setBounds(180, 90, 100, 20);
		jlHNumRooms.setBounds(290, 70, 100, 20);
		jtHNumRooms.setBounds(290, 90, 100, 20);
		jbHotel.setBounds(400, 90, 140, 20);

		jlCar.setBounds(10, 130, 50, 20);
		jlClocation.setBounds(70, 120, 100, 20);
		jtCLocation.setBounds(70, 140, 100, 20);
		jlCPrice.setBounds(180, 120, 100, 20);
		jtCPrice.setBounds(180, 140, 100, 20);
		jlCNumCars.setBounds(290, 120, 100, 20);
		jtCNumCars.setBounds(290, 140, 100, 20);
		jbCar.setBounds(400, 140, 140, 20);

		jlCust.setBounds(10, 180, 50, 20);
		jlCustName.setBounds(70, 170, 100, 20);
		jtCustName.setBounds(70, 190, 100, 20);
		jbCust.setBounds(400, 190, 140, 20);

		this.setVisible(true);
		this.setTitle("插入旅行信息");
		this.setBounds(400, 280, 550, 400);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

	}

	public void actionPerformed(ActionEvent event) {
		if (event.getSource() == jbFlight) {
			if (jtFlightNum.getText().equals("")
					|| jtFPrice.getText().equals("")
					|| jtFNum.getText().equals("")) {
				JOptionPane.showMessageDialog(null, "字段不能为空", "空值",
						JOptionPane.ERROR_MESSAGE);
			} else {
				try {
					boolean result = stub.insertFlight(jtFlightNum.getText(),
							Integer.parseInt(jtFPrice.getText()), Integer
									.parseInt(jtFNum.getText()));
					if (result == true) {
						JOptionPane.showMessageDialog(null,
								"恭喜你，添加航班成功", "success",
								JOptionPane.INFORMATION_MESSAGE);
					} else {
						JOptionPane.showMessageDialog(null, "添加航班记录失败","failed",
								JOptionPane.INFORMATION_MESSAGE);
					}

				} catch (NumberFormatException e) {
					JOptionPane.showMessageDialog(null, "非法输入", "错误",
							JOptionPane.ERROR_MESSAGE);
					e.printStackTrace();
				} catch(RemoteException e) {
					e.printStackTrace();
				}
			}
		} else if(event.getSource() == jbCar){
			if(this.jtCLocation.getText().equals("")||
					this.jtCPrice.getText().equals("")||
					this.jtCNumCars.getText().equals("")) {
				JOptionPane.showMessageDialog(null, "字段不能为空", "空值",
						JOptionPane.ERROR_MESSAGE);
			} else {
				try{
					boolean result = stub.insertCar(jtCLocation.getText(), 
							Integer.parseInt(jtCPrice.getText()),
							Integer.parseInt(jtCNumCars.getText()));
					if (result == true) {
						JOptionPane.showMessageDialog(null,
								"恭喜你，添加出租车成功", "success",
								JOptionPane.INFORMATION_MESSAGE);
					} else {
						JOptionPane.showMessageDialog(null, "添加出租车记录失败","failed",
								JOptionPane.INFORMATION_MESSAGE);
					}
				} catch (NumberFormatException e) {
					JOptionPane.showMessageDialog(null, "非法输入", "错误",
							JOptionPane.ERROR_MESSAGE);
					e.printStackTrace();
				} catch(RemoteException e) {
					e.printStackTrace();
				}
			}
		} else if(event.getSource() == jbHotel) {

			if(this.jtHLocation.getText().equals("")||
					this.jtHPrice.getText().equals("")||
					this.jtHNumRooms.getText().equals("")) {
				JOptionPane.showMessageDialog(null, "字段不能为空", "空值",
						JOptionPane.ERROR_MESSAGE);
			} else {
				try{
					boolean result = stub.insertHotel(jtHLocation.getText(), 
							Integer.parseInt(jtHPrice.getText()),
							Integer.parseInt(jtHNumRooms.getText()));
					if (result == true) {
						JOptionPane.showMessageDialog(null,
								"恭喜你，添加酒店成功", "success",
								JOptionPane.INFORMATION_MESSAGE);
					} else {
						JOptionPane.showMessageDialog(null, "添加酒店记录失败","failed",
								JOptionPane.INFORMATION_MESSAGE);
					}
				} catch (NumberFormatException e) {
					JOptionPane.showMessageDialog(null, "非法输入", "错误",
							JOptionPane.ERROR_MESSAGE);
					e.printStackTrace();
				} catch(RemoteException e) {
					e.printStackTrace();
				}
			}
		} else if(event.getSource() == jbCust) {


			if(this.jtCustName.getText().equals("")) {
				JOptionPane.showMessageDialog(null, "字段不能为空", "空值",
						JOptionPane.ERROR_MESSAGE);
			} else {
				try{
					boolean result = stub.insertCust(jtCustName.getText());
					if (result == true) {
						JOptionPane.showMessageDialog(null,
								"恭喜你，添加顾客成功", "success",
								JOptionPane.INFORMATION_MESSAGE);
					} else {
						JOptionPane.showMessageDialog(null, "添加顾客记录失败","failed",
								JOptionPane.INFORMATION_MESSAGE);
					}
				} catch (NumberFormatException e) {
					JOptionPane.showMessageDialog(null, "非法输入", "错误",
							JOptionPane.ERROR_MESSAGE);
					e.printStackTrace();
				} catch(RemoteException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public static void main(String args[]) {
		new insert();
	}
}