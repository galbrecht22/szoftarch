package bme.szoftarch.spring.controller;

import java.text.DateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.Locale;
import java.util.Map;


import javax.servlet.ServletContextEvent;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.servlet.ModelAndView;

import bme.szoftarch.spring.model.Coordinate;
import bme.szoftarch.spring.model.Dispatcher;
import bme.szoftarch.spring.model.OrderCoordinate;
import bme.szoftarch.spring.model.Vehicle;
import bme.szoftarch.spring.model.VehiclePark;
import bme.szoftarch.spring.model.VehicleRegistry;

@Controller
public class HomeController extends ContextLoaderListener {
	
	private Dispatcher dispatcher = Dispatcher.getInstance();
    
    @Override
    public void contextInitialized(ServletContextEvent arg0) {
        super.contextInitialized(arg0);
        dispatcher.loadModel();
    }

    @Override
    public void contextDestroyed(ServletContextEvent arg0) {
        super.contextDestroyed(arg0);
        
    }
	
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		System.out.println("Home Page Requested, locale = " + locale);
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);

		String formattedDate = dateFormat.format(date);

		model.addAttribute("serverTime", formattedDate);

		return "orderRequest";
	}
	
	@RequestMapping(value = "/orderRequest_admin", method = RequestMethod.GET)  
    public String home_admin(Locale locale, Model model) {
		System.out.println("Home Page Requested, locale = " + locale);
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);

		String formattedDate = dateFormat.format(date);

		model.addAttribute("serverTime", formattedDate);

		return "orderRequest_admin";
	}
	
	@RequestMapping(value = "/orderRequest_admin", method = RequestMethod.POST)  
    public ModelAndView postLogin(HttpServletRequest request, HttpServletResponse response) {
		  String userName=request.getParameter("userName");  
	      String password=request.getParameter("password");
	      String message;
	      if(userName != null && 
	    		  !userName.equals("") 
	    		  && userName.equals("admin") && 
	    		  password != null && 
	    		  !password.equals("") && 
	    		  password.equals("admin")){
	    	  message = "Welcome " +userName + ".";
		      return new ModelAndView("orderRequest_admin", 
		    		  "message", message);  
	 
	      }else{
	    	  message = "Wrong username or password.";
	    	  return new ModelAndView("errorPage", 
	    			  "message", message);
	      }
	   }
	
	@RequestMapping(value = "/orderSubmitted", method = RequestMethod.POST)
	public ModelAndView orderSubmitted(@RequestBody MultiValueMap<String, String> formData, Model model) {
		System.out.println("Order Submission Requested");
		//TODO: Form validation
		boolean missingData = false;
		String message = "";
		for(String key : formData.keySet()) {
			if(formData.get(key).get(0).isEmpty()) {
				missingData = true;
				String fieldMessage = "No " + key + " specified for order.";
				System.out.println(fieldMessage);
				message += fieldMessage;
			}
		}
		if(missingData) {
			return new ModelAndView("errorPage_orderRequest", 
		    		  "message", message);  
		}
		
		for(Entry<String, List<String>> param : formData.entrySet()){
			System.out.println(param.getKey() + ", " + param.getValue());
			}
	
		HashMap<String, String> orderParams = new HashMap<>();
		orderParams.put("mass", formData.get("mass").get(0));
		orderParams.put("volume", formData.get("volume").get(0));
		orderParams.put("from_lat", formData.get("from_lat").get(0));
		orderParams.put("from_lon", formData.get("from_lon").get(0));
		orderParams.put("to_lat", formData.get("to_lat").get(0));
		orderParams.put("to_lon", formData.get("to_lon").get(0));
		orderParams.put("date", formData.get("date").get(0));
		
		Map<String, String> result = dispatcher.onOrderReceived(orderParams);
		model.addAllAttributes(result);
		//model.addAttribute("userName", user.getUserName());
		return new ModelAndView("orderSubmitted", 
	    		  "message", message);
		}
	
	@RequestMapping(value = "/orderSubmitted_admin", method = RequestMethod.POST)
	public ModelAndView orderSubmitted_admin(@RequestBody MultiValueMap<String, String> formData, Model model) {
		System.out.println("Order Submission Requested");
		//TODO: Form validation
		boolean missingData = false;
		String message = "";
		for(String key : formData.keySet()) {
			if(formData.get(key).get(0).isEmpty()) {
				missingData = true;
				String fieldMessage = "No " + key + " specified for order.";
				System.out.println(fieldMessage);
				message += fieldMessage;
			}
		}
		if(missingData) {
			return new ModelAndView("errorPage_orderRequest_admin", 
		    		  "message", message);  
		}
		
		for(Entry<String, List<String>> param : formData.entrySet()){
			System.out.println(param.getKey() + ", " + param.getValue());
			}
	
		HashMap<String, String> orderParams = new HashMap<>();
		orderParams.put("mass", formData.get("mass").get(0));
		orderParams.put("volume", formData.get("volume").get(0));
		orderParams.put("from_lat", formData.get("from_lat").get(0));
		orderParams.put("from_lon", formData.get("from_lon").get(0));
		orderParams.put("to_lat", formData.get("to_lat").get(0));
		orderParams.put("to_lon", formData.get("to_lon").get(0));
		orderParams.put("date", formData.get("date").get(0));
		
		Map<String, String> result = dispatcher.onOrderReceived(orderParams);
		model.addAllAttributes(result);
		//model.addAttribute("userName", user.getUserName());
		return new ModelAndView("orderSubmitted_admin", 
	    		  "message", message);
		}
		
	@RequestMapping(value = "/computeRequest", method = RequestMethod.POST)
	public String computeRequest(@RequestParam(value="computeDate", required=false) String date, Model model,
			HttpServletRequest request, HttpServletResponse response) {
		//System.out.println("FormPost happened");
			System.out.println(date);
			if(date == "") {System.out.println("No date-filtering");}
			
			Map<String,String> datemap = new HashMap<>();
			datemap.put("date", date);
			
			List<VehiclePark> vPs = dispatcher.onComputeRequestReceived(datemap);
			VehicleRegistry vr = new VehicleRegistry();
			for(VehiclePark vP : vPs) {
				vr.addVehiclePark(vP);
			}
			
			HashMap<String, HashMap<String, List<OrderCoordinate>>> results = new HashMap<>();
			for (VehiclePark vp : vr.getVehicleParks()) {
				HashMap<String, List<OrderCoordinate>> vehicleResults = new HashMap<>();
				for (Vehicle v : vp.getVehicles()) {
					vehicleResults.put(((Integer)v.getID()).toString(), v.getPath());
					}
				results.put(((Integer)vp.getID()).toString(), vehicleResults);
			}
			for(Entry<String, HashMap<String, List<OrderCoordinate>>> vehicleResults : results.entrySet()){
				System.out.println("VehiclePark " + vehicleResults.getKey() + ":");
				HashMap<String, List<OrderCoordinate>> i = vehicleResults.getValue();
				for(Entry<String, List<OrderCoordinate>> entries : i.entrySet()) {
					System.out.println("\tVehicle " + entries.getKey() + ":");
					for(OrderCoordinate oc : entries.getValue()) {
						System.out.println("\t\t(" + oc.getLatitude() + ", " +
													 oc.getLongitude() + ")");
					}
					System.out.println("\n\t\tPath length: " + vr.getVehicleParkById(Integer.parseInt(vehicleResults.getKey()))
							.getVehicleById(Integer.parseInt(entries.getKey())).getPathLength() + " m\n");
				}
				}
			request.setAttribute("vehicleregistry", vr);
		return "computeRequest";
	}
	
	@RequestMapping(value = "/login", method = RequestMethod.GET)  
	   public String getLogin(HttpServletRequest request,
			   HttpServletResponse response) {
		return "index";
	   }
	
	@RequestMapping(value = "/deleteRequest", method = RequestMethod.POST)  
	   public ModelAndView getDeleteID(HttpServletRequest request,
			   HttpServletResponse response) {
		String message = "";
		String str_id = request.getParameter("deleteid");
		try {
			if(str_id != null && !str_id.equals("")) {
			int id = Integer.parseInt(str_id);
			message = dispatcher.onOrderDeleteRequest(id);
			return new ModelAndView("orderDeleted", "message", message);
			}
			else {
				message = "Please choose an order ID.";
				return new ModelAndView("orderDeleted", 
		    			  "message", message);
			}
		}catch (NumberFormatException e) {
			e.printStackTrace();
			}
		return null;
		}
}
