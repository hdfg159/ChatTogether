package hdfg159.chattogether.data;

import hdfg159.chattogether.config.JpaConfig;
import hdfg159.chattogether.domain.MicroWord;
import hdfg159.chattogether.domain.MicroWordAgree;
import hdfg159.chattogether.domain.User;
import hdfg159.chattogether.exception.MicroWordAgreeExistException;
import hdfg159.chattogether.exception.MicroWordNotFoundException;
import hdfg159.chattogether.exception.UserNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Optional;

/**
 * Project:ChatTogether
 * Package:hdfg159.chattogether.data
 * Created by hdfg159 on 2017-12-4 10:22.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {JpaConfig.class})
@Slf4j
public class MicroWordAgreeRepositoryTest {
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private MicroWordRepository microWordRepository;
	@Autowired
	private MicroWordAgreeRepository microWordAgreeRepository;
	
	@Test
	
	public void findByMicroWordAndUser() throws Exception {
//		String username = RandomStringUtils.randomAlphanumeric(5);
		String username = "rrrrrs";
//		Long microWordId = Long.valueOf(RandomStringUtils.randomNumeric(5));
		Long microWordId = Long.valueOf("5");
		Optional<User> userOptional = userRepository.findByUsernameIgnoreCase(username);
		User findUser = userOptional.orElseThrow(() -> new UserNotFoundException("For Username:" + username));
		Optional<MicroWord> microWordOptional = Optional.ofNullable(microWordRepository.findOne(microWordId));
		MicroWord findMicroWord = microWordOptional.orElseThrow(() -> new MicroWordNotFoundException("For Id:" + microWordId));
		Optional<MicroWordAgree> microWordAgreeOptional = Optional
				.ofNullable(microWordAgreeRepository.findByMicroWordAndUser(findMicroWord, findUser));
		microWordAgreeOptional.ifPresent(microWordAgrees -> {
			throw new MicroWordAgreeExistException();
		});
	}
}