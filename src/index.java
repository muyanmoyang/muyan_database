import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;

public class index extends JFrame implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JButton jbl = new JButton("Ԥ����ѯ");
	JButton jbi = new JButton("������Ϣ");
	JButton jblogin = new JButton("����Ԥ��");

	public index() {
		this.add(jbl);
		this.add(jbi);
		this.add(jblogin);
		this.setLayout(null);

		jbl.setBounds(10, 30, 150, 30);
		jbi.setBounds(200, 30, 150, 30);
		jblogin.setBounds(400, 30, 150, 30); 

		jbl.addActionListener(this);
		jbi.addActionListener(this);
		jblogin.addActionListener(this);

		this.setTitle("�ֲ�ʽ����Ԥ��ϵͳ");
		this.setVisible(true);
		this.setBounds(400, 200, 600, 200);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == jbl) {
			new ListInfo();
		} else if (e.getSource() == jbi) {
			new insert();
		} else if (e.getSource() == jblogin) {
			new login();
		}

	}

	public static void main(String args[]) {
		new index();
	}
}