package hdfg159.chattogether.exception;

/**
 * Project:ChatTogether
 * Package:hdfg159.chattogether.exception
 * Created by hdfg159 on 2018-1-12 11:28.
 */
public class UserProfileNotFoundException extends RuntimeException {
	public UserProfileNotFoundException() {
	}
	
	public UserProfileNotFoundException(String message) {
		super(message);
	}
}
