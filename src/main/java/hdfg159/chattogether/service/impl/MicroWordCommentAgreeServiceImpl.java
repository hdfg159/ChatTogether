package hdfg159.chattogether.service.impl;

import hdfg159.chattogether.data.MessageNotificationRepository;
import hdfg159.chattogether.data.MicroWordCommentAgreeRepository;
import hdfg159.chattogether.data.MicroWordCommentRepository;
import hdfg159.chattogether.data.UserRepository;
import hdfg159.chattogether.domain.MessageNotification;
import hdfg159.chattogether.domain.MicroWordComment;
import hdfg159.chattogether.domain.MicroWordCommentAgree;
import hdfg159.chattogether.domain.User;
import hdfg159.chattogether.exception.MicroWordCommentAgreeExistException;
import hdfg159.chattogether.exception.MicroWordCommentNotFoundException;
import hdfg159.chattogether.exception.UserNotFoundException;
import hdfg159.chattogether.service.MicroWordCommentAgreeService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Optional;

import static hdfg159.chattogether.constant.MessageNotificationConsts.*;

/**
 * Project:ChatTogether
 * Package:hdfg159.chattogether.service.impl
 * Created by hdfg159 on 18-3-13 下午7:56.
 */
@Slf4j
@Service
@Transactional
public class MicroWordCommentAgreeServiceImpl implements MicroWordCommentAgreeService {
	private final UserRepository userRepository;
	private final MicroWordCommentRepository microWordCommentRepository;
	private final MicroWordCommentAgreeRepository microWordCommentAgreeRepository;
	private final MessageNotificationRepository messageNotificationRepository;
	
	@Autowired
	public MicroWordCommentAgreeServiceImpl(UserRepository userRepository, MicroWordCommentRepository microWordCommentRepository, MicroWordCommentAgreeRepository microWordCommentAgreeRepository, MessageNotificationRepository messageNotificationRepository) {
		this.userRepository = userRepository;
		this.microWordCommentRepository = microWordCommentRepository;
		this.microWordCommentAgreeRepository = microWordCommentAgreeRepository;
		this.messageNotificationRepository = messageNotificationRepository;
	}
	
	@Override
	public boolean save(String username, Long microWordCommentId, String uri) {
		checkMicroWordCommentAgreeExist(username, microWordCommentId);
		User user = fetchUserByUsername(username);
		MicroWordComment microWordComment = fetchMicroWordCommentById(microWordCommentId);
		
		MicroWordCommentAgree microWordCommentAgree = buildMicroWordCommentAgree(user, microWordComment);
		microWordCommentAgreeRepository.save(microWordCommentAgree);
		
		//save agree notification
		if (StringUtils.equals(username, microWordComment.getCommentUser().getUsername())) {
			return true;
		}
		MessageNotification agreeNotification = buildCommentAgreeNotification(uri, microWordCommentAgree);
		messageNotificationRepository.save(agreeNotification);
		
		return false;
	}
	
	private MicroWordCommentAgree buildMicroWordCommentAgree(User user, MicroWordComment microWordComment) {
		Date now = new Date();
		return MicroWordCommentAgree.builder()
				.user(user)
				.microWordComment(microWordComment)
				.createTime(now)
				.modifiedTime(now)
				.build();
	}
	
	private void checkMicroWordCommentAgreeExist(String username, Long microWordCommentId) {
		Optional<MicroWordCommentAgree> microWordCommentAgreeOptional = microWordCommentAgreeRepository.findByUser_UsernameAndMicroWordComment_Id(username, microWordCommentId);
		microWordCommentAgreeOptional.ifPresent(microWordCommentAgree -> {
			throw new MicroWordCommentAgreeExistException("For MicroWordCommentId:" + microWordCommentId + ",For Username:" + username);
		});
	}
	
	private User fetchUserByUsername(String username) {
		return userRepository.findByUsernameIgnoreCase(username).orElseThrow(() -> new UserNotFoundException("For Username:" + username));
	}
	
	private MicroWordComment fetchMicroWordCommentById(Long microwordCommentId) {
		Optional<MicroWordComment> microWordCommentOptional = Optional.ofNullable(microWordCommentRepository.findOne(microwordCommentId));
		return microWordCommentOptional.orElseThrow(() -> new MicroWordCommentNotFoundException("For Id:" + microwordCommentId));
	}
	
	private MessageNotification buildCommentAgreeNotification(String uri, MicroWordCommentAgree microWordCommentAgree) {
		Date now = new Date();
		return MessageNotification.builder()
				.type(TYPE_MICROWORD)
				.isRead(IS_NOT_READ)
				.content(AGREE_YOUR＿MICROWORD_COMMENT)
				.url(uri)
				.sendNotificationUser(microWordCommentAgree.getUser())
				.receiveNotificationUser(microWordCommentAgree.getMicroWordComment().getCommentUser())
				.createTime(now)
				.modifiedTime(now)
				.build();
	}
}
