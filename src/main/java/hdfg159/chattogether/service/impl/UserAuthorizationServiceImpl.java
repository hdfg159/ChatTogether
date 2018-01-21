package hdfg159.chattogether.service.impl;

import hdfg159.chattogether.data.UserAuthorizationRepository;
import hdfg159.chattogether.data.UserPermissionRepository;
import hdfg159.chattogether.data.UserRepository;
import hdfg159.chattogether.domain.User;
import hdfg159.chattogether.domain.UserAuthorization;
import hdfg159.chattogether.domain.vo.UserAuthorizationFormVO;
import hdfg159.chattogether.exception.UserNotFoundException;
import hdfg159.chattogether.service.UserAuthorizationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Project:ChatTogether
 * Package:hdfg159.chattogether.service.impl
 * Created by hdfg159 on 2018-1-12 22:33.
 */
@Slf4j
@Service
@Transactional
public class UserAuthorizationServiceImpl implements UserAuthorizationService {
	private final UserAuthorizationRepository userAuthorizationRepository;
	private final UserRepository userRepository;
	private final UserPermissionRepository userPermissionRepository;
	
	@Autowired
	public UserAuthorizationServiceImpl(UserAuthorizationRepository userAuthorizationRepository, UserRepository userRepository, UserPermissionRepository userPermissionRepository) {
		this.userAuthorizationRepository = userAuthorizationRepository;
		this.userRepository = userRepository;
		this.userPermissionRepository = userPermissionRepository;
	}
	
	@Override
	public boolean save(UserAuthorizationFormVO userAuthorizationFormVO) {
		User user = fetchUserByUsername(userAuthorizationFormVO.getUsername());
		
		userAuthorizationRepository.deleteAllByUserId(user.getId());
		
		List<UserAuthorization> userAuthorizations = new ArrayList<>();
		Date now = new Date();
		for (Long userPermission : userAuthorizationFormVO.getPermissions()) {
			UserAuthorization userAuthorization = UserAuthorization.builder()
					.user(user)
					.userPermission(userPermissionRepository.findOne(userPermission))
					.createTime(now)
					.modifiedTime(now)
					.build();
			userAuthorizations.add(userAuthorization);
		}
		userAuthorizationRepository.save(userAuthorizations);
		return true;
	}
	
	private User fetchUserByUsername(String username) {
		return userRepository.findByUsernameIgnoreCase(username)
				.orElseThrow(() -> new UserNotFoundException("For Username:" + username));
	}
}
