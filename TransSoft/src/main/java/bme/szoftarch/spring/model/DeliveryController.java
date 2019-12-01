package bme.szoftarch.spring.model;

import java.util.ArrayList;
import java.util.List;

public class DeliveryController implements DistanceCalculator {

	public void calculate(final List<VehiclePark> vehicleParks) {
		scheduleOrders(vehicleParks);
		for(VehiclePark vp : vehicleParks) {
			for(Vehicle v : vp.getVehicles()) {
				if(v.getOrders().isEmpty()) {
					continue;
				}
				final List<Vehicle> options = new ArrayList<Vehicle>();
				options.add(lazyAlgorithmOnePhase(v, vp.getLocation()));
				options.add(lazyAlgorithmTwoPhase(v, vp.getLocation()));
				v = min(options);
			}
		}
	}

	private Vehicle min(final List<Vehicle> options) {
		float min = Float.MAX_VALUE;
		Vehicle minimalPathVehicle = null;
		for(Vehicle v : options) {
			final float pathLen = v.getPathLength();
			if(pathLen < min) {
				min = pathLen;
				minimalPathVehicle = v;
			}
		}
		return minimalPathVehicle;
	}

	private void scheduleOrders(List<VehiclePark> VehicleParks) {
		for (VehiclePark vp : VehicleParks) {
			// Pair Order to Vehicle by Mass
			final VehiclePark vpByMass = assignOrdersToVehiclesByMass(vp);
			// Pair Order to Vehicle by Volume
			final VehiclePark vpByVolume = assignOrdersToVehiclesByVolume(vp);
			// and take the one with fewer Vehicles
			if (vpByMass.getUnusedVehicles() < vpByVolume.getUnusedVehicles()) {
				vp = vpByMass;
			} else {
				vp = vpByVolume;
			}
		}
	}

	private VehiclePark assignOrdersToVehiclesByMass(final VehiclePark vehiclePark) {
		final VehiclePark vp = vehiclePark.copy();
		final List<Order> orders = vp.getOrders();
		orders.sort(new OrderComparatorOnMass());
		final List<Vehicle> vehicles = vp.getVehicles();
		for (Order o : orders) {
			fitInVehicle(o, vehicles);
		}
		return vp;
	}

	private VehiclePark assignOrdersToVehiclesByVolume(final VehiclePark vehiclePark) {
		final VehiclePark vp = vehiclePark.copy();
		final List<Order> orders = vp.getOrders();
		orders.sort(new OrderComparatorOnVolume());
		final List<Vehicle> vehicles = vp.getVehicles();
		for (Order o : orders) {
			fitInVehicle(o, vehicles);
		}
		return vp;
	}

	private void fitInVehicle(final Order order, final List<Vehicle> vehicles) {
		for (Vehicle v : vehicles) {
			if (v.getRemainingMass() >= order.getMass() && v.getRemainingVolume() >= order.getVolume()) {
				v.addOrder(order);
				return;
			}
		}
	}

	// First collect all, then deliver
	private Vehicle lazyAlgorithmTwoPhase(final Vehicle vehicle, final Coordinate vehicleParkLocation) {
		final List<Coordinate> pathToTake = new ArrayList<Coordinate>();
		final List<Order> orders = new ArrayList<>();
		orders.addAll(vehicle.getOrders());
		pathToTake.add(vehicleParkLocation);
		// collect
		while (orders.size() > 0) {
			Order toAdd = null;
			float minDist = Float.MAX_VALUE;
			for (Order o : orders) {
				final float min = DistanceCalculator.distFrom(pathToTake.get(pathToTake.size() - 1), o.getFrom());
				if (min < minDist) {
					minDist = min;
					toAdd = o;
				}
			}
			pathToTake.add(toAdd.getFrom());
			orders.remove(toAdd);
		}

		// deliver
		orders.addAll(vehicle.getOrders());
		while (orders.size() > 0) {
			Order toAdd = null;
			float minDist = Float.MAX_VALUE;
			for (Order o : orders) {
				final float min = DistanceCalculator.distFrom(pathToTake.get(pathToTake.size() - 1), o.getTo());
				if (min < minDist) {
					minDist = min;
					toAdd = o;
				}
			}
			pathToTake.add(toAdd.getFrom());
			orders.remove(toAdd);
		}
		pathToTake.add(vehicleParkLocation);
		final Vehicle v = new Vehicle(vehicle.getMax_volume(), vehicle.getMax_mass(), vehicle.getID(),
				vehicle.getMax_speed());
		v.setPath(pathToTake);
		return v;
	}

	// It can deliver while collecting (always the closest possible destination)
	private Vehicle lazyAlgorithmOnePhase(final Vehicle vehicle, final Coordinate vehicleParkLocation) {
		final List<Coordinate> pathToTake = new ArrayList<Coordinate>();
		pathToTake.add(vehicleParkLocation);
		final List<Order> orders = new ArrayList<>();
		orders.addAll(vehicle.getOrders());
		final List<OrderCoordinate> availableDestinations = getFroms(orders);
		while (availableDestinations.size() > 0) {
			OrderCoordinate toAdd = null;
			float minDist = Float.MAX_VALUE;
			for (OrderCoordinate oc : availableDestinations) {
				final float min = DistanceCalculator.distFrom(pathToTake.get(pathToTake.size() - 1), oc);
				if (min < minDist) {
					minDist = min;
					toAdd = oc;
				}
			}
			pathToTake.add(toAdd);
			addNewAvailableDestinationIfPossible(availableDestinations, toAdd, orders);
		}
		pathToTake.add(vehicleParkLocation);
		final Vehicle v = new Vehicle(vehicle.getMax_volume(), vehicle.getMax_mass(), vehicle.getID(),
				vehicle.getMax_speed());
		v.setPath(pathToTake);
		return v;
	}

	private void addNewAvailableDestinationIfPossible(final List<OrderCoordinate> availableDestinations,
			final OrderCoordinate toAdd, final List<Order> orders) {
		for (Order o : orders) {
			if (o.getID() == toAdd.getId() && o.getFrom().equals(toAdd)) {
				availableDestinations.add((OrderCoordinate) o.getTo());
				return;
			}
		}
	}

//	private Vehicle veryLazyRandomAlgorithm(final List<Order> orders) {
//		final List<Coordinate> pathToTake = new ArrayList<Coordinate>();
//		// TODO
//		return null;
//	}

	private List<OrderCoordinate> getFroms(final List<Order> orders) {
		final List<OrderCoordinate> froms = new ArrayList<>();
		for (Order o : orders) {
			froms.add((OrderCoordinate) o.getFrom());
		}
		return froms;
	}
}