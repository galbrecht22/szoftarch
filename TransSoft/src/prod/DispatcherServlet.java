package prod;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class DispatcherServlet
 */
@WebServlet("/DispatcherServlet")
public class DispatcherServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private OrderEventHandler orderEventHandler = new OrderEventHandler();
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DispatcherServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//doGet(request, response);
		
		if(request.getParameter("button") != null) {
			
			OrderEvent orderEvent = new OrderEvent();
			orderEvent.setOrderEventEnum(OrderEventEnum.COMPUTE_REQUEST);
			
			orderEventHandler.handle(orderEvent);
			
			PrintWriter pw = response.getWriter();
			response.setContentType("text/html");
			pw.println("<tr><td><a href=\\TransSoft\\>Back To Main Page</tr></td>");
			pw.close();
		}
		else {
		HashMap<String, String> orderParams = new HashMap<>();
		orderParams.put("ID", request.getParameter("id"));
		orderParams.put("mass", request.getParameter("mass"));
		orderParams.put("volume", request.getParameter("volume"));
		orderParams.put("from_lat", request.getParameter("from_lat"));
		orderParams.put("from_lon", request.getParameter("from_lon"));
		orderParams.put("to_lat", request.getParameter("to_lat"));
		orderParams.put("to_lon", request.getParameter("to_lon"));
		orderParams.put("date", request.getParameter("date"));
		
		OrderEvent orderEvent = new OrderEvent();
		orderEvent.setOrderEventEnum(OrderEventEnum.NEW_ORDER);
		orderEvent.setEvent(orderParams);
		
		orderEventHandler.handle(orderEvent);
		
		request.getRequestDispatcher("/index.html").forward(request, response);
		}
	}

}
