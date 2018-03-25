package hdfg159.chattogether.data;

import hdfg159.chattogether.config.JpaConfig;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

/**
 * Project:ChatTogether
 * Package:hdfg159.chattogether.data
 * Created by hdfg159 on 18-3-25 下午12:14.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {JpaConfig.class})
@Slf4j
public class MessageNotificationRepositoryTest {
	@Autowired
	private MessageNotificationRepository messageNotificationRepository;
	
	@Test
	@Transactional
	@Rollback
	public void cleanAllNotifications() throws Exception {
		messageNotificationRepository.cleanAllMessageNotificationsByUserId(Long.valueOf(1));
	}
}
