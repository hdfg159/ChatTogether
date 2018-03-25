package hdfg159.chattogether.service.impl;

import hdfg159.chattogether.data.MessageNotificationRepository;
import hdfg159.chattogether.data.UserRepository;
import hdfg159.chattogether.domain.MessageNotification;
import hdfg159.chattogether.domain.User;
import hdfg159.chattogether.exception.UserNotFoundException;
import hdfg159.chattogether.service.MessageNotificationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

import static hdfg159.chattogether.constant.MessageNotificationConsts.*;

/**
 * Project:ChatTogether
 * Package:hdfg159.chattogether.service.impl
 * Created by hdfg159 on 2017-12-4 16:10.
 */
@Slf4j
@Service
@Transactional
public class MessageNotificationServiceImpl implements MessageNotificationService {
	private final MessageNotificationRepository messageNotificationRepository;
	private final UserRepository userRepository;
	
	@Autowired
	public MessageNotificationServiceImpl(MessageNotificationRepository messageNotificationRepository, UserRepository userRepository) {
		this.messageNotificationRepository = messageNotificationRepository;
		this.userRepository = userRepository;
	}
	
	@Override
	public Page<MessageNotification> findAllByReceiveNotificationUser(String username, Pageable pageable) {
		return messageNotificationRepository.findAllByReceiveNotificationUser_Username(username, pageable);
	}
	
	@Override
	public Long countByReceiveNotificationUserAndIsRead(String username) {
		return messageNotificationRepository.countByReceiveNotificationUser_UsernameAndIsRead(username, IS_NOT_READ);
	}
	
	@Override
	public boolean markNotificationsAsRead(String username) {
		List<MessageNotification> messageNotifications = messageNotificationRepository.findAllByReceiveNotificationUser_UsernameAndIsRead(username, IS_NOT_READ);
		for (MessageNotification messageNotification : messageNotifications) {
			messageNotification.setIsRead(IS_READ);
			messageNotification.setModifiedTime(new Date());
		}
		messageNotificationRepository.save(messageNotifications);
		return true;
	}
	
	@Override
	public boolean markNotificationAsRead(Long id) {
		MessageNotification messageNotification = messageNotificationRepository.findOne(id);
		messageNotification.setModifiedTime(new Date());
		messageNotification.setIsRead(IS_READ);
		messageNotificationRepository.save(messageNotification);
		return true;
	}
	
	@Override
	public boolean markMessageNotificationReadByUsers(String receiveUsername, String sendUsername) {
		List<MessageNotification> messageNotifications = messageNotificationRepository.findAllByReceiveNotificationUser_UsernameAndSendNotificationUser_UsernameAndIsReadAndType(receiveUsername, sendUsername, IS_NOT_READ, TYPE_MESSAGE);
		for (MessageNotification messageNotification : messageNotifications) {
			messageNotification.setIsRead(IS_READ);
			messageNotification.setModifiedTime(new Date());
		}
		return true;
	}
	
	@Override
	public boolean cleanAllMessageNotifications(String username) {
		messageNotificationRepository.cleanAllMessageNotificationsByUserId(fetchUserByUsername(username).getId());
		return true;
	}
	
	private User fetchUserByUsername(String username) {
		return userRepository.findByUsernameIgnoreCase(username)
				.orElseThrow(() -> new UserNotFoundException("For Username:" + username));
	}
}
