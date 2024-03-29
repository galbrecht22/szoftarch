package bme.szoftarch.spring.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class DeliveryController implements DistanceCalculator {

	public void calculate(final List<VehiclePark> vehicleParks) {
//		for (VehiclePark vp : vehicleParks) {
//			System.out.println(vp.getID());
//			System.out.println(vp.getOrders());
//			for (Vehicle v : vp.getVehicles()) {
//				System.out.println(v.getID());
//			}
//		}
		System.out.println("Scheduling orders...");
		scheduleOrders(vehicleParks);
		System.out.println("Scheduling done.");
		for (VehiclePark vp : vehicleParks) {
//			System.out.println(vp.getID());
//			System.out.println(vp.getVehicles());
//			System.out.println(vp.getVehicles().get(0).getMax_mass());
//			System.out.println(vp.getVehicles().get(0).getMax_volume());
//			System.out.println(vp.getVehicles());
//			System.out.println(vp.getOrders());
//			System.out.println(vp.getOrders().get(0).getMass());
//			System.out.println(vp.getOrders().get(0).getVolume());

			for (Vehicle v : vp.getVehicles()) {
//				System.out.println(v.getID());
//				System.out.println(v.getMax_mass());
//				System.out.println(v.getMax_volume());
//				System.out.println(v.getOrders());
				if (v.getOrders().isEmpty()) {
					System.out.println("Empty vehicle");
					continue;
				}
				final List<Vehicle> options = new ArrayList<Vehicle>();
				
				System.out.println("Adding options...");
				
//				System.out.println("Adding option...");
//				options.add(lazyAlgorithmOnePhase(v, vp.getLocation())); //0
//				System.out.println("Adding done.");

				System.out.println("Adding option...");
				options.add(lazyAlgorithmTwoPhase(v, vp.getLocation())); //1
				System.out.println("Adding done.");

				// let's take some random routes, WHO KNOWS..
				
//				System.out.println("Adding option...");
//				options.add(veryLazyRandomAlgorithm(v, vp.getLocation())); //2
//				System.out.println("Adding done.");
//
//				System.out.println("Adding option...");
//				options.add(veryLazyRandomAlgorithm(v, vp.getLocation())); //3
//				System.out.println("Adding done.");
//				
//				System.out.println("Adding option...");
//				options.add(veryLazyRandomAlgorithm(v, vp.getLocation()));
//				System.out.println("Adding done.");
//				
//				System.out.println("Adding option...");
//				options.add(veryLazyRandomAlgorithm(v, vp.getLocation()));
//				System.out.println("Adding done.");
//				
//				System.out.println("Adding option...");
//				options.add(veryLazyRandomAlgorithm(v, vp.getLocation()));
//				System.out.println("Adding done.");
				
//				System.out.println("Adding option...");
//				options.add(enhanceAlgorithm2_opt(options.get(0)));
//				System.out.println("Adding done.");
//				
//				System.out.println("Adding option...");
//				options.add(enhanceAlgorithm2_opt(options.get(1)));
//				System.out.println("Adding done.");
				
//				System.out.println("Adding option...");
//				options.add(enhanceAlgorithm2_opt(options.get(2)));
//				System.out.println("Adding done.");
//				
//				System.out.println("Adding option...");
//				options.add(enhanceAlgorithm2_opt(options.get(3)));
//				System.out.println("Adding done.");
				
				System.out.println("Addings done.");

				System.out.println("Computing options...");
				
				System.out.println(v.getID());
				System.out.println(v.getOrders());
				
				//v = min(options);
				min2(options, v);
				System.out.println("Computing done.");
				
//				System.out.println(v.getID());
//				System.out.println(v.getOrders());
//				System.out.println(v.getPath());
//				System.out.println(v.getPathLength());
				
			}
		}
	}

	private Vehicle min(final List<Vehicle> options) {
		float min = Float.MAX_VALUE;
		Vehicle minimalPathVehicle = null;
		for (Vehicle v : options) {
			final float pathLen = v.getPathLength();
			if (pathLen < min) {
				min = pathLen;
				minimalPathVehicle = v;
			}
		}
		return minimalPathVehicle;
	}
	
	private void min2(final List<Vehicle> options, Vehicle vehicle) {
		float min = Float.MAX_VALUE;
		Vehicle minimalPathVehicle = null;
		for (Vehicle v : options) {
			final float pathLen = v.getPathLength();
			if (pathLen < min) {
				min = pathLen;
				minimalPathVehicle = v;
			}
		}
		vehicle.setPath(minimalPathVehicle.getPath());
	}

	// if incorrect, sorting vehicles might be a solution
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
		final List<Order> orders = vehiclePark.getOrders();
		orders.sort(new OrderComparatorOnMass());
		final List<Vehicle> vehicles = vehiclePark.getVehicles();
		for (Order o : orders) {
			fitInVehicle(o, vehicles);
		}
		return vehiclePark;
	}

	private VehiclePark assignOrdersToVehiclesByVolume(final VehiclePark vehiclePark) {
		final List<Order> orders = vehiclePark.getOrders();
		orders.sort(new OrderComparatorOnVolume());
		final List<Vehicle> vehicles = vehiclePark.getVehicles();
		for (Order o : orders) {
			fitInVehicle(o, vehicles);
		}
		return vehiclePark;
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
		final List<OrderCoordinate> pathToTake = new ArrayList<>();
		final List<Order> orders = new ArrayList<>();
		orders.addAll(vehicle.getOrders());
		final OrderCoordinate vpLoc = new OrderCoordinate(vehicleParkLocation.getLatitude(),
				vehicleParkLocation.getLongitude(), 0);
		pathToTake.add(vpLoc);
		// collect
		//while (orders.size() > 0) {
		for (int i = 0; i < orders.size() * 2; i++) {
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
		//while (orders.size() > 0) {
		for (int i = 0; i < orders.size() * 2; i++) {
			Order toAdd = null;
			float minDist = Float.MAX_VALUE;
			for (Order o : orders) {
				final float min = DistanceCalculator.distFrom(pathToTake.get(pathToTake.size() - 1), o.getTo());
				if (min < minDist) {
					minDist = min;
					toAdd = o;
				}
			}
			pathToTake.add(toAdd.getTo());
			orders.remove(toAdd);
		}
		pathToTake.add(vpLoc);
		final Vehicle v = new Vehicle(vehicle.getMax_volume(), vehicle.getMax_mass(), vehicle.getID(),
				vehicle.getMax_speed());
		v.setPath(pathToTake);
		return v;
	}

	// It can deliver while collecting (always the closest possible destination)
	private Vehicle lazyAlgorithmOnePhase(final Vehicle vehicle, final Coordinate vehicleParkLocation) {
		final List<OrderCoordinate> pathToTake = new ArrayList<>();
		final OrderCoordinate vpLoc = new OrderCoordinate(vehicleParkLocation.getLatitude(),
				vehicleParkLocation.getLongitude(), 0);
		pathToTake.add(vpLoc);
		final List<Order> orders = new ArrayList<>();
		orders.addAll(vehicle.getOrders());
		final List<OrderCoordinate> availableDestinations = getFroms(orders);
		//while (availableDestinations.size() > 0) {
		for (int i = 0; i < orders.size() * 2; i++) {
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
			availableDestinations.remove(toAdd);
		}
		pathToTake.add(vpLoc);
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

	private Vehicle veryLazyRandomAlgorithm(final Vehicle vehicle, final Coordinate vehicleParkLocation) {
		final List<OrderCoordinate> pathToTake = new ArrayList<>();
		final OrderCoordinate vpLoc = new OrderCoordinate(vehicleParkLocation.getLatitude(),
				vehicleParkLocation.getLongitude(), 0);
		pathToTake.add(vpLoc);
		final List<Order> orders = new ArrayList<>();
		orders.addAll(vehicle.getOrders());
		final List<OrderCoordinate> availableDestinations = getFroms(orders);
		while (availableDestinations.size() > 0) {
			int index = new Random().nextInt(orders.size()) % (orders.size() - 1);
			OrderCoordinate toAdd = availableDestinations.get(index);
			pathToTake.add(toAdd);
			addNewAvailableDestinationIfPossible(availableDestinations, toAdd, orders);
			availableDestinations.remove(toAdd);
		}
		pathToTake.add(vpLoc);
		final Vehicle toReturn = new Vehicle(vehicle.getMax_volume(), vehicle.getMax_mass(), vehicle.getID(),
				vehicle.getMax_speed());
		toReturn.setPath(pathToTake);
		return toReturn;
	}

	private Vehicle enhanceAlgorithm2_opt(final Vehicle vehicle) {
		List<OrderCoordinate> betterPath = new ArrayList<>(vehicle.getPath().size());
		betterPath.addAll(vehicle.getPath());
		final List<Order> orders = vehicle.getOrders();
		for (int i = 1; i < betterPath.size() - 4; i++) {
			for (int j = i+2; j < betterPath.size() - 2; j++) {
				final float current = DistanceCalculator.distFrom(betterPath.get(i), betterPath.get(i + 1))
						+ DistanceCalculator.distFrom(betterPath.get(j), betterPath.get(j + 1));
				final float swapped = DistanceCalculator.distFrom(betterPath.get(i), betterPath.get(j))
						+ DistanceCalculator.distFrom(betterPath.get(i + 1), betterPath.get(j + 1));
				if (current > swapped) {
					betterPath = checkIfSwappable(betterPath, i, j, orders);

				}
			}
		}
		final Vehicle toReturn = new Vehicle(vehicle.getMax_volume(), vehicle.getMax_mass(), vehicle.getID(),
				vehicle.getMax_speed());
		toReturn.setPath(betterPath);
		return toReturn;
	}

	private List<OrderCoordinate> checkIfSwappable(final List<OrderCoordinate> original, final int i, final int j,
			final List<Order> orders) {
		List<OrderCoordinate> copy = new ArrayList<>();
		copy.addAll(original);
		copy = swapRoute(copy, i, j);
		if (checkRouteCorrectness(copy, orders)) {
			return copy;
		} else {
			return original;
		}

	}

	private boolean checkRouteCorrectness(final List<OrderCoordinate> route, final List<Order> orders) {
		// from 0 -> 1 is always correct
		// from n-2 to n-1 is always correct (n is route.size())
		// because 0 and n-2 are the same VehiclePark.
		for (int i = 1; i < route.size() - 2; i++) {
			if (!checkValidLink(route, i, orders)) {
				return false;
			}
		}
		return true;
	}

	private boolean checkValidLink(final List<OrderCoordinate> route, final int i, final List<Order> orders) {
		final OrderCoordinate nextHop = route.get(i+1);
		final int nextHopId = nextHop.getId();
		final Order order = getOrder(orders, nextHopId);
		if (order.getFrom().equals(nextHop)) {
			return true;
		}
		for(int j = 1; j <= i; j++) {
			final OrderCoordinate coord = route.get(j);
			if(coord.getId() == nextHopId && coord.equals(order.getFrom())) {
				return true;
			}
		}
		return false;
	}

	private Order getOrder(List<Order> orders, int orderId) {
		for(Order o : orders) {
			if(o.getID() == orderId) {
				return o;
			}
		}
		return null;
	}

	private List<OrderCoordinate> swapRoute(final List<OrderCoordinate> route, final int i, final int j) {
		final List<OrderCoordinate> newPath = new ArrayList<>(route.size());
		for (int k = 0; k <= i; k++) {
			newPath.add(route.get(k));
		}
		for (int k = j; k >= i + 1; k--) {
			newPath.add(route.get(k));
		}
		for (int k = j + 1; k < route.size(); k++) {
			newPath.add(route.get(k));
		}
		return newPath;
	}

	private List<OrderCoordinate> getFroms(final List<Order> orders) {
		final List<OrderCoordinate> froms = new ArrayList<>();
		for (Order o : orders) {
			froms.add((OrderCoordinate) o.getFrom());
		}
		return froms;
	}
}