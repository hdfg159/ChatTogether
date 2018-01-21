package hdfg159.chattogether.service.impl;

import hdfg159.chattogether.data.MessageNotificationRepository;
import hdfg159.chattogether.data.MicroWordAgreeRepository;
import hdfg159.chattogether.data.MicroWordRepository;
import hdfg159.chattogether.data.UserRepository;
import hdfg159.chattogether.domain.MessageNotification;
import hdfg159.chattogether.domain.MicroWord;
import hdfg159.chattogether.domain.MicroWordAgree;
import hdfg159.chattogether.domain.User;
import hdfg159.chattogether.exception.MicroWordAgreeExistException;
import hdfg159.chattogether.exception.MicroWordNotFoundException;
import hdfg159.chattogether.exception.UserNotFoundException;
import hdfg159.chattogether.service.MicroWordAgreeService;
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
 * Created by hdfg159 on 2017-12-4 09:55.
 */
@Slf4j
@Service
@Transactional
public class MicroWordAgreeServiceImpl implements MicroWordAgreeService {
	private final UserRepository userRepository;
	private final MicroWordAgreeRepository microWordAgreeRepository;
	private final MicroWordRepository microWordRepository;
	private final MessageNotificationRepository messageNotificationRepository;
	
	@Autowired
	public MicroWordAgreeServiceImpl(UserRepository userRepository, MicroWordAgreeRepository microWordAgreeRepository, MicroWordRepository microWordRepository, MessageNotificationRepository messageNotificationRepository) {
		this.userRepository = userRepository;
		this.microWordAgreeRepository = microWordAgreeRepository;
		this.microWordRepository = microWordRepository;
		this.messageNotificationRepository = messageNotificationRepository;
	}
	
	@Override
	public boolean save(String username, Long microWordId, String uri) {
		User findUser = fetchUserByUsername(username);
		MicroWord findMicroWord = fetchMicroWordById(microWordId);
		checkMicroWordAgreeExist(findUser, findMicroWord);
		
		//save microwordagree
		MicroWordAgree saveMicroWordAgree = microWordAgreeRepository.save(buildMicroWordAgree(findUser, findMicroWord));
		
		//save agree notification
		MessageNotification agreeNotification = buildAgreeNotification(uri, saveMicroWordAgree);
		messageNotificationRepository.save(agreeNotification);
		return true;
	}
	
	private User fetchUserByUsername(String username) {
		return userRepository.findByUsernameIgnoreCase(username).orElseThrow(() -> new UserNotFoundException("For Username:" + username));
	}
	
	private MicroWord fetchMicroWordById(Long microWordId) {
		Optional<MicroWord> microWordOptional = Optional.ofNullable(microWordRepository.findOne(microWordId));
		return microWordOptional.orElseThrow(() -> new MicroWordNotFoundException("For Id:" + microWordId));
	}
	
	private void checkMicroWordAgreeExist(User findUser, MicroWord findMicroWord) {
		Optional<MicroWordAgree> microWordAgreeOptional = Optional.ofNullable(microWordAgreeRepository.findByMicroWordAndUser(findMicroWord, findUser));
		microWordAgreeOptional.ifPresent(microWordAgree -> {
			throw new MicroWordAgreeExistException("For MicroWordId:" + microWordAgree.getMicroWord().getId() + ",For Username:" + microWordAgree.getUser().getId());
		});
	}
	
	private MicroWordAgree buildMicroWordAgree(User findUser, MicroWord findMicroWord) {
		Date now = new Date();
		return MicroWordAgree.builder().user(findUser).microWord(findMicroWord).createTime(now).modifiedTime(now).build();
	}
	
	private MessageNotification buildAgreeNotification(String uri, MicroWordAgree saveMicroWordAgree) {
		Date now = new Date();
		return MessageNotification.builder()
				.type(TYPE_MICROWORD)
				.isRead(IS_NOT_READ)
				.content(AGREE_YOUR＿MICROWORD)
				.url(uri)
				.sendNotificationUser(saveMicroWordAgree.getUser())
				.receiveNotificationUser(saveMicroWordAgree.getMicroWord().getUser())
				.createTime(now)
				.modifiedTime(now)
				.build();
	}
}
