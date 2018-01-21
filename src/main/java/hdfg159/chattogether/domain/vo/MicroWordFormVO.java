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
 * Created by hdfg159 on 2017-12-1 15:09.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MicroWordFormVO {
	@NotNull
	@NotBlank(message = "{microword.content.empty}")
	@Size(min = 1, max = 200, message = "{microword.content.size}")
	String content;
}
