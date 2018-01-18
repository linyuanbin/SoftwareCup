package timing;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
 
 
public class NFDFlightDataTaskListener implements  ServletContextListener {
 
    public void contextInitialized(ServletContextEvent sce) {
         new TimerManager();
    }
 
    public void contextDestroyed(ServletContextEvent sce) {
        // TODO Auto-generated method stub
         
    }
 
}