package hdfg159.chattogether.service.impl;

import hdfg159.chattogether.config.JpaConfig;
import hdfg159.chattogether.config.RootConfig;
import hdfg159.chattogether.domain.User;
import hdfg159.chattogether.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.annotation.Repeat;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphanumeric;

/**
 * Project:ChatTogether
 * Package:hdfg159.chattogether.service.impl
 * Created by hdfg159 on 2017-11-28 20:42.
 */
@Slf4j
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {RootConfig.class, JpaConfig.class})
public class UserServiceImplTest {
	@Autowired
	private UserService userService;
	
	@Test
	@Transactional
	@Rollback
	@Repeat(value = 30)
	public void save() throws Exception {
		User user = User.builder()
				.username(randomAlphanumeric(16))
				.password(randomAlphanumeric(16))
				.build();
		Assert.assertTrue(userService.save(user));
	}
	
	@Test
	public void saveAdmin() throws Exception {
		User user = User.builder()
				.username("admin")
				.password("admin")
				.build();
		Assert.assertTrue(userService.save(user));
	}
	
	@Test
	public void findAll() throws Exception {
	}
	
	@Test
	@Transactional
	@Rollback
	public void deleteById() throws Exception {
		Assert.assertTrue(userService.deleteById((long) 2));
	}
	
	@Test
	public void findByUsernameContaining() throws Exception {
		Optional<Page<User>> usersOptional = Optional.ofNullable(userService.findByUsernameContaining("H", new PageRequest(0, 10)));
		usersOptional.ifPresent(users -> {
			for (User user : users.getContent()) {
				log.info(user.toString());
				log.info(user.getUserProfile().toString());
				log.info(user.getUserAccountState().toString());
			}
		});
		usersOptional.orElseThrow(() -> new RuntimeException("查询为空"));
	}
	
}