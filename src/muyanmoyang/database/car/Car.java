package muyanmoyang.database.car;

import java.io.Serializable;

/**
 * @author dyp
 * 这个类是car类，包括参数 price，numCar，numAvail
 * 有函数：
 * 1.fixCar()-----订车函数
 * 2.returnCar()----还车函数
 * 
 */
public class Car implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int price;
	private int numCars;
	private int numAvail;

	/**	
	 * 构造函数
	 * @param price
	 * @param numCars
	 * @param numAvail
	 */
	public Car(int price, int numCars, int numAvail) {
		this.price = price;
		this.numCars = numCars;
		this.numAvail = numAvail;
	}

	public int getNumCars() {
		return numCars;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public int getPrice() {
		return price;
	}

	public int getNumAvail() {
		return numAvail;
	}

	/**
	 * 订车函数
	 * @return
	 */
	public boolean fixCar() {
		if (numAvail > 0) {
			numAvail--;
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 还车函数---returnCar()
	 * @return
	 */
	public boolean returnCar() {
		if (numAvail < numCars) {
			numAvail++;
			return true;
		} else {
			return false;
		}
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#clone()
	 * 对Object()类中的clone()函数的覆写
	 */
	protected Car clone() {
		Car car = new Car(this.price, this.numCars, this.numAvail);
		return car;
	}

	public String toString() {
		return price + "  " + numCars + "  " + numAvail;
	}

}
