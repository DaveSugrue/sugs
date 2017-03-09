package ie.sugrue.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Utils {

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	public static Boolean isNotNull(Object val) {
		return !isNull(val);
	}

	public static Boolean isNull(Object val) {
		return (null == val);
	}

}
