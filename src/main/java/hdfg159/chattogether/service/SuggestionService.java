package hdfg159.chattogether.service;

import hdfg159.chattogether.domain.Suggestion;
import hdfg159.chattogether.domain.vo.SuggestionFormVO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Project:ChatTogether
 * Package:hdfg159.chattogether.service
 * Created by hdfg159 on 18-3-25 下午4:40.
 */
public interface SuggestionService {
	/**
	 * 查询所有建议
	 *
	 * @param pageable
	 * 		分页参数
	 *
	 * @return {@code Page<Suggestion>}
	 */
	Page<Suggestion> findAll(Pageable pageable);
	
	/**
	 * 保存建议
	 *
	 * @param suggestionFormVO
	 * 		建议视图表单
	 * @param username
	 * 		用户名
	 *
	 * @return boolean
	 */
	boolean save(SuggestionFormVO suggestionFormVO, String username);
	
	/**
	 * 根据id删除建议
	 *
	 * @param id
	 * 		建议的id
	 *
	 * @return boolean
	 */
	boolean deleteById(Long id);
}
