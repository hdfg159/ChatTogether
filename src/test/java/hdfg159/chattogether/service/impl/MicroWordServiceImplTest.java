package hdfg159.chattogether.service.impl;

import hdfg159.chattogether.config.JpaConfig;
import hdfg159.chattogether.config.RootConfig;
import hdfg159.chattogether.domain.MicroWord;
import hdfg159.chattogether.service.MicroWordService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Project:ChatTogether
 * Package:hdfg159.chattogether.service.impl
 * Created by hdfg159 on 2018-1-29 11:43.
 */
@Slf4j
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {RootConfig.class, JpaConfig.class})
public class MicroWordServiceImplTest {
	@Autowired
	private MicroWordService microWordService;
	
	@Test
	public void findAllByUsername() throws Exception {
		Page<MicroWord> microWords = microWordService.findAllByUsername("rrrrr", new PageRequest(1, 10));
		for (MicroWord microWord : microWords) {
			Assert.assertEquals("rrrrr", microWord.getUser().getUsername());
			log.info(microWord.getContent());
		}
	}
}
