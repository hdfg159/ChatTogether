package hdfg159.chattogether.service;

import hdfg159.chattogether.domain.UserPermission;

import java.util.List;

/**
 * Project:ChatTogether
 * Package:hdfg159.chattogether.service
 * Created by hdfg159 on 2018-1-12 17:34.
 */
public interface UserPermissionService {
	/**
	 * 查找所有权限
	 *
	 * @return List 权限列表
	 */
	List<UserPermission> findAll();
	
	/**
	 * 根据不区分大小写用户名查找该用户所有已经拥有权限
	 *
	 * @param username
	 * 		用户名
	 *
	 * @return List 权限列表
	 */
	List<UserPermission> findAllByUsernameIgnoreCase(String username);
}
