package hdfg159.chattogether.service.impl;

import hdfg159.chattogether.data.MessageNotificationRepository;
import hdfg159.chattogether.data.MicroWordCommentRepository;
import hdfg159.chattogether.data.MicroWordRepository;
import hdfg159.chattogether.data.UserRepository;
import hdfg159.chattogether.domain.MessageNotification;
import hdfg159.chattogether.domain.MicroWord;
import hdfg159.chattogether.domain.MicroWordComment;
import hdfg159.chattogether.domain.User;
import hdfg159.chattogether.exception.MicroWordNotFoundException;
import hdfg159.chattogether.exception.UserNotFoundException;
import hdfg159.chattogether.service.MicroWordCommentService;
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
 * Created by hdfg159 on 2017-12-4 17:44.
 */
@Slf4j
@Service
@Transactional
public class MicroWordCommentServiceImpl implements MicroWordCommentService {
	private final MicroWordCommentRepository microWordCommentRepository;
	private final MessageNotificationRepository messageNotificationRepository;
	private final MicroWordRepository microWordRepository;
	private final UserRepository userRepository;
	
	@Autowired
	public MicroWordCommentServiceImpl(MicroWordCommentRepository microWordCommentRepository, MessageNotificationRepository messageNotificationRepository, MicroWordRepository microWordRepository, UserRepository userRepository) {
		this.microWordCommentRepository = microWordCommentRepository;
		this.messageNotificationRepository = messageNotificationRepository;
		this.microWordRepository = microWordRepository;
		this.userRepository = userRepository;
	}
	
	@Override
	public boolean deleteById(Long id) {
		microWordCommentRepository.delete(id);
		return true;
	}
	
	@Override
	public boolean save(String username, Long microWordId, String content, String uri) {
		MicroWord microWord = fetchMicroWordById(microWordId);
		User user = fetchUserByUsername(username);
		MicroWordComment microWordComment = buildSaveMicroWordComment(content, microWord, user);
		microWordCommentRepository.save(microWordComment);
		
		MessageNotification messageNotification = buildSaveMessageNotification(uri, microWord, user);
		messageNotificationRepository.save(messageNotification);
		return true;
	}
	
	private MicroWord fetchMicroWordById(Long microWordId) {
		Optional<MicroWord> microWordOptional = Optional.ofNullable(microWordRepository.findOne(microWordId));
		return microWordOptional.orElseThrow(() -> new MicroWordNotFoundException("For Id:" + microWordId));
	}
	
	private User fetchUserByUsername(String username) {
		return userRepository.findByUsernameIgnoreCase(username)
				.orElseThrow(() -> new UserNotFoundException("For Username:" + username));
	}
	
	private MicroWordComment buildSaveMicroWordComment(String content, MicroWord microWord, User user) {
		Date now = new Date();
		return MicroWordComment.builder()
				.commentUser(user)
				.content(content)
				.microWord(microWord)
				.createTime(now)
				.modifiedTime(now)
				.build();
	}
	
	private MessageNotification buildSaveMessageNotification(String uri, MicroWord microWord, User user) {
		Date now = new Date();
		return MessageNotification.builder()
				.type(TYPE_MICROWORD)
				.url(uri)
				.receiveNotificationUser(microWord.getUser())
				.sendNotificationUser(user)
				.createTime(now)
				.modifiedTime(now)
				.content(COMMENT_YOUR_MICROWORD)
				.isRead(IS_NOT_READ)
				.build();
	}
}
