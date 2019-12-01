package bme.szoftarch.spring.controller;

import java.text.DateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.ServletContextEvent;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.ContextLoaderListener;

import bme.szoftarch.spring.model.Dispatcher;

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
	
//	@RequestMapping(value = "/spring-mvc-example", method = RequestMethod.POST)
//	public String doPost(@RequestParam String action, Model model) {
//		System.out.println("FormPost happened");
//		if(action.equals("save")) {
//			
//			OrderEvent orderEvent = new OrderEvent();
//			orderEvent.setOrderEventEnum(OrderEventEnum.COMPUTE_REQUEST);
//			OrderEventHandler orderEventHandler = new OrderEventHandler();
//
//			orderEventHandler.handle(orderEvent);
//		}
//		return "orderRequest";
//	}
	

//	@RequestMapping(value = "/user", method = RequestMethod.POST)
//	public String user(@Validated User user, Model model) {
//		System.out.println("User Page Requested");
//		model.addAttribute("userName", user.getUserName());
//		return "user";
//	}
	
	@RequestMapping(value = "/orderSubmitted", method = RequestMethod.POST)
	public String orderSubmitted(@RequestBody MultiValueMap<String, String> formData, Model model) {
		System.out.println("Order Submission Requested");
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
		return "orderSubmitted";
	}
	
	@RequestMapping(value = "/computeRequest", method = RequestMethod.POST)
	public String computeRequest(@RequestParam(value="computeDate", required=false) String date, Model model) {
		//System.out.println("FormPost happened");
			System.out.println(date);
			if(date == "") {System.out.println("All date");}
			
			Map<String,String> datemap = new HashMap<>();
			datemap.put("date", date);
			
			dispatcher.onComputeRequestReceived(datemap);
			
		return "computeRequest";
	}
}
