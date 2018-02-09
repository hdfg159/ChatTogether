package hdfg159.chattogether.data;

import hdfg159.chattogether.domain.MicroWord;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * Project:ChatTogether
 * Package:hdfg159.chattogether.data
 * Created by hdfg159 on 2017-11-28 11:43.
 */
public interface MicroWordRepository extends JpaRepository<MicroWord, Long> {
	Page<MicroWord> findAllByContentContaining(String content, Pageable pageable);
	
	Page<MicroWord> findAllByUser_Username(String username, Pageable pageable);
	
	@Query("SELECT m FROM MicroWord m WHERE m.user.id IN (SELECT uf.passiveUser.id FROM UserFriend uf LEFT JOIN User u ON u.username = ?1 WHERE uf.activeUser.id = u.id)")
	Page<MicroWord> findAllByUserFriend(String username, Pageable pageable);
}
