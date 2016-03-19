package muyanmoyang.database.reservation;

import java.io.Serializable;

/**
 * @author dyp
 *
 */
public class Reservation implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private String custName;
	private int resvType;
	private String detail;
	/**
	 * ���캯��
	 * @param custName---�˿�����
	 * @param resvType---Ԥ�����������
	 * @param detail
	 */
	public Reservation(String custName,int resvType,String detail) {
		this.detail = detail;
		this.custName = custName;
		this.resvType = resvType;
	}
	public String getCustName() {
		return custName;
	}
	public String getDetail() {
		return detail;
	}
	public int getResvType() {
		return resvType;
	}
	public Reservation clone() {
		Reservation r = new Reservation(this.custName,this.resvType,this.detail);
		return r;
	}
	public String toString() {
		return custName +"  "+resvType+"  "+detail;
	}
	
}
