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
 * Created by hdfg159 on 2017/7/6 16:30.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserFormVO {
	@NotNull
	@NotBlank(message = "{username.empty}")
	@Size(min = 5, max = 16, message = "{username.size}")
	private String username;
	
	@NotNull
	@NotBlank(message = "{password.empty}")
	@Size(min = 5, max = 16, message = "{password.size}")
	private String password;
	
	@NotNull
	private String validCode;
}
