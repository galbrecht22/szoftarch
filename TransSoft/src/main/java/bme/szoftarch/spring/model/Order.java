package bme.szoftarch.spring.model;

import java.time.LocalDate;

public class Order {
	private int id;

	public int getID() {
		return id;
	}

	public void setID(int id) {
		this.id = id;
	}

	private double volume;
	private double mass;
	private OrderCoordinate from;
	private OrderCoordinate to;
	private LocalDate when;

	public Order(final double volume, final double mass, final OrderCoordinate from, final OrderCoordinate to,
			final LocalDate when, final int id) {
		this.volume = volume;
		this.mass = mass;
		this.from = from;
		this.to = to;
		this.when = when;
		this.id = id;
	}

	public double getVolume() {
		return volume;
	}

	public void setVolume(final double volume) {
		this.volume = volume;
	}

	public double getMass() {
		return mass;
	}

	public void setMass(final double mass) {
		this.mass = mass;
	}

	public Coordinate getFrom() {
		return from;
	}

	public void setFrom(final OrderCoordinate from) {
		this.from = from;
	}

	public Coordinate getTo() {
		return to;
	}

	public void setTo(final OrderCoordinate to) {
		this.to = to;
	}

	public LocalDate getWhen() {
		return when;
	}

	public void setWhen(final LocalDate when) {
		this.when = when;
	}
}