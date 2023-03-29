package log4jwebtracker.servlet.init;

import java.io.File;
import java.net.URL;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import org.apache.logging.log4j.core.util.Loader;


/**
 * Log4J servlet initializer.
 *
 * @author Mariano Ruiz
 */
public class Log4jInitServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public void init() throws ServletException {
		String prefix = getServletContext().getRealPath("/");
		String file = getInitParameter("log4jConfigLocation");
		if (file==null) {
			URL url = Loader.getResource("log4j.properties", this.getClass().getClassLoader());
			if(new File(url.getFile()).exists()) {
				System.out.println("Log4jInitServlet *** Log4j configuration exists="+ url);
			} else {
				url = Loader.getResource("log4j2.xml",this.getClass().getClassLoader());
				if(url==null || !new File(url.getFile()).exists()) {
					System.err.println("*** Log4jInitServlet Log4j nor log4j2 configuration file not found. "+url);
				} else {
					System.out.println("*** Log4jInitServlet Log4j2 configuration exists="+ url);
				}
			}
		}
		super.init();
	}
}
