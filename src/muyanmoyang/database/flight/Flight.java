package muyanmoyang.database.flight;

import java.io.Serializable;

/**
 * @author dyp
 *
 */
public class Flight implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int price;
	private int numSeats;
	private int numAvail;

	/**
	 * ���캯��
	 * @param price---�۸�
	 * @param numSeats----�ɻ�����λ��
	 * @param numAvail---�ɻ����õ���λ��
	 */
	public Flight(int price, int numSeats,int numAvail) {
		this.price = price;
		this.numSeats = numSeats;
		this.numAvail = numAvail;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public int getNumSeats() {
		return numSeats;
	}

	public int getPrice() {
		return price;
	}

	public int getNumAvail() {
		return numAvail;
	}

	/**
	 * Ԥ�����ɻ�
	 * @return boolean
	 */
	public boolean fixFlight() {
		if (numAvail > 0) {
			numAvail--;
			return true;
		} else {
			return false;
		}
	}

	/**
	 * �˶��ɻ�
	 * @return  boolean
	 */
	public boolean returnFlight() {
		if (numAvail < numSeats) {
			numAvail++;
			return true;
		} else {
			return false;
		}

	}
	
	public Flight clone() {
		Flight f = new Flight(this.price,this.numSeats,this.numAvail);
		return f;
	}
	public String toString() {
		return price + "  " + numSeats +"  "+numAvail;
	}
}
