package hdfg159.chattogether.service.impl;

import hdfg159.chattogether.data.UserProfileRepository;
import hdfg159.chattogether.domain.UserProfile;
import hdfg159.chattogether.domain.ao.ProfilePhotoAO;
import hdfg159.chattogether.domain.vo.UserProfileFormVO;
import hdfg159.chattogether.exception.SaveProfilePhotoException;
import hdfg159.chattogether.exception.UserProfileNotFoundException;
import hdfg159.chattogether.service.UserProfileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Date;

import static hdfg159.chattogether.constant.UserProfileConsts.PROFILE_PHOTO_DEFAULT_PATH;
import static hdfg159.chattogether.util.UUIDUtils.uuid;
import static org.apache.commons.lang3.StringUtils.isEmpty;
import static org.apache.commons.lang3.StringUtils.substringAfter;
import static org.apache.commons.lang3.exception.ExceptionUtils.getMessage;

/**
 * Project:ChatTogether
 * Package:hdfg159.chattogether.service.impl
 * Created by hdfg159 on 2018-1-12 11:25.
 */
@Slf4j
@Service
@Transactional
@PropertySource(value = {"classpath:appconfig.properties"})
public class UserProfileServiceImpl implements UserProfileService {
	private final UserProfileRepository userProfileRepository;
	@Value("${userProfile.profilePhoto.path}")
	private String saveProfilePhotoPath;
	
	@Autowired
	public UserProfileServiceImpl(UserProfileRepository userProfileRepository) {this.userProfileRepository = userProfileRepository;}
	
	@Override
	public UserProfile findByUsername(String username) {
		return userProfileRepository
				.findByUser_Username(username)
				.orElseThrow(() -> new UserProfileNotFoundException("For Username:" + username));
	}
	
	@Override
	public UserProfile save(UserProfileFormVO userProfileFormVO) {
		Long userProfileId = userProfileFormVO.getId();
		UserProfile userProfile = userProfileRepository
				.findById(userProfileId)
				.orElseThrow(() -> new UserProfileNotFoundException("For Id:" + userProfileId));
		BeanUtils.copyProperties(userProfileFormVO, userProfile);
		userProfile.setModifiedTime(new Date());
		return userProfileRepository.save(userProfile);
	}
	
	@Override
	public UserProfile findById(Long id) {
		return userProfileRepository
				.findById(id)
				.orElseThrow(() -> new UserProfileNotFoundException("For Id:" + id));
	}
	
	@Override
	public boolean saveProfilePhoto(ProfilePhotoAO profilePhotoAO) {
		UserProfile userProfile = userProfileRepository
				.findByUser_Username(profilePhotoAO.getUsername())
				.orElseThrow(() -> new UserProfileNotFoundException("For Username:" + profilePhotoAO.getUsername()));
		userProfile.setModifiedTime(new Date());
		
		MultipartFile profilePhoto = profilePhotoAO.getProfilePhoto();
		String profilePhotoPath = isEmpty(saveProfilePhotoPath) ? PROFILE_PHOTO_DEFAULT_PATH : saveProfilePhotoPath;
		String saveFilePath = profilePhotoPath + getFilename(profilePhoto);
		userProfile.setProfilePhoto(saveFilePath);
		userProfileRepository.save(userProfile);
		
		saveProfilePhotoFile(profilePhotoAO.getWebRootPath(), profilePhoto, saveFilePath);
		return true;
	}
	
	private String getFilename(MultipartFile profilePhoto) {
		return uuid() + "." + substringAfter(profilePhoto.getContentType(), "/");
	}
	
	private void saveProfilePhotoFile(String webRootPath, MultipartFile profilePhoto, String saveFilePath) {
		File dest = new File(webRootPath + File.separator + saveFilePath);
		if (!dest.getParentFile().exists() && !dest.getParentFile().mkdirs()) {
			log.error("创建自定义头像保存路径出现错误:{}", dest.getParentFile().getAbsolutePath());
			throw new SaveProfilePhotoException("保存自定义头像失败!");
		}
		try {
			profilePhoto.transferTo(dest);
		} catch (IOException e) {
			log.error("保存自定义头像出现IO错误:{}", getMessage(e));
			throw new SaveProfilePhotoException("保存自定义头像失败!");
		}
	}
}
