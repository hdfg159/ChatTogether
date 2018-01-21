package hdfg159.chattogether.service.impl;

import hdfg159.chattogether.data.UserPermissionRepository;
import hdfg159.chattogether.data.UserRepository;
import hdfg159.chattogether.domain.User;
import hdfg159.chattogether.domain.UserAuthorization;
import hdfg159.chattogether.domain.UserPermission;
import hdfg159.chattogether.exception.UserNotFoundException;
import hdfg159.chattogether.service.UserPermissionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Project:ChatTogether
 * Package:hdfg159.chattogether.service.impl
 * Created by hdfg159 on 2018-1-12 18:04.
 */
@Slf4j
@Service
@Transactional
public class UserPermissionServiceImpl implements UserPermissionService {
	private final UserPermissionRepository userPermissionRepository;
	private final UserRepository userRepository;
	
	@Autowired
	public UserPermissionServiceImpl(UserPermissionRepository userPermissionRepository, UserRepository userRepository) {
		this.userPermissionRepository = userPermissionRepository;
		this.userRepository = userRepository;
	}
	
	@Override
	public List<UserPermission> findAll() {
		return userPermissionRepository.findAll();
	}
	
	@Override
	public List<UserPermission> findAllByUsernameIgnoreCase(String username) {
		User user = fetchUserByUsername(username);
		List<UserPermission> userPermissions = new ArrayList<>();
		for (UserAuthorization userAuthorization : user.getUserAuthorizations()) {
			userPermissions.add(userAuthorization.getUserPermission());
		}
		return userPermissions;
	}
	
	private User fetchUserByUsername(String username) {
		return userRepository.findByUsernameIgnoreCase(username)
				.orElseThrow(() -> new UserNotFoundException("For Username:" + username));
	}
}
