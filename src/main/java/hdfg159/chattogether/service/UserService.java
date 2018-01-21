package hdfg159.chattogether.service;

import hdfg159.chattogether.domain.User;
import hdfg159.chattogether.domain.ao.ChangePasswordAO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Project:ChatTogether
 * Package:hdfg159.chattogether.service
 * Created by hdfg159 on 2017-11-28 11:37.
 */
public interface UserService {
	/**
	 * 保存新注册的用户信息
	 *
	 * @param user
	 * 		用户基本信息
	 *
	 * @return boolean 是否成功
	 */
	boolean save(User user);
	
	/**
	 * 查找所有用户信息
	 *
	 * @param pageable
	 * 		分页参数
	 *
	 * @return {@code Page<User>}某页用户信息
	 */
	Page<User> findAll(Pageable pageable);
	
	/**
	 * 通过主键ID删除用户信息
	 *
	 * @param id
	 * 		用户id
	 *
	 * @return boolean 是否成功
	 */
	boolean deleteById(Long id);
	
	/**
	 * 根据用户名关键字模糊查询用户信息
	 *
	 * @param username
	 * 		用户名
	 * @param pageable
	 * 		分页参数
	 *
	 * @return {@code Page<User>} 某页用户信息
	 */
	Page<User> findByUsernameContaining(String username, Pageable pageable);
	
	/**
	 * 重置用户密码，生成是的密码随机字母数字组合
	 *
	 * @param username
	 * 		用户名
	 *
	 * @return String 用户的新密码
	 */
	String resetPassword(String username);
	
	/**
	 * 根据用户改密码的复用对象模型修改用户密码
	 *
	 * @param changePasswordAO
	 * 		关于密码修改信息相关复用的对象模型
	 *
	 * @return boolean 是否成功
	 */
	boolean changePassword(ChangePasswordAO changePasswordAO);
	
	/**
	 * 根据用户名查询用户
	 *
	 * @param username
	 * 		用户名
	 *
	 * @return User 用户
	 */
	User findByUsernameIgnoreCase(String username);
	
	/**
	 * 切换用户账户状态
	 *
	 * @param id
	 * 		用户ID
	 *
	 * @return boolean
	 */
	boolean switchUserAccountStateById(Long id);
}
