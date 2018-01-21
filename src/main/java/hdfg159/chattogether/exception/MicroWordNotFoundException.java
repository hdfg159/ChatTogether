package hdfg159.chattogether.exception;

/**
 * Project:ChatTogether
 * Package:hdfg159.chattogether.exception
 * Created by hdfg159 on 2017-12-4 10:18.
 */
public class MicroWordNotFoundException extends RuntimeException {
	public MicroWordNotFoundException() {
		super();
	}
	
	public MicroWordNotFoundException(String message) {
		super(message);
	}
}
