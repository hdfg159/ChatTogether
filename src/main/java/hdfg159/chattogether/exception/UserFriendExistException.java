package hdfg159.chattogether.exception;

/**
 * Project:ChatTogether
 * Package:hdfg159.chattogether.exception
 * Created by hdfg159 on 2018-1-17 12:18.
 */
public class UserFriendExistException extends RuntimeException {
	public UserFriendExistException() {
	}
	
	public UserFriendExistException(String message) {
		super(message);
	}
}
