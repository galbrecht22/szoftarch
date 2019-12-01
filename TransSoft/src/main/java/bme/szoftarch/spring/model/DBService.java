package bme.szoftarch.spring.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Map;

import javax.servlet.ServletContextEvent;

public class DBService {
		
	public ArrayList<VehiclePark> loadVehicleParks() {

		ArrayList<VehiclePark> vehicleParks = new ArrayList<>();
		
		Connection conn = null;
        PreparedStatement pstmt = null;
        try{
           //STEP 2: Register JDBC driver
           Class.forName("org.sqlite.JDBC");
           
           //STEP 3: Open a connection
           String url="jdbc:sqlite:db\\transsoft.db";
           System.out.println(url);
           
           System.out.println("Connecting to a selected database...");
           conn = DriverManager.getConnection(url);
           System.out.println("Connected database successfully...");
           
           //STEP 4: Execute a query
           System.out.println("Creating statement...");
           String sql = "SELECT id,location_lat,location_lon FROM vehicleparks";
           pstmt = conn.prepareStatement(sql);
           
           ResultSet rs = pstmt.executeQuery();
           
           //STEP 5: Extract data from result set
           
           while(rs.next()){
              //Retrieve by column name
              int id  = rs.getInt("id");
              float location_lat = rs.getFloat("location_lat");
              float location_lon = rs.getFloat("location_lon");
              VehiclePark vp = new VehiclePark();
              vp.setID(id);
              vp.setLocation(new Coordinate(location_lat, location_lon));
              vehicleParks.add(vp);
              
              //Display values
              System.out.print("ID: " + id);
              System.out.print(", Location (lat): " + location_lat);
              System.out.println(", Location (lon): " + location_lon);
           }
           rs.close();
        }catch(SQLException se){
            //Handle errors for JDBC
            se.printStackTrace();
         }catch(Exception e){
            //Handle errors for Class.forName
            e.printStackTrace();
         }finally{
            //finally block used to close resources
            try{
               if(pstmt!=null)
                  conn.close();
            }catch(SQLException se){
            }// do nothing
            try{
               if(conn!=null)
                  conn.close();
            }catch(SQLException se){
               se.printStackTrace();
            }//end finally try
         }//end try
         System.out.println("Goodbye!");
         
         return vehicleParks;
     }

           
        public ArrayList<Vehicle> loadVehicles() {
        	
        	ArrayList<Vehicle> vehicles = new ArrayList<>();
        	
        	Connection conn = null;
            PreparedStatement pstmt = null;
            try{
               //STEP 2: Register JDBC driver
               Class.forName("org.sqlite.JDBC");
               
               //STEP 3: Open a connection
               String url="jdbc:sqlite:db\\transsoft.db";
               System.out.println(url);
               
               System.out.println("Connecting to a selected database...");
               conn = DriverManager.getConnection(url);
               System.out.println("Connected database successfully...");
               
               //STEP 4: Execute a query
               System.out.println("Creating statement...");
               String sql = "SELECT id,max_mass,max_volume,max_speed,park_id FROM vehicles";
               pstmt = conn.prepareStatement(sql);

               ResultSet rs = pstmt.executeQuery();
           
           //STEP 5: Extract data from result set
           
               while(rs.next()){
            	   //Retrieve by column name
            	   int id  = rs.getInt("id");
            	   double max_mass = rs.getFloat("max_mass");
            	   double max_volume = rs.getFloat("max_volume");
            	   int max_speed = rs.getInt("max_speed");
            	   int park_id = rs.getInt("park_id");
              
            	   Vehicle v = new Vehicle(max_mass, max_volume, id, max_speed);
            	   v.setID(id);
            	   v.setVehiclePark_ID(park_id);
              
            	   //Display values
            	   System.out.print("ID: " + id);
            	   System.out.print(", Max mass: " + max_mass);
            	   System.out.print(", Max volume: " + max_volume);
            	   System.out.print(", Max speed: " + max_speed);
            	   System.out.println(", Park ID: " + park_id);
            	   vehicles.add(v);
           }
           rs.close();
                      
        }catch(SQLException se){
           //Handle errors for JDBC
           se.printStackTrace();
        }catch(Exception e){
           //Handle errors for Class.forName
           e.printStackTrace();
        }finally{
           //finally block used to close resources
           try{
              if(pstmt!=null)
                 conn.close();
           }catch(SQLException se){
           }// do nothing
           try{
              if(conn!=null)
                 conn.close();
           }catch(SQLException se){
              se.printStackTrace();
           }//end finally try
        }//end try
        System.out.println("Goodbye!");
        
        return vehicles;
    }

