package hdfg159.chattogether.data;

import hdfg159.chattogether.domain.UserFriend;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

/**
 * Project:ChatTogether
 * Package:hdfg159.chattogether.data
 * Created by hdfg159 on 2017-11-28 11:55.
 */
public interface UserFriendRepository extends JpaRepository<UserFriend, Long> {
	Optional<UserFriend> findByActiveUser_UsernameAndPassiveUser_Username(String activeUsername, String passiveUsername);
	
	void deleteByActiveUser_UsernameAndPassiveUser_Username(String activeUsername, String passiveUsername);
	
	@Query("select case when (count(u) = 2)  then true else false end from UserFriend u where( u.activeUser.username = ?1 and u.passiveUser.username = ?2) or (u.activeUser.username = ?2 and u.passiveUser.username = ?1)")
	boolean isExistFriend(String username1, String username2);
	
	Page<UserFriend> findAllByActiveUser_Username(String activeUsername, Pageable pageable);
}
