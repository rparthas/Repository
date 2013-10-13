package utility;

import java.io.Serializable;


public class QueueDetails implements Serializable {

	/**
	 * 
	 */
	public static final long serialVersionUID = 1L;
	public String requestQueue;
	public String responseQueue;
	public String clientName;
	public String url;

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
	
	public String toString(){
		StringBuilder builder = new StringBuilder(url);
		builder.append("###");
		builder.append(clientName);
		builder.append("###");
		builder.append(requestQueue);
		builder.append("###");
		builder.append(responseQueue);
		
		return builder.toString();
	}

	public QueueDetails() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public QueueDetails(String str) {
		super();
		String[] strArray = str.split("###");
		this.url= strArray[0];
		this.clientName= strArray[1];
		this.requestQueue= strArray[2];
		this.responseQueue= strArray[3];
		
		// TODO Auto-generated constructor stub
	}

}
