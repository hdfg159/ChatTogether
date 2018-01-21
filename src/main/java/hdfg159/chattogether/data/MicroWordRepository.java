package hdfg159.chattogether.data;

import hdfg159.chattogether.domain.MicroWord;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Project:ChatTogether
 * Package:hdfg159.chattogether.data
 * Created by hdfg159 on 2017-11-28 11:43.
 */
public interface MicroWordRepository extends JpaRepository<MicroWord, Long> {
	Page<MicroWord> findAllByContentContaining(String content, Pageable pageable);
}
