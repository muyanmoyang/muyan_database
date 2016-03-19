import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.HashSet;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

public class login extends JFrame implements ActionListener//,ItemListener
{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JComboBox jcb=new JComboBox();
    JComboBox jcbaddress=new JComboBox();
    JComboBox jcbtaxi=new JComboBox();
    JComboBox jcbhotel=new JComboBox();
    JComboBox jcbName = new JComboBox();
    JLabel jlhang=new JLabel("�����");
    JLabel jldi=new JLabel("��ַ");
    JLabel jlchuzu=new JLabel("�Ƿ�Ҫ���⳵");
    JLabel jlbinguan=new JLabel("�Ƿ�ס����");
    JLabel jlName = new JLabel("����");
    JLabel jl=new JLabel();
    JButton jb=new JButton("�ύ");
    Registry registry;
    ServerRemote stub;
	
	public login()
	{
		HashSet<String> set = null;
		try 
		{
			registry = LocateRegistry.getRegistry("202.193.74.243",9999);
			stub = (ServerRemote)registry.lookup("Server");
			set = stub.getFlightNums();
						
		} 
		catch(Exception e) 
		{
			e.printStackTrace();
		}
		if(set != null) 
		{
//			String[] str = set.toArray(new String[0]);
			String[]str=new String[set.size()];
			set.toArray(str);
			jcb = new JComboBox(str);
		}
		try{
			set = stub.getLocation();
		} catch(Exception e) {
			e.printStackTrace();
		}
		if(set != null) {
//			String[] str = set.toArray(new String[0]);
			String[]str=new String[set.size()];
			set.toArray(str);
			jcbaddress = new JComboBox(str);
		}
		try {
			set = stub.getCustomers();
//			String[] str = set.toArray(new String[0]);
			String[]str=new String[set.size()];
			set.toArray(str);
			jcbName = new JComboBox(str);
		} catch(Exception e) {
			e.printStackTrace();
		}
		jcbtaxi.addItem("��");
		jcbtaxi.addItem("��");
		jcbhotel.addItem("��");
		jcbhotel.addItem("��");
		
		
		this.add(jcb);
		this.add(jcbaddress);
		this.add(jcbtaxi);
		this.add(jcbhotel);
		this.add(jlhang);
		this.add(jldi);
		this.add(jlchuzu);
		this.add(jlbinguan);
		this.add(jb);
		this.add(jl);
		this.add(jlName);
		this.add(jcbName);
		
		this.setLayout(null);
		jlhang.setBounds(10,10,100,20);
		jcb.setBounds(10,30,100,20);
		
		jldi.setBounds(120,10,100,20);
		jcbaddress.setBounds(120,30,100,20);
		
		jlchuzu.setBounds(230,10,100,20);
		jcbtaxi.setBounds(230,30,100,20);
		
		jlbinguan.setBounds(340,10,100,20);
		jcbhotel.setBounds(340,30,100,20);
		
		jlName.setBounds(10, 60, 100, 20);
		jcbName.setBounds(10, 80, 100, 20);
		
		jb.setBounds(225,80,60,20);
		jl.setBounds(10,90,480,340);
		
		
		jb.addActionListener(this);
		this.setTitle("����");
		this.setVisible(true);
		this.setBounds(400,240,500,400);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}
	
	public void actionPerformed(ActionEvent event)
	{
		if(event.getSource()==jb)
		{
			Boolean b = null;
			String custName = jcbName.getSelectedItem().toString();
			String flightNum = jcb.getSelectedItem().toString();
			String location = jcbaddress.getSelectedItem().toString();
			boolean needCar = jcbtaxi.getSelectedItem().equals("��")?true:false;
			boolean needRoom = jcbhotel.getSelectedItem().equals("��")?true:false;
			
			try {
				b =stub.reserveItinerary(custName,flightNum,location,needCar,needRoom);
			} catch(RuntimeException e) {
				jl.setText("false");
				e.printStackTrace();
			} catch(RemoteException e) {
				
			}
			if(b != null) {
				if(b == true) {
					JOptionPane.showMessageDialog(null,
							"Ԥ���ɹ������Բ�ѯ", "success",
							JOptionPane.INFORMATION_MESSAGE);
				}else {
					JOptionPane.showMessageDialog(null, "Ԥ��ʧ�ܣ����ѯ������Ԥ��","failed",
							JOptionPane.INFORMATION_MESSAGE);
				}
			}
		}
	}
	
/*	public void itemStateChanged(ItemEvent e)
	{
		
	}
	*/
	public static void main(String args[])
	{
		new login();
	}
}