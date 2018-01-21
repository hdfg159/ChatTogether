package hdfg159.chattogether.domain.ao;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Project:ChatTogether
 * Package:hdfg159.chattogether.domain.ao
 * Created by hdfg159 on 2018-1-12 19:55.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChangePasswordAO {
	private String username;
	private String oldPassword;
	private String newPassword;
}
