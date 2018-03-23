package hdfg159.chattogether.service;

import hdfg159.chattogether.domain.MessageNotification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Project:ChatTogether
 * Package:hdfg159.chattogether.service
 * Created by hdfg159 on 2017-12-4 09:12.
 */
public interface MessageNotificationService {
	/**
	 * 查询用户所有的通知
	 *
	 * @param username
	 * 		用户名称
	 * @param pageable
	 * 		分页参数
	 *
	 * @return {@code Page<MessageNotification> 每页该用户的通知}
	 */
	Page<MessageNotification> findAllByReceiveNotificationUser(String username, Pageable pageable);
	
	/**
	 * 查询用户所有通知的数量
	 *
	 * @param username
	 * 		用户名称
	 *
	 * @return Long 数量
	 */
	Long countByReceiveNotificationUserAndIsRead(String username);
	
	/**
	 * 标记用户所有通知为已读
	 *
	 * @param username
	 * 		用户名
	 *
	 * @return boolean 是否成功
	 */
	boolean markNotificationsAsRead(String username);
	
	/**
	 * 标记某条通知为已读
	 *
	 * @param id
	 * 		通知id
	 *
	 * @return {@code boolean 是否成功}
	 */
	boolean markNotificationAsRead(Long id);
	
	/**
	 * 标记两个用户之间的消息通知标记已读状态
	 *
	 * @param receiveUsername
	 * 		接收者用户名
	 * @param sendUsername
	 * 		发送者用户名
	 *
	 * @return boolean
	 */
	boolean markMessageNotificationReadByUsers(String receiveUsername, String sendUsername);
}
