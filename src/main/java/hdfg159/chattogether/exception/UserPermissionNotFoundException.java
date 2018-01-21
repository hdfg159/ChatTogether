package hdfg159.chattogether.exception;

/**
 * Project:ChatTogether
 * Package:hdfg159.chattogether.exception
 * Created by hdfg159 on 2017-11-28 17:53.
 */
public class UserPermissionNotFoundException extends RuntimeException {
	public UserPermissionNotFoundException() {
		super();
	}
	
	public UserPermissionNotFoundException(String message) {
		super(message);
	}
}
