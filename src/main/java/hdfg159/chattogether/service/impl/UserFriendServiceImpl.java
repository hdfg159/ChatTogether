package hdfg159.chattogether.service.impl;

import hdfg159.chattogether.data.MessageNotificationRepository;
import hdfg159.chattogether.data.UserFriendRepository;
import hdfg159.chattogether.data.UserRepository;
import hdfg159.chattogether.domain.MessageNotification;
import hdfg159.chattogether.domain.User;
import hdfg159.chattogether.domain.UserFriend;
import hdfg159.chattogether.exception.UserFriendExistException;
import hdfg159.chattogether.exception.UserFriendNotFoundException;
import hdfg159.chattogether.exception.UserNotFoundException;
import hdfg159.chattogether.service.UserFriendService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Optional;

import static hdfg159.chattogether.constant.MessageNotificationConsts.*;

/**
 * Project:ChatTogether
 * Package:hdfg159.chattogether.service.impl
 * Created by hdfg159 on 2018-1-16 10:12.
 */
@Slf4j
@Service
@Transactional
public class UserFriendServiceImpl implements UserFriendService {
	private final UserFriendRepository userFriendRepository;
	private final MessageNotificationRepository messageNotificationRepository;
	private final UserRepository userRepository;
	
	@Autowired
	public UserFriendServiceImpl(UserFriendRepository userFriendRepository, MessageNotificationRepository messageNotificationRepository, UserRepository userRepository) {
		this.userFriendRepository = userFriendRepository;
		this.messageNotificationRepository = messageNotificationRepository;
		this.userRepository = userRepository;
	}
	
	@Override
	public Page<UserFriend> findAllByActiveUser(String username, Pageable pageable) {
		return userFriendRepository.findAllByActiveUser_Username(username, pageable);
	}
	
	@Override
	public boolean unfriend(String username1, String username2) {
		userFriendRepository.deleteByActiveUser_UsernameAndPassiveUser_Username(username1, username2);
		userFriendRepository.deleteByActiveUser_UsernameAndPassiveUser_Username(username2, username1);
		return true;
	}
	
	@Override
	public boolean buildFriend(String activeUsername, String passiveUsername, String uri) {
		User activeUser = fetchUserByUsername(activeUsername);
		User passiveUser = fetchUserByUsername(passiveUsername);
		
		Optional<UserFriend> userFriend = userFriendRepository.findByActiveUser_UsernameAndPassiveUser_Username(activeUsername, passiveUsername);
		if (userFriend.isPresent()) {
			log.error("UserFriend Is Exist:For {}-{}", activeUsername, passiveUsername);
		} else {
			UserFriend saveUserFriend = buildUserFriendByActiveUserAndPassiveUser(activeUser, passiveUser);
			userFriendRepository.save(saveUserFriend);
		}
		
		MessageNotification userFriendNotification =
				buildUserFriendNotificationByUsers(uri, activeUser, passiveUser);
		messageNotificationRepository.save(userFriendNotification);
		return true;
	}
	
	@Override
	public boolean acceptFriend(String activeUsername, String passiveUsername) {
		//检测对方删除你好友后不能接收请求
		detectOneWayFriends(activeUsername, passiveUsername);
		//检测是否都是双向好友
		detectTwoWayFriends(activeUsername, passiveUsername);
		User activeUser = fetchUserByUsername(activeUsername);
		User passiveUser = fetchUserByUsername(passiveUsername);
		userFriendRepository.save(buildUserFriendByActiveUserAndPassiveUser(activeUser, passiveUser));
		return true;
	}
	
	private void detectOneWayFriends(String activeUsername, String passiveUsername) {
		userFriendRepository
				.findByActiveUser_UsernameAndPassiveUser_Username(passiveUsername, activeUsername)
				.orElseThrow(() -> new UserFriendNotFoundException("For activeUsername:" + passiveUsername + "-passiveUSername:" + activeUsername));
	}
	
	private void detectTwoWayFriends(String activeUsername, String passiveUsername) {
		userFriendRepository.findByActiveUser_UsernameAndPassiveUser_Username(activeUsername, passiveUsername).ifPresent(userFriend -> {
			throw new UserFriendExistException("For activeUsername:" + activeUsername + "-passiveUSername:" + passiveUsername);
		});
		
	}
	
	@Override
	public boolean isExistUserFriend(String username1, String username2) {
		return userFriendRepository.isExistFriend(username1, username2);
	}
	
	private User fetchUserByUsername(String username) {
		return userRepository.findByUsernameIgnoreCase(username)
				.orElseThrow(() -> new UserNotFoundException("For Username:" + username));
	}
	
	private UserFriend buildUserFriendByActiveUserAndPassiveUser(User activeUser, User passiveUser) {
		Date now = new Date();
		return UserFriend.builder()
				.activeUser(activeUser)
				.passiveUser(passiveUser)
				.createTime(now)
				.modifiedTime(now)
				.build();
	}
	
	private MessageNotification buildUserFriendNotificationByUsers(String uri, User activeUser, User passiveUser) {
		Date now = new Date();
		return MessageNotification.builder()
				.content(BUILD_USERFRIEND)
				.sendNotificationUser(activeUser)
				.receiveNotificationUser(passiveUser)
				.type(TYPE_USERFRIEND)
				.url(uri)
				.isRead(IS_NOT_READ)
				.createTime(now)
				.modifiedTime(now)
				.build();
	}
}
