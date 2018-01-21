package hdfg159.chattogether.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.*;
import java.util.Date;

/**
 * Project:ChatTogether
 * Package:hdfg159.chattogether.domain.vo
 * Created by hdfg159 on 2018-1-12 11:02.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserProfileFormVO {
	@NotNull
	private Long id;
	
	private String qq;
	
	private String wechat;
	
	@Past
	private Date birthday;
	
	@Range(min = 0, max = 199)
	private Integer age;
	
	@NotBlank
	private String sex;
	
	@NotBlank
	@Pattern(regexp = "^(13[0-9]|14[579]|15[0-3,5-9]|17[0135678]|18[0-9])\\d{8}$")
	private String phoneNumber;
	
	@Email
	private String email;
	
	@NotBlank
	private String selfIntroduction;
}
