package hdfg159.chattogether.service.impl;

import hdfg159.chattogether.data.MessageNotificationRepository;
import hdfg159.chattogether.data.MicroWordCommentReplyRepository;
import hdfg159.chattogether.data.MicroWordCommentRepository;
import hdfg159.chattogether.data.UserRepository;
import hdfg159.chattogether.domain.MessageNotification;
import hdfg159.chattogether.domain.MicroWordComment;
import hdfg159.chattogether.domain.MicroWordCommentReply;
import hdfg159.chattogether.domain.User;
import hdfg159.chattogether.exception.MicroWordCommentNotFoundException;
import hdfg159.chattogether.exception.UserNotFoundException;
import hdfg159.chattogether.service.MicroWordCommentReplyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Optional;

import static hdfg159.chattogether.constant.MessageNotificationConsts.*;

/**
 * Project:ChatTogether
 * Package:hdfg159.chattogether.service.impl
 * Created by hdfg159 on 2017-12-5 15:27.
 */
@Slf4j
@Service
@Transactional
public class MicroWordCommentReplyServiceImpl implements MicroWordCommentReplyService {
	private final UserRepository userRepository;
	private final MicroWordCommentReplyRepository microWordCommentReplyRepository;
	private final MicroWordCommentRepository microWordCommentRepository;
	private final MessageNotificationRepository messageNotificationRepository;
	
	@Autowired
	public MicroWordCommentReplyServiceImpl(MicroWordCommentRepository microWordCommentRepository, MicroWordCommentReplyRepository microWordCommentReplyRepository, UserRepository userRepository, MessageNotificationRepository messageNotificationRepository) {
		this.microWordCommentRepository = microWordCommentRepository;
		this.microWordCommentReplyRepository = microWordCommentReplyRepository;
		this.userRepository = userRepository;
		this.messageNotificationRepository = messageNotificationRepository;
	}
	
	@Override
	public boolean save(Long microwordCommentId, String replyUserName, Long repliedUserId, String content, String url) {
		MicroWordComment microWordComment = fetchMicroWordCommentById(microwordCommentId);
		User replyUser = fetchUserByUsername(replyUserName);
		User repliedUser = fetchUserById(repliedUserId);
		
		MicroWordCommentReply microWordCommentReply = buildSaveMicroWordCommentReply(content, microWordComment, replyUser, repliedUser);
		microWordCommentReplyRepository.save(microWordCommentReply);
		
		messageNotificationRepository.save(buildSaveMessageNotification(url, repliedUser, replyUser));
		return true;
	}
	
	@Override
	public boolean deleteById(Long id) {
		microWordCommentReplyRepository.delete(id);
		return true;
	}
	
	private MicroWordComment fetchMicroWordCommentById(Long microwordCommentId) {
		Optional<MicroWordComment> microWordCommentOptional = Optional.ofNullable(microWordCommentRepository.findOne(microwordCommentId));
		return microWordCommentOptional.orElseThrow(() -> new MicroWordCommentNotFoundException("For Id:" + microwordCommentId));
	}
	
	private User fetchUserByUsername(String username) {
		return userRepository.findByUsernameIgnoreCase(username)
				.orElseThrow(() -> new UserNotFoundException("For Username:" + username));
	}
	
	private User fetchUserById(Long id) {
		Optional<User> userOptional = Optional.ofNullable(userRepository.findOne(id));
		return userOptional.orElseThrow(() -> new UserNotFoundException("For Id:" + id));
	}
	
	private MicroWordCommentReply buildSaveMicroWordCommentReply(String content, MicroWordComment microWordComment, User replyUser, User repliedUser) {
		Date now = new Date();
		return MicroWordCommentReply.builder()
				.microWordComment(microWordComment)
				.repliedUser(repliedUser)
				.replyUser(replyUser)
				.createTime(now)
				.modifiedTime(now)
				.content(content)
				.build();
	}
	
	private MessageNotification buildSaveMessageNotification(String uri, User receiveUser, User sendUser) {
		Date now = new Date();
		return MessageNotification.builder()
				.type(TYPE_MICROWORD)
				.url(uri)
				.receiveNotificationUser(receiveUser)
				.sendNotificationUser(sendUser)
				.createTime(now)
				.modifiedTime(now)
				.content(COMMENT_YOUR_MICROWORD)
				.isRead(IS_NOT_READ)
				.build();
	}
}
