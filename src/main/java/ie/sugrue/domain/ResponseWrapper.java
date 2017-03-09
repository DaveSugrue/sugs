package ie.sugrue.domain;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * Used by all services to wrap a response being sent back to a client. It always contains a status object and then an array of objects to
 * be returned to the client
 * 
 * @author Dave Sugrue
 *
 */
@Component
@Scope(value = "prototype")
public class ResponseWrapper {
	private Status			status	= new Status();
	private List			objects	= new ArrayList();

	private final Logger	log		= LoggerFactory.getLogger(this.getClass());

	public ResponseWrapper() {

	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public List getObject() {
		return objects;
	}

	public void setObject(ArrayList objects) {
		this.objects = objects;
	}

	public void addObject(Object object) {
		this.objects.add(object);
	}

	public ResponseWrapper complete() {
		if (this.getStatus().getCode() == 2) {
			log.error("");
			log.error("##################################################     ERROR     ##################################################");
			log.info("Error encountered :: {}", this);
			log.error("########################################     End     ########################################");
			log.error("");
		} else {
			log.info("Returning :: {}", this);
		}

		return this;
	}

	@Override
	public String toString() {
		return "ResponseWrapper [status=" + status + ", objects=" + objects + "]";
	}
}
