package edu.iit.cs550.core;

import java.io.Serializable;

public class DataObject implements Serializable {

	@Override
	public String toString() {
		return "DataObject [key=" + key + ", value=" + value + ", operation="
				+ operation + ", success=" + success + "]";
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -2556165966825086057L;

	private Object key;

	private Object value;

	private String operation;

	private String success = "N";

	public DataObject(Object key, Object value, String operation) {
		super();
		this.key = key;
		this.value = value;
		this.operation = operation;
	}

	public String getSuccess() {
		return success;
	}

	public void setSuccess(String success) {
		this.success = success;
	}

	public Object getKey() {
		return key;
	}

	public void setKey(Object key) {
		this.key = key;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}

	public String getOperation() {
		return operation;
	}

	public void setOperation(String operation) {
		this.operation = operation;
	}

	public boolean isSuccess() {
		return "Y".equals(success);
	}

}
