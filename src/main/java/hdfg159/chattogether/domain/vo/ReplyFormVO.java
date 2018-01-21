package hdfg159.chattogether.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Project:ChatTogether
 * Package:hdfg159.chattogether.domain.vo
 * Created by hdfg159 on 2017-12-5 15:38.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReplyFormVO {
	@NotNull
	private Long microWordId;
	
	@NotNull
	private Long microwordCommentId;
	
	@NotNull
	private Long repliedUserId;
	
	@NotNull
	@NotBlank(message = "{reply.content.empty}")
	@Size(min = 1, max = 200, message = "{reply.content.size}")
	private String content;
}
