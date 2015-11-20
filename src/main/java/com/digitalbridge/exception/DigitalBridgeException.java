package com.digitalbridge.exception;

/**
 * <p> DigitalBridgeException class. </p>
 *
 * @author rajakolli
 * @version 1 : 0
 */
public class DigitalBridgeException extends Exception {

	private static final long serialVersionUID = 3502009474963599102L;

	private final DigitalBridgeExceptionBean faultBean;

	/**
	 * <p>
	 * Constructor for DigitalBridgeException.
	 * </p>
	 * Constructs a new exception with the specified detail message.
	 *
	 * @param faultBean a {@link com.digitalbridge.exception.DigitalBridgeExceptionBean} object.
	 */
	public DigitalBridgeException(DigitalBridgeExceptionBean faultBean) {
		super(faultBean.getFaultString());
		this.faultBean = faultBean;
	}

	/**
	 * <p>
	 * Constructor for DigitalBridgeException.
	 * </p>
	 *
	 * @param message a {@link java.lang.String} object.
	 * @param faultinfo a {@link com.digitalbridge.exception.DigitalBridgeExceptionBean} object.
	 */
	public DigitalBridgeException(String message, DigitalBridgeExceptionBean faultinfo) {
		super(message);
		this.faultBean = faultinfo;
	}

	/**
	 * Constructs a new exception with the specified detail message and cause.
	 * <p>
	 * Note that the detail message associated with {@code cause} is <i>not</i> automatically incorporated in this
	 * exception's detail message.
	 *
	 * @param message the detail message (which is saved for later retrieval by the {@link #getMessage()} method).
	 * @param cause the cause (which is saved for later retrieval by the {@link #getCause()} method). (A <tt>null</tt>
	 *          value is permitted, and indicates that the cause is nonexistent or unknown.)
	 * @since 1.0
	 * @param faultinfo a {@link com.digitalbridge.exception.DigitalBridgeExceptionBean} object.
	 */
	public DigitalBridgeException(String message, DigitalBridgeExceptionBean faultinfo, Throwable cause) {
		super(message, cause);
		this.faultBean = faultinfo;
	}

	/**
	 * <p>
	 * Constructor for DigitalBridgeException.
	 * </p>
	 *
	 * @param code a {@link java.lang.String} object.
	 * @param message a {@link java.lang.String} object.
	 */
	public DigitalBridgeException(String code, String message) {
		super(message);
		this.faultBean = new DigitalBridgeExceptionBean();
		this.faultBean.setFaultCode(code);
		this.faultBean.setFaultString(message);
	}

	/**
	 * <p>
	 * Getter for the field <code>faultBean</code>.
	 * </p>
	 *
	 * @return a {@link com.digitalbridge.exception.DigitalBridgeExceptionBean} object.
	 */
	public DigitalBridgeExceptionBean getFaultBean() {
		return faultBean;
	}

}
