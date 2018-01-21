package hdfg159.chattogether.exception;

/**
 * Project:ChatTogether
 * Package:hdfg159.chattogether.exception
 * Created by hdfg159 on 2018-1-12 20:33.
 */
public class PasswordNotMatchException extends RuntimeException {
	public PasswordNotMatchException() {
		super();
	}
	
	public PasswordNotMatchException(String message) {
		super(message);
	}
}
