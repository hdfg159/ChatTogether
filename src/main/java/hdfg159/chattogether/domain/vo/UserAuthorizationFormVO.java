package hdfg159.chattogether.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Project:ChatTogether
 * Package:hdfg159.chattogether.domain.vo
 * Created by hdfg159 on 2018-1-12 17:06.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserAuthorizationFormVO {
	private String username;
	private Long[] permissions;
}
