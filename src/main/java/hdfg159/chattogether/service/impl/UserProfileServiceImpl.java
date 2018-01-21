package hdfg159.chattogether.service.impl;

import hdfg159.chattogether.data.UserProfileRepository;
import hdfg159.chattogether.domain.UserProfile;
import hdfg159.chattogether.domain.vo.UserProfileFormVO;
import hdfg159.chattogether.exception.UserProfileNotFoundException;
import hdfg159.chattogether.service.UserProfileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * Project:ChatTogether
 * Package:hdfg159.chattogether.service.impl
 * Created by hdfg159 on 2018-1-12 11:25.
 */
@Slf4j
@Service
@Transactional
public class UserProfileServiceImpl implements UserProfileService {
	private final UserProfileRepository userProfileRepository;
	
	@Autowired
	public UserProfileServiceImpl(UserProfileRepository userProfileRepository) {this.userProfileRepository = userProfileRepository;}
	
	@Override
	public UserProfile findByUsername(String username) {
		return userProfileRepository.findByUser_Username(username).orElseThrow(() -> new UserProfileNotFoundException("For Username:" + username));
	}
	
	@Override
	public UserProfile save(UserProfileFormVO userProfileFormVO) {
		Long userProfileId = userProfileFormVO.getId();
		UserProfile userProfile = userProfileRepository.findById(userProfileId).orElseThrow(() -> new UserProfileNotFoundException("For Id:" + userProfileId));
		BeanUtils.copyProperties(userProfileFormVO, userProfile);
		userProfile.setModifiedTime(new Date());
		return userProfileRepository.save(userProfile);
	}
	
	@Override
	public UserProfile findById(Long id) {
		return userProfileRepository.findById(id).orElseThrow(() -> new UserProfileNotFoundException("For Id:" + id));
	}
}
