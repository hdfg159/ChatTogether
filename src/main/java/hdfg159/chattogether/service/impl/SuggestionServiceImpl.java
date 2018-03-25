package hdfg159.chattogether.service.impl;

import hdfg159.chattogether.data.SuggestionRepository;
import hdfg159.chattogether.data.UserRepository;
import hdfg159.chattogether.domain.Suggestion;
import hdfg159.chattogether.domain.User;
import hdfg159.chattogether.domain.vo.SuggestionFormVO;
import hdfg159.chattogether.exception.UserNotFoundException;
import hdfg159.chattogether.service.SuggestionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * Project:ChatTogether
 * Package:hdfg159.chattogether.service.impl
 * Created by hdfg159 on 18-3-25 下午4:42.
 */
@Slf4j
@Transactional
@Service
public class SuggestionServiceImpl implements SuggestionService {
	private final SuggestionRepository suggestionRepository;
	private final UserRepository userRepository;
	
	@Autowired
	public SuggestionServiceImpl(SuggestionRepository suggestionRepository, UserRepository userRepository) {
		this.suggestionRepository = suggestionRepository;
		this.userRepository = userRepository;
	}
	
	@Override
	public Page<Suggestion> findAll(Pageable pageable) {
		return suggestionRepository.findAll(pageable);
	}
	
	@Override
	public boolean save(SuggestionFormVO suggestionFormVO, String username) {
		User user = fetchUserByUsername(username);
		Suggestion suggestion = buildSuggestion(suggestionFormVO, user);
		
		suggestionRepository.save(suggestion);
		return true;
	}
	
	@Override
	public boolean deleteById(Long id) {
		suggestionRepository.delete(id);
		return true;
	}
	
	private Suggestion buildSuggestion(SuggestionFormVO suggestionFormVO, User user) {
		Date now = new Date();
		return Suggestion.builder()
				.user(user)
				.type(suggestionFormVO.getType())
				.title(suggestionFormVO.getTitle())
				.content(suggestionFormVO.getContent())
				.createTime(now)
				.modifiedTime(now)
				.build();
	}
	
	private User fetchUserByUsername(String username) {
		return userRepository.findByUsernameIgnoreCase(username)
				.orElseThrow(() -> new UserNotFoundException("For Username:" + username));
	}
}
