package log4jwebtracker.logging;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.Appender;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.appender.FileAppender;
import org.apache.logging.log4j.core.appender.RollingFileAppender;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.config.LoggerConfig;


/**
 * Logging utils methods.
 *
 * @author Mariano Ruiz
 */
public abstract class LoggingUtils {

	public static synchronized List getFileAppenders() {
		List list = new ArrayList();
		final LoggerContext ctx = (LoggerContext) LogManager.getContext(false);
		final Configuration config = ctx.getConfiguration();
		for (Map.Entry<String, Appender> entry : config.getAppenders().entrySet()) {
			if (entry.getValue() instanceof FileAppender || entry.getValue() instanceof RollingFileAppender) {
				list.add(entry.getValue());
			}
		}
		return list;

	}

	static synchronized public String getFileNameFromAppender(String appenderName) {
		Iterator e = getFileAppenders().iterator();
		while(e.hasNext()) {
			Appender a = (Appender) e.next();
			if(a.getName().equals(appenderName)) {
				String fileName = null;
				if (a instanceof FileAppender) {
					fileName=((FileAppender) a).getFileName();
				} else if (a instanceof RollingFileAppender) {
					fileName=((RollingFileAppender) a).getFileName();
				}
				return fileName;
			}
		}
		return null;
	}

	static public boolean contains(List<LoggerConfig> loggers, String loggerName) {
		int i=0;
		while(i<loggers.size()) {
			if(loggers.get(i).getName().equals(loggerName)) {
				return true;
			}
			i++;
		}
		return false;
	}

	static public List<LoggerConfig> getLoggers() {
		final LoggerContext ctx = (LoggerContext) LogManager.getContext(false);
		final Configuration config = ctx.getConfiguration();

		List loggersList = new ArrayList(Arrays.asList(config.getLoggers().values().toArray()));
		Collections.sort(loggersList, new Comparator<LoggerConfig>() {
			public int compare(LoggerConfig log0, LoggerConfig log1) {
				return log0.getName().compareTo(log1.getName());
			}
		});
		return loggersList;
	}
}
