<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<%@ page import="bme.szoftarch.spring.model.VehicleRegistry"%>
<%@ page import="bme.szoftarch.spring.model.VehiclePark"%>
<%@ page import="bme.szoftarch.spring.model.OrderCoordinate"%>
<%@ page import="bme.szoftarch.spring.model.Vehicle"%>
<%@ page import="java.util.HashMap"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.Map.Entry"%>

<!DOCTYPE html>
<html>
<body>
<br>
<p>Order Submission Successful.</p>
<br>
<%
Object vrr = request.getAttribute("vehicleregistry");
VehicleRegistry vr = (VehicleRegistry) vrr;
HashMap<String, HashMap<String, List<OrderCoordinate>>> results = new HashMap<>();
for (VehiclePark vp : vr.getVehicleParks()) {
	HashMap<String, List<OrderCoordinate>> vehicleResults = new HashMap<>();
	for (Vehicle v : vp.getVehicles()) {
		vehicleResults.put(((Integer)v.getID()).toString(), v.getPath());
		}
	results.put(((Integer)vp.getID()).toString(), vehicleResults);
}
for(Entry<String, HashMap<String, List<OrderCoordinate>>> vehicleResults : results.entrySet()){
	out.print("VehiclePark " + vehicleResults.getKey() + ":");
	out.println("<br/>");
	HashMap<String, List<OrderCoordinate>> i = vehicleResults.getValue();
	for(Entry<String, List<OrderCoordinate>> entries : i.entrySet()) {
		out.print("\tVehicle " + entries.getKey() + ":");
		out.println("<br/>");

		for(OrderCoordinate oc : entries.getValue()) {
			out.print("\t\t(" + oc.getLatitude() + ", " +
										 oc.getLongitude() + ")");
			out.println("<br/>");
		}
		out.print("\n\t\tPath length: " + vr.getVehicleParkById(Integer.parseInt(vehicleResults.getKey()))
				.getVehicleById(Integer.parseInt(entries.getKey())).getPathLength() + " m\n");
		out.println("<br/><br/>");
	}
	}
%>
<a href="/TransSoft/orderRequest_admin">Back to Order Submission</a>
</body>
</html>