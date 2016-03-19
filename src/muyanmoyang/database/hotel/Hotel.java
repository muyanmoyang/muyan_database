package muyanmoyang.database.hotel;

import java.io.Serializable;

/**
 * @author dyp
 *
 */
public class Hotel implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private int price;
	private int numRooms;
	private int numAvail;

	/**
	 * ���캯��
	 * @param price---�۸�
	 * @param numRooms----������
	 * @param numAvail---��Ԥ��������
	 */
	public Hotel(int price, int numRooms,int numAvail) {
		this.price = price;
		this.numRooms = numRooms;
		this.numAvail = numAvail;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public int getPrice() {
		return price;
	}

	public int getNumRooms() {
		return numRooms;
	}

	public int getNumAvail() {
		return numAvail;
	}

	/**
	 * Ԥ��������
	 * @return  boolean
	 */
	public boolean fixRoom() {
		if (numAvail > 0) {
			numAvail--;
			return true;
		} else {
			return false;
		}
	}

	/**
	 * �˶�����
	 * @return--boolean
	 */
	public boolean returnRoom() {
		if (numAvail < numRooms) {
			numAvail++;
			return true;
		} else {
			return false;
		}

	}
	public Hotel clone() {
		Hotel h = new Hotel(this.price,this.numRooms,this.numAvail);
		return h;
	}
	public String toString() {
		return price + "  "+numRooms+"  "+numAvail;
	}
}
