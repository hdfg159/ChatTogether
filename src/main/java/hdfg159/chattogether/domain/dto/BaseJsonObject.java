package hdfg159.chattogether.domain.dto;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

/**
 * Project:ChatTogether
 * Package:hdfg159.chattogether.domain.dto
 * Created by hdfg159 on 2017-12-1 11:56.
 */
@Data
@Builder
public class BaseJsonObject<T> implements Serializable {
	public static final int SUCCESS = 1;
	public static final String STR_SUCCESS = "SUCCESS";
	public static final int FAIL = 0;
	public static final String STR_FAIL = "FAIL";
	
	private T data;
	private String message;
	private int code;
}
