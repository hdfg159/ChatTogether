package hdfg159.chattogether.exception;

/**
 * Project:ChatTogether
 * Package:hdfg159.chattogether.exception
 * Created by hdfg159 on 2018-1-18 11:45.
 */
public class UserFriendNotFoundException extends RuntimeException {
	public UserFriendNotFoundException() {
	}
	
	public UserFriendNotFoundException(String message) {
		super(message);
	}
}
