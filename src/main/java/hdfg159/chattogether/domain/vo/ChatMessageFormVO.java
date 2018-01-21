package hdfg159.chattogether.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

/**
 * Project:ChatTogether
 * Package:hdfg159.chattogether.domain.vo
 * Created by hdfg159 on 2018-1-18 18:36.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChatMessageFormVO {
	@NotBlank
	private String receiveUsername;
	
	@NotBlank
	private String content;
}
