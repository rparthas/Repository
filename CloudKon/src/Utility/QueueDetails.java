package Utility;

import java.util.Properties;

public class QueueDetails {

	private String hostName;
	private String requestQueue;
	private String responseQueue;
	private String clientName;
	private Properties props;

	public Properties getProps() {
		return props;
	}

	public void setProps(Properties props) {
		this.props = props;
	}

	public String getHostName() {
		return hostName;
	}

	public void setHostName(String hostName) {
		this.hostName = hostName;
	}

	public String getClientName() {
		return clientName;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
	}

	public String getRequestQueue() {
		return requestQueue;
	}

	public void setRequestQueue(String requestQueue) {
		this.requestQueue = requestQueue;
	}

	public String getResponseQueue() {
		return responseQueue;
	}

	public void setResponseQueue(String responseQueue) {
		this.responseQueue = responseQueue;
	}

}
