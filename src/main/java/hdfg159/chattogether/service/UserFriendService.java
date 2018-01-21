package hdfg159.chattogether.service;

import hdfg159.chattogether.domain.UserFriend;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Project:ChatTogether
 * Package:hdfg159.chattogether.service
 * Created by hdfg159 on 2018-1-16 10:12.
 */
public interface UserFriendService {
	/**
	 * 分页查找好友列表
	 *
	 * @param username
	 * 		用户名
	 * @param pageable
	 * 		分页参数
	 *
	 * @return {@code Page<UserFriend>}
	 */
	Page<UserFriend> findAllByActiveUser(String username, Pageable pageable);
	
	/**
	 * 解除好友关系
	 *
	 * @param username1
	 * 		用户1
	 * @param username2
	 * 		用户2
	 *
	 * @return boolean
	 */
	boolean unfriend(String username1, String username2);
	
	/**
	 * 建立用户好友关系
	 *
	 * @param activeUsername
	 * 		主动用户名称
	 * @param passiveUsername
	 * 		被动用户名称
	 * @param uri
	 * 		通知信息url
	 *
	 * @return boolean
	 */
	boolean buildFriend(String activeUsername, String passiveUsername, String uri);
	
	/**
	 * 接受好友邀请
	 *
	 * @param activeUsername
	 * 		主动用户名称
	 * @param passiveUsername
	 * 		被动用户名称
	 *
	 * @return boolean
	 */
	boolean acceptFriend(String activeUsername, String passiveUsername);
	
	/**
	 * 是否存在双方好友
	 *
	 * @param username1
	 * 		用户1
	 * @param username2
	 * 		用户2
	 *
	 * @return boolean
	 */
	boolean isExistUserFriend(String username1, String username2);
}
