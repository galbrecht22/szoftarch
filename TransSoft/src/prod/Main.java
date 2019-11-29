package prod;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class Main implements ServletContextListener {
	private Controller controller = Controller.getInstance();
	
	public void contextInitialized(ServletContextEvent event) {
    	// Webapp startup.
    	controller.loadModel();
    }
    
    public void contextDestroyed(ServletContextEvent event) {
        // Webapp shutdown.
    	
    }
    
}
