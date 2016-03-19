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
	 * 构造函数
	 * @param price---价格
	 * @param numSeats----飞机的座位数
	 * @param numAvail---飞机可用的座位数
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
	 * 预订订飞机
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
	 * 退订飞机
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
