package hdfg159.chattogether.data;

import hdfg159.chattogether.config.JpaConfig;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Project:ChatTogether
 * Package:hdfg159.chattogether.data
 * Created by hdfg159 on 2017-11-29 10:43.
 */
@Slf4j
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {JpaConfig.class})
public class UserRepositoryTest {
	@Autowired
	private UserRepository userRepository;
	
	@Test
	public void findAllByUsernameContaining() throws Exception {
	}
	
	@Test
	public void findByUsernameIgnoreCase() throws Exception {
		boolean aa = userRepository.findByUsernameIgnoreCase("aa").isPresent();
		log.info(String.valueOf(aa));
	}
	
	@Test
	public void findOne() throws Exception {
		log.info(userRepository.findOne((long) 2).toString());
	}
	
}