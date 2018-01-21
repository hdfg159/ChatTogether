package hdfg159.chattogether.service.impl;

import hdfg159.chattogether.data.MicroWordCommentRepository;
import hdfg159.chattogether.data.MicroWordRepository;
import hdfg159.chattogether.data.UserRepository;
import hdfg159.chattogether.domain.MicroWord;
import hdfg159.chattogether.domain.MicroWordComment;
import hdfg159.chattogether.domain.User;
import hdfg159.chattogether.domain.vo.MicroWordDetailVO;
import hdfg159.chattogether.exception.MicroWordNotFoundException;
import hdfg159.chattogether.exception.UserNotFoundException;
import hdfg159.chattogether.service.MicroWordService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Optional;

/**
 * Project:ChatTogether
 * Package:hdfg159.chattogether.service.impl
 * Created by hdfg159 on 2017-11-29 22:52.
 */
@Slf4j
@Service
@Transactional
public class MicroWordServiceImpl implements MicroWordService {
	private final MicroWordRepository microWordRepository;
	private final UserRepository userRepository;
	private final MicroWordCommentRepository microWordCommentRepository;
	
	@Autowired
	public MicroWordServiceImpl(MicroWordRepository microWordRepository, UserRepository userRepository, MicroWordCommentRepository microWordCommentRepository) {
		this.microWordRepository = microWordRepository;
		this.userRepository = userRepository;
		this.microWordCommentRepository = microWordCommentRepository;
	}
	
	@Override
	public Page<MicroWord> findAll(Pageable pageable) {
		return microWordRepository.findAll(pageable);
	}
	
	@Override
	public boolean deleteById(Long id) {
		microWordRepository.delete(id);
		return true;
	}
	
	@Override
	public boolean deleteBatch(Long... ids) {
		for (Long id : ids) {
			microWordRepository.delete(id);
		}
		return true;
	}
	
	@Override
	public boolean save(String username, MicroWord microWord) {
		User user = userRepository.findByUsernameIgnoreCase(username)
				.orElseThrow(() -> new UserNotFoundException("For Username:" + username));
		MicroWord saveMicroWord = MicroWord.builder()
				.user(user)
				.content(microWord.getContent())
				.createTime(new Date())
				.modifiedTime(new Date())
				.build();
		microWordRepository.save(saveMicroWord);
		//TODO:保存MicroWord Attachment
		return true;
	}
	
	@Override
	public Page<MicroWord> findAllByContentContaining(String content, Pageable pageable) {
		return microWordRepository.findAllByContentContaining(content, pageable);
	}
	
	@Override
	public MicroWordDetailVO findMicroWordDetailById(Long id, Pageable pageable) {
		Optional<MicroWord> microWordOptional = Optional.ofNullable(microWordRepository.findOne(id));
		MicroWord microWord = microWordOptional.orElseThrow(() -> new MicroWordNotFoundException("For Id:" + id));
		Page<MicroWordComment> microWordComments = microWordCommentRepository.findAllByMicroWord(microWord, pageable);
		return new MicroWordDetailVO(microWord, microWordComments);
	}
}
