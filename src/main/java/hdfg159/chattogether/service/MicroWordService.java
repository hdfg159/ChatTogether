package hdfg159.chattogether.service;

import hdfg159.chattogether.domain.MicroWord;
import hdfg159.chattogether.domain.vo.MicroWordDetailVO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Project:ChatTogether
 * Package:hdfg159.chattogether.service
 * Created by hdfg159 on 2017-11-29 19:54.
 */
public interface MicroWordService {
	/**
	 * 分页查找所有的微说
	 *
	 * @param pageable
	 * 		分页参数
	 *
	 * @return {@code Page<MicroWord>} 某页的微说
	 */
	Page<MicroWord> findAll(Pageable pageable);
	
	/**
	 * 通过id删除微说
	 *
	 * @param id
	 * 		主键id
	 *
	 * @return boolean 是否成功
	 */
	boolean deleteById(Long id);
	
	/**
	 * 批量删除微说
	 *
	 * @param ids
	 * 		可变长参数id主键
	 *
	 * @return boolean 是否成功
	 */
	boolean deleteBatch(Long... ids);
	
	/**
	 * 保存微说
	 *
	 * @param username
	 * 		发表微说的用户名
	 * @param microWord
	 * 		微说
	 *
	 * @return boolean 是否成功
	 */
	boolean save(String username, MicroWord microWord);
	
	/**
	 * 根据微说内容模糊查找
	 *
	 * @param content
	 * 		内容关键字
	 * @param pageable
	 * 		分页参数
	 *
	 * @return {@code Page<MicroWord>} 返回某页微说内容列
	 */
	Page<MicroWord> findAllByContentContaining(String content, Pageable pageable);
	
	/**
	 * 根据微说ID查找微说及分页后的评论
	 *
	 * @param id
	 * 		微说ID
	 * @param pageable
	 * 		分页参数
	 *
	 * @return MicroWordDetailVO
	 */
	MicroWordDetailVO findMicroWordDetailById(Long id, Pageable pageable);
}
