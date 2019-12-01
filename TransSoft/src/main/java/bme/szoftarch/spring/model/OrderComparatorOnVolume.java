package bme.szoftarch.spring.model;

import java.util.Comparator;

public class OrderComparatorOnVolume implements Comparator<Order> {
	public int compare(Order o1, Order o2) {
		return (o2.getVolume() - o1.getVolume()) < 0 ? -1 : 1;
	}
}