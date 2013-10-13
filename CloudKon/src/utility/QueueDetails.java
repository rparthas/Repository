package utility;

import java.io.Serializable;


public class QueueDetails implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String requestQueue;
	private String responseQueue;
	private String clientName;
	private String url;

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

	public String getClientName() {
		return clientName;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

}
