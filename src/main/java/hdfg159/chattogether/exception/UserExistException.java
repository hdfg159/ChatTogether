package hdfg159.chattogether.exception;

/**
 * Project:ChatTogether
 * Package:hdfg159.chattogether.exception
 * Created by hdfg159 on 2017-11-28 19:58.
 */
public class UserExistException extends RuntimeException {
	public UserExistException() {
		super();
	}
	
	public UserExistException(String message) {
		super(message);
	}
}
