package hdfg159.chattogether.data;

import hdfg159.chattogether.config.JpaConfig;
import hdfg159.chattogether.domain.UserPermission;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static hdfg159.chattogether.constant.RoleConsts.*;

/**
 * Project:ChatTogether
 * Package:hdfg159.chattogether.data
 * Created by hdfg159 on 2017-11-25 19:03.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {JpaConfig.class})
@Slf4j
public class UserPermissionRepositoryTest {
	@Autowired
	private UserPermissionRepository userPermissionRepository;
	
	@Test
	@Transactional
	@Rollback
	public void saveUsedPermission() {
		List<UserPermission> userPermissions = new ArrayList<>();
		userPermissions.add(buildUserPermission(USER));
		userPermissions.add(buildUserPermission(ADMIN_USER));
		userPermissions.add(buildUserPermission(ADMIN_MICROWORD));
		userPermissions.add(buildUserPermission(ADMIN_COMMENT));
		userPermissionRepository.save(userPermissions);
	}
	
	private UserPermission buildUserPermission(String role) {
		return UserPermission.builder()
				.roleName(role).createTime(new Date())
				.modifiedTime(new Date())
				.build();
	}
}