package hdfg159.chattogether.service.impl;

import hdfg159.chattogether.data.MessageNotificationRepository;
import hdfg159.chattogether.data.MessageRepository;
import hdfg159.chattogether.data.UserFriendRepository;
import hdfg159.chattogether.data.UserRepository;
import hdfg159.chattogether.domain.Message;
import hdfg159.chattogether.domain.MessageNotification;
import hdfg159.chattogether.domain.User;
import hdfg159.chattogether.domain.ao.ChatMessageAO;
import hdfg159.chattogether.exception.UserFriendNotFoundException;
import hdfg159.chattogether.exception.UserNotFoundException;
import hdfg159.chattogether.service.MessageService;
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
 * Created by hdfg159 on 2018-1-18 18:50.
 */
@Slf4j
@Service
@Transactional
public class MessageServiceImpl implements MessageService {
	private final MessageRepository messageRepository;
	private final MessageNotificationRepository messageNotificationRepository;
	private final UserRepository userRepository;
	private final UserFriendRepository userFriendRepository;
	
	@Autowired
	public MessageServiceImpl(MessageRepository messageRepository, MessageNotificationRepository messageNotificationRepository, UserRepository userRepository, UserFriendRepository userFriendRepository) {
		this.messageRepository = messageRepository;
		this.messageNotificationRepository = messageNotificationRepository;
		this.userRepository = userRepository;
		this.userFriendRepository = userFriendRepository;
	}
	
	@Override
	public Page<Message> findAllByUsers(String sendUsername, String receiveUsername, Pageable pageable) {
		markReadMessageByUsers(sendUsername, receiveUsername);
		markMessageNotificationReadByUsers(sendUsername, receiveUsername);
		return messageRepository.findAllByUsers(sendUsername, receiveUsername, pageable);
	}
	
	private void markReadMessageByUsers(String receiveUsername, String sendUsername) {
		List<Message> unReadMessage = messageRepository.findAllByReceiveUser_UsernameAndSendUser_UsernameAndIsRead(receiveUsername, sendUsername, IS_NOT_READ);
		for (Message message : unReadMessage) {
			message.setModifiedTime(new Date());
			message.setIsRead(IS_READ);
		}
		messageRepository.save(unReadMessage);
	}
	
	private void markMessageNotificationReadByUsers(String receiveUsername, String sendUsername) {
		List<MessageNotification> messageNotifications = messageNotificationRepository.findAllByReceiveNotificationUser_UsernameAndSendNotificationUser_UsernameAndIsReadAndType(receiveUsername, sendUsername, IS_NOT_READ, TYPE_MESSAGE);
		for (MessageNotification messageNotification : messageNotifications) {
			messageNotification.setIsRead(IS_READ);
			messageNotification.setModifiedTime(new Date());
		}
	}
	
	@Override
	public Message save(ChatMessageAO chatMessageAO) {
		String sendUsername = chatMessageAO.getSendUsername();
		String receiveUsername = chatMessageAO.getReceiveUsername();
		if (!isExistFriend(sendUsername, receiveUsername)) {
			throw new UserFriendNotFoundException("For activeUsername:" + sendUsername + "-passiveUsername:" + receiveUsername);
		}
		
		User sendUser = fetchUserByUsername(sendUsername);
		User receiveUser = fetchUserByUsername(receiveUsername);
		
		sendMessageNotification(chatMessageAO.getUri(), sendUser, receiveUser);
		
		Message message = buildMessageByUsers(chatMessageAO, sendUser, receiveUser);
		return messageRepository.save(message);
	}
	
	private boolean isExistFriend(String sendUsername, String receiveUsername) {
		return userFriendRepository.isExistFriend(sendUsername, receiveUsername);
	}
	
	private User fetchUserByUsername(String username) {
		return userRepository.findByUsernameIgnoreCase(username)
				.orElseThrow(() -> new UserNotFoundException("For Username:" + username));
	}
	
	private void sendMessageNotification(String uri, User sendUser, User receiveUser) {
		MessageNotification notification = null;
		if (getUserAllUnReadMessages(receiveUser.getUsername(), sendUser.getUsername()).isEmpty()) {
			notification = buildMessageNotificationByUsersAndUrl(uri, sendUser, receiveUser);
		} else {
			//如果有消息提醒
			//消息提醒里面没找到消息类型和对应的发送接收者的消息话，新发送一条通知
			notification = messageNotificationRepository
					.findFirstByTypeAndSendNotificationUserAndReceiveNotificationUser(TYPE_MESSAGE, sendUser, receiveUser)
					.orElse(buildMessageNotificationByUsersAndUrl(uri, sendUser, receiveUser));
		}
		notification.setModifiedTime(new Date());
		notification.setIsRead(IS_NOT_READ);
		messageNotificationRepository.save(notification);
	}
	
	private Message buildMessageByUsers(ChatMessageAO chatMessageAO, User sendUser, User receiveUser) {
		Date now = new Date();
		return Message.builder()
				.sendUser(sendUser)
				.receiveUser(receiveUser)
				.content(chatMessageAO.getChatMessageFormVO().getContent())
				.isRead(IS_NOT_READ)
				.createTime(now)
				.modifiedTime(now)
				.build();
	}
	
	private List<Message> getUserAllUnReadMessages(String receiveUsername, String sendUsername) {
		return messageRepository.findAllByReceiveUser_UsernameAndSendUser_UsernameAndIsRead(receiveUsername, sendUsername, IS_NOT_READ);
	}
	
	private MessageNotification buildMessageNotificationByUsersAndUrl(String uri, User sendUser, User receiveUser) {
		Date now = new Date();
		return MessageNotification.builder()
				.sendNotificationUser(sendUser)
				.receiveNotificationUser(receiveUser)
				.url(uri)
				.content(MESSAGE)
				.type(TYPE_MESSAGE)
				.modifiedTime(now)
				.createTime(now)
				.build();
	}
}
