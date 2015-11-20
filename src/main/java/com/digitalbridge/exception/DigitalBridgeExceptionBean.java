package com.digitalbridge.exception;

import java.io.Serializable;

/**
 * <p>DigitalBridgeExceptionBean class.</p>
 *
 * @author rajakolli
 * @version 1 : 0
 */
public class DigitalBridgeExceptionBean implements Serializable{

	private static final long serialVersionUID = 1535308599415652228L;
	/**
	 * Fault Code
	 */
	 private String faultCode;
	 /**
	  * Fault String
	  */
     private String faultString;
     
	/**
	 * <p>Getter for the field <code>faultCode</code>.</p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	public String getFaultCode() {
		return faultCode;
	}
	/**
	 * <p>Setter for the field <code>faultCode</code>.</p>
	 *
	 * @param faultCode a {@link java.lang.String} object.
	 */
	public void setFaultCode(String faultCode) {
		this.faultCode = faultCode;
	}
	/**
	 * <p>Getter for the field <code>faultString</code>.</p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	public String getFaultString() {
		return faultString;
	}
	/**
	 * <p>Setter for the field <code>faultString</code>.</p>
	 *
	 * @param faultString a {@link java.lang.String} object.
	 */
	public void setFaultString(String faultString) {
		this.faultString = faultString;
	}


}
