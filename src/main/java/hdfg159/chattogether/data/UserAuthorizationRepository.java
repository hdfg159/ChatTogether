package hdfg159.chattogether.data;

import hdfg159.chattogether.domain.UserAuthorization;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

/**
 * Project:ChatTogether
 * Package:hdfg159.chattogether.data
 * Created by hdfg159 on 2017-11-28 11:52.
 */
public interface UserAuthorizationRepository extends JpaRepository<UserAuthorization, Long> {
	@Modifying
	@Query("DELETE FROM UserAuthorization u WHERE u.user.id = ?1")
	void deleteAllByUserId(Long userId);
}
