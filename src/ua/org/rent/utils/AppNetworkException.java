package ua.org.rent.utils;


public class AppNetworkException extends Exception {
	private static final long serialVersionUID = 693564438465940412L;
	public static final int ERROR_WRONG_TOKEN=406;
	public static final int ERROR_WRONG_REQUEST=403;
	public static final int ERROR_EMPTY_BALANCE=411;
	public static final int ERROR_SERVER_PARSING=400;
	public static final int ERROR_CONNECTION_FAILED=777;
	public static final int ERROR_CONNECTION_UNAVAILABLE=999;
	public static final int ERROR_EMPTY_ARRAY=499;
	
	
	private int errorCode;

	public AppNetworkException(String detailMessage, int errorCode) {
		super(detailMessage);
		this.errorCode=errorCode;
		//Crittercism.logHandledException(this);
	}

	public synchronized int getErrorCode() {
		return errorCode;
	}

}
