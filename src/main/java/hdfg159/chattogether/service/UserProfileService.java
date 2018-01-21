package hdfg159.chattogether.service;

import hdfg159.chattogether.domain.UserProfile;
import hdfg159.chattogether.domain.vo.UserProfileFormVO;

/**
 * Project:ChatTogether
 * Package:hdfg159.chattogether.service
 * Created by hdfg159 on 2018-1-12 11:01.
 */
public interface UserProfileService {
	/**
	 * 通过名称查询用户资料
	 *
	 * @param username
	 * 		用户名
	 *
	 * @return UserProfile 用户的资料
	 */
	UserProfile findByUsername(String username);
	
	/**
	 * 保存用户资料的表单
	 *
	 * @param userProfileFormVO
	 * 		用户资料表单展示
	 *
	 * @return UserProfile
	 */
	UserProfile save(UserProfileFormVO userProfileFormVO);
	
	/**
	 * 根据Id查询用户资料
	 *
	 * @param id
	 *
	 * @return UserProfile 用户资料
	 */
	UserProfile findById(Long id);
}
