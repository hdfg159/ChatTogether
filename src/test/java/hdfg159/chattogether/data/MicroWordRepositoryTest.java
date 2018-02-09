package hdfg159.chattogether.data;

import hdfg159.chattogether.config.JpaConfig;
import hdfg159.chattogether.domain.MicroWord;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;
import java.util.Optional;

/**
 * Project:ChatTogether
 * Package:hdfg159.chattogether.data
 * Created by hdfg159 on 2017-11-30 10:37.
 */
@Slf4j
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {JpaConfig.class})
public class MicroWordRepositoryTest {
	@Autowired
	private MicroWordRepository microWordRepository;
	
	@Test
	public void findAllByContentContaining() throws Exception {
	}
	
	@Test
	public void save() throws Exception {
	}
	
	@Test
	public void findAll() throws Exception {
		Optional<List<MicroWord>> microWordsOptional = Optional.ofNullable(microWordRepository.findAll());
		microWordsOptional.ifPresent(microWords -> {
			for (MicroWord microWord : microWords) {
				log.info(microWord.toString());
			}
		});
	}
	
	@Test
	public void findAllByUserFriend() throws Exception {
		Page<MicroWord> microWords = microWordRepository.findAllByUserFriend("aaaaaa", new PageRequest(0, 10));
		log.info(String.valueOf(microWords.getSize()));
	}
}