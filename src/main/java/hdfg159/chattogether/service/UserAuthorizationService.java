package hdfg159.chattogether.service;

import hdfg159.chattogether.domain.vo.UserAuthorizationFormVO;

/**
 * Project:ChatTogether
 * Package:hdfg159.chattogether.service
 * Created by hdfg159 on 2018-1-12 22:32.
 */
public interface UserAuthorizationService {
	/**
	 * 根据用户权限表单保存用户权限信息
	 *
	 * @param userAuthorizationFormVO
	 * 		用户权限表单基本信息
	 *
	 * @return boolean 是否成功
	 */
	boolean save(UserAuthorizationFormVO userAuthorizationFormVO);
}
