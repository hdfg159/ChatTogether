package hdfg159.chattogether.domain.dto;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

/**
 * Project:ChatTogether
 * Package:hdfg159.chattogether.domain.dto
 * Created by hdfg159 on 18-3-23 上午10:44.
 */
@Data
@Builder
public class MessageJsonObject<T> implements Serializable {
	private String sendUsername;
	private String receiveUsername;
	private T content;
}
