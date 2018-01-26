package hdfg159.chattogether.service.impl;

import hdfg159.chattogether.data.*;
import hdfg159.chattogether.domain.*;
import hdfg159.chattogether.domain.ao.ChangePasswordAO;
import hdfg159.chattogether.exception.*;
import hdfg159.chattogether.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Optional;

import static hdfg159.chattogether.constant.BooleanStateConsts.FALSE;
import static hdfg159.chattogether.constant.BooleanStateConsts.TRUE;
import static hdfg159.chattogether.constant.RoleConsts.USER;
import static hdfg159.chattogether.constant.UserProfileConsts.*;
import static org.apache.commons.lang3.RandomStringUtils.randomAlphanumeric;

/**
 * Project:ChatTogether
 * Package:hdfg159.chattogether.service.impl
 * Created by hdfg159 on 2017-11-28 11:37.
 */
@Slf4j
@Service
@Transactional
public class UserServiceImpl implements UserService {
	private final UserRepository userRepository;
	private final UserProfileRepository userProfileRepository;
	private final UserAccountStateRepository userAccountStateRepository;
	private final UserPermissionRepository userPermissionRepository;
	private final UserAuthorizationRepository userAuthorizationRepository;
	
	private final PasswordEncoder passwordEncoder;
	
	@Autowired
	public UserServiceImpl(UserRepository userRepository, UserProfileRepository userProfileRepository, UserAccountStateRepository userAccountStateRepository, UserPermissionRepository userPermissionRepository, UserAuthorizationRepository userAuthorizationRepository, PasswordEncoder passwordEncoder) {
		this.userRepository = userRepository;
		this.userProfileRepository = userProfileRepository;
		this.userAccountStateRepository = userAccountStateRepository;
		this.userPermissionRepository = userPermissionRepository;
		this.userAuthorizationRepository = userAuthorizationRepository;
		this.passwordEncoder = passwordEncoder;
	}
	
	@Override
	public boolean save(User user) {
		userRepository.findByUsernameIgnoreCase(user.getUsername())
				.ifPresent(existedUser -> {
					throw new UserExistException("For Username:" + existedUser.getUsername());
				});
		saveUser(user);
		return true;
	}
	
	@Override
	public Page<User> findAll(Pageable pageable) {
		return userRepository.findAll(pageable);
	}
	
	@Override
	public boolean deleteById(Long id) {
		userRepository.delete(id);
		return true;
	}
	
	@Override
	public Page<User> findByUsernameContaining(String username, Pageable pageable) {
		return userRepository.findAllByUsernameContaining(username, pageable);
	}
	
	@Override
	public String resetPassword(String username) {
		String newPassword = randomAlphanumeric(5);
		changeUserPassword(username, newPassword);
		return newPassword;
	}
	
	@Override
	public boolean changePassword(ChangePasswordAO changePasswordAO) {
		validUserPassword(changePasswordAO);
		changeUserPassword(changePasswordAO.getUsername(), changePasswordAO.getNewPassword());
		return true;
	}
	
	private void validUserPassword(ChangePasswordAO changePasswordAO) {
		User user = fetchUserByUsername(changePasswordAO.getUsername());
		if (!passwordEncoder.matches(changePasswordAO.getOldPassword(), user.getPassword())) {
			throw new PasswordNotMatchException("原密码不匹配");
		}
	}
	
	private User fetchUserByUsername(String username) {
		return userRepository.findByUsernameIgnoreCase(username)
				.orElseThrow(() -> new UserNotFoundException("For Username:" + username));
	}
	
	@Override
	public User findByUsernameIgnoreCase(String username) {
		return userRepository.findByUsernameIgnoreCase(username).orElseThrow(() -> new UserNotFoundException("For Username:" + username));
	}
	
	@Override
	public boolean switchUserAccountStateById(Long id) {
		UserAccountState userAccountState = userAccountStateRepository
				.findByUser_Id(id)
				.orElseThrow(() -> new UserAccountStateNotFoundException("For userId:" + id));
		Integer isEnable = userAccountState.getIsEnabled() == 0 ? TRUE : FALSE;
		userAccountState.setIsEnabled(isEnable);
		userAccountState.setModifiedTime(new Date());
		userAccountStateRepository.save(userAccountState);
		return true;
	}
	
	private void changeUserPassword(String username, String newPassword) {
		User user = userRepository.findByUsernameIgnoreCase(username)
				.orElseThrow(() -> new UserNotFoundException("For Username:" + username));
		user.setPassword(encodePassword(newPassword));
		userRepository.save(user);
	}
	
	private void saveUser(User user) {
		Date now = new Date();
		user.setCreateTime(now);
		user.setModifiedTime(now);
		user.setPassword(encodePassword(user.getPassword()));
		User savedUser = userRepository.save(user);
		
		log.info(savedUser.toString());
		
		initUser(savedUser);
	}
	
	private String encodePassword(String rawPassword) {
		return passwordEncoder.encode(rawPassword);
	}
	
	private void initUser(User savedUser) {
		initUserAccountState(savedUser);
		initUserProfile(savedUser);
		initUserAuthorization(savedUser);
	}
	
	private void initUserAccountState(User savedUser) {
		Date now = new Date();
		UserAccountState accountState = UserAccountState.builder()
				.user(savedUser)
				.isEnabled(TRUE)
				.createTime(now)
				.modifiedTime(now)
				.build();
		userAccountStateRepository.save(accountState);
	}
	
	private void initUserProfile(User savedUser) {
		Date now = new Date();
		UserProfile userProfile = UserProfile.builder()
				.user(savedUser)
				.age(AGE)
				.birthday(new Date())
				.email(KEEP_SECRET)
				.qq(KEEP_SECRET)
				.wechat(KEEP_SECRET)
				.phoneNumber(KEEP_SECRET)
				.selfIntroduction(ABOUT)
				.sex(SEX_MAN)
				.profilePhoto(PROFILE_PHOTO_DEFAULT)
				.createTime(now)
				.modifiedTime(now)
				.build();
		userProfileRepository.save(userProfile);
	}
	
	private void initUserAuthorization(User user) {
		Date now = new Date();
		Optional<UserPermission> userRoleOptional = Optional.ofNullable(userPermissionRepository.findByRoleNameIgnoreCase(USER));
		UserPermission userRole = userRoleOptional.orElseThrow(() -> new UserPermissionNotFoundException("For Role Name:" + USER));
		UserAuthorization authorization = UserAuthorization.builder()
				.user(user)
				.userPermission(userRole)
				.createTime(now)
				.modifiedTime(now)
				.build();
		userAuthorizationRepository.save(authorization);
	}
	
}
