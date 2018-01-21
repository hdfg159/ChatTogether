package hdfg159.chattogether.exception;

/**
 * Project:ChatTogether
 * Package:hdfg159.chattogether.exception
 * Created by hdfg159 on 2018-1-21 12:06.
 */
public class UserAccountStateNotFoundException extends RuntimeException {
	public UserAccountStateNotFoundException() {
	}
	
	public UserAccountStateNotFoundException(String message) {
		super(message);
	}
}