    public void contextDestroyed(ServletContextEvent event) {
        // Webapp shutdown.
    }
	
	public String insertOrder(Map<String, String> orderParams) {
		Connection c = null;
        PreparedStatement pstmt = null;
        int i = 0;
        
        String resultID = null;
        
        try {
            Class.forName("org.sqlite.JDBC");
            String url="jdbc:sqlite:db\\transsoft.db";
            System.out.println(url);
            c = DriverManager.getConnection(url);
            c.setAutoCommit(false);
            System.out.println("Opened database successfully");
            
            String sql = "INSERT INTO ORDERS ("
            		+ "mass,volume,"
            		+ "from_lat,from_lon,"
            		+ "to_lat,to_lon, "
            		+ "dateof_tr) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?)";
            
            pstmt = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            
            pstmt.setDouble(1, Double.parseDouble(orderParams.get("mass")));
            pstmt.setDouble(2, Double.parseDouble(orderParams.get("volume")));
            pstmt.setFloat(3, Float.parseFloat(orderParams.get("from_lat")));
            pstmt.setFloat(4, Float.parseFloat(orderParams.get("from_lon")));
            pstmt.setFloat(5, Float.parseFloat(orderParams.get("to_lat")));
            pstmt.setFloat(6, Float.parseFloat(orderParams.get("to_lon")));
            pstmt.setString(7, orderParams.get("date"));

            i = pstmt.executeUpdate();
            
            try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    resultID = String.valueOf(generatedKeys.getLong(1));
                }
                else {
                    throw new SQLException("Creating user failed, no ID obtained.");
                }
            }
            
            pstmt.close();
            c.commit();
            c.close();
         } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
         }
         System.out.println(i + " record(s) inserted successfully");
         return resultID;
	}
	
	public ArrayList<Order> getOrders(String computeDate) {
		
		ArrayList<Order> orders = new ArrayList<Order>();
		
		Connection conn = null;
        PreparedStatement pstmt = null;
        try{
           //STEP 2: Register JDBC driver
           Class.forName("org.sqlite.JDBC");

           //STEP 3: Open a connection
           String url="jdbc:sqlite:db\\transsoft.db";
           System.out.println(url);
           
           System.out.println("Connecting to a selected database...");
           conn = DriverManager.getConnection(url);
           System.out.println("Connected database successfully...");
           
           //STEP 4: Execute a query
           System.out.println("Creating statement...");
           
           String sql = "";
           
           if(computeDate != "") {
        	   sql = "SELECT * FROM orders WHERE dateof_tr=?";
        	   pstmt = conn.prepareStatement(sql);
               pstmt.setString(1, computeDate);
           }
           else {
        	   sql = "SELECT * FROM orders";
        	   pstmt = conn.prepareStatement(sql);
           }

           ResultSet rs = pstmt.executeQuery();
           
           //STEP 5: Extract data from result set
           
           while(rs.next()){
        	   
              //Retrieve by column name
              int id  = rs.getInt("id");
              double mass = rs.getDouble("mass");
              double volume = rs.getDouble("volume");
              float from_lat = rs.getFloat("from_lat");
              float from_lon = rs.getFloat("from_lon");
              float to_lat = rs.getFloat("to_lat");
              float to_lon = rs.getFloat("to_lon");
              String date = rs.getString("dateof_tr");
              
              final OrderCoordinate from = new OrderCoordinate(from_lat, from_lon, id);
              final OrderCoordinate to = new OrderCoordinate(to_lat, to_lon, id);
              LocalDate d = LocalDate.parse(date);
              Order o = new Order(volume, mass, from, to, d, id);
              
              orders.add(o);
              
              //Display values
              System.out.print("ID: " + id);
              System.out.print(", Mass: " + mass);
              System.out.print(", Volume: " + volume);
              System.out.print(", From(lat): " + from_lat);
              System.out.print(", From(lon): " + from_lon);
              System.out.print(", To(lat): " + to_lat);
              System.out.print(", To(lon): " + to_lon);
              System.out.println(", Date: " + date);
           }
           rs.close();
        }catch(SQLException se){
           //Handle errors for JDBC
           se.printStackTrace();
        }catch(Exception e){
           //Handle errors for Class.forName
           e.printStackTrace();
        }finally{
           //finally block used to close resources
           try{
              if(pstmt!=null)
                 conn.close();
           }catch(SQLException se){
           }// do nothing
           try{
              if(conn!=null)
                 conn.close();
           }catch(SQLException se){
              se.printStackTrace();
           }//end finally try
        }//end try
        System.out.println("Goodbye!");
        return orders;
	}
}