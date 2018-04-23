package hdfg159.chattogether.service.impl;

import hdfg159.chattogether.config.JpaConfig;
import hdfg159.chattogether.config.RootConfig;
import hdfg159.chattogether.domain.vo.UserAuthorizationFormVO;
import hdfg159.chattogether.service.UserAuthorizationService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Project:ChatTogether
 * Package:hdfg159.chattogether.service.impl
 * Created by hdfg159 on 18-3-28 下午7:10.
 */
@Slf4j
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {RootConfig.class, JpaConfig.class})
public class UserAuthorizationServiceImplTest {
	@Autowired
	private UserAuthorizationService userAuthorizationService;
	
	@Test
	public void authorize() {
		Long[] permissionIds = new Long[]{(long) 1, (long) 2, (long) 3, (long) 4, (long) 5};
		userAuthorizationService.save(UserAuthorizationFormVO.builder()
				.username("admin")
				.permissions(permissionIds).build());
	}
}
