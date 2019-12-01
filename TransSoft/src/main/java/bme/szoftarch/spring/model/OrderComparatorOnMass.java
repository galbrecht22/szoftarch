package bme.szoftarch.spring.model;

import java.util.Comparator;

public class OrderComparatorOnMass implements Comparator<Order> {
	public int compare(Order o1, Order o2) {
		return (o2.getMass() - o1.getMass()) < 0 ? -1 : 1;
	}
}