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
 * Created by hdfg159 on 18-3-25 下午4:46.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SuggestionFormVO {
	private Integer type;
	
	@NotNull
	@NotBlank(message = "{suggestion.title.empty}")
	@Size(min = 1, max = 20, message = "{suggestion.title.size}")
	private String title;
	
	@NotNull
	@NotBlank(message = "{suggestion.content.empty}")
	@Size(min = 1, max = 100, message = "{suggestion.content.size}")
	private String content;
}
