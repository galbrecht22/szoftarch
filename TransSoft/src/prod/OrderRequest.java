package prod;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class OrderRequest extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private App app = new App();

	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		//PrintWriter pw = res.getWriter();
		//res.setContentType("text/html");
		if(req.getParameter("button") != null) {
			app.onComputeReq();
		}
		else {
			String ID = req.getParameter("id");
			String from_lat = req.getParameter("from_lat");
			String from_lon = req.getParameter("from_lon");
			String to_lat = req.getParameter("to_lat");
			String to_lon = req.getParameter("to_lon");
			String mass = req.getParameter("mass");
			String volume = req.getParameter("volume");
			String date = req.getParameter("date");
			//pw.println("Login Success...!");
			app.onReq(ID, from_lat, from_lon, to_lat, to_lon, mass, volume, date);
		}
		
		//pw.close();
		
		req.getRequestDispatcher("/index.html").forward(req, res);

	}
}
