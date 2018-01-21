package hdfg159.chattogether.data;

import hdfg159.chattogether.domain.MicroWord;
import hdfg159.chattogether.domain.MicroWordComment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Project:ChatTogether
 * Package:hdfg159.chattogether.data
 * Created by hdfg159 on 2017-11-28 11:46.
 */
public interface MicroWordCommentRepository extends JpaRepository<MicroWordComment, Long> {
	/**
	 * 根据微说查找所有一级评论
	 *
	 * @param pageable
	 * 		分页参数
	 *
	 * @return {@code Page<MicroWordComment>} 某页的一级评论
	 */
	Page<MicroWordComment> findAllByMicroWord(MicroWord microWord, Pageable pageable);
}
