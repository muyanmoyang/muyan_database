package muyanmoyang.database.car;

import java.io.Serializable;

/**
 * @author dyp
 * �������car�࣬�������� price��numCar��numAvail
 * �к�����
 * 1.fixCar()-----��������
 * 2.returnCar()----��������
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
	 * ���캯��
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
	 * ��������
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
	 * ��������---returnCar()
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
	 * ��Object()���е�clone()�����ĸ�д
	 */
	protected Car clone() {
		Car car = new Car(this.price, this.numCars, this.numAvail);
		return car;
	}

	public String toString() {
		return price + "  " + numCars + "  " + numAvail;
	}

}
