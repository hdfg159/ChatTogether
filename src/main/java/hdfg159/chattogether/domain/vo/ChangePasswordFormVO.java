package hdfg159.chattogether.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.ScriptAssert;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * Project:ChatTogether
 * Package:hdfg159.chattogether.domain.vo
 * Created by hdfg159 on 2018-1-12 14:59.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ScriptAssert(lang = "javascript", script = "_this.newPassword.equals(_this.confirmPassword)", message = "{password.different}")
public class ChangePasswordFormVO {
	@NotBlank(message = "{password.empty}")
	@Size(min = 5, max = 16, message = "{password.size}")
	private String existingPassword;
	
	@NotBlank(message = "{password.empty}")
	@Size(min = 5, max = 16, message = "{password.size}")
	private String newPassword;
	
	@NotBlank(message = "{password.empty}")
	@Size(min = 5, max = 16, message = "{password.size}")
	private String confirmPassword;
}
