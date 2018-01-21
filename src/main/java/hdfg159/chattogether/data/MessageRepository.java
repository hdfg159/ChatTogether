package hdfg159.chattogether.data;

import hdfg159.chattogether.domain.Message;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Project:ChatTogether
 * Package:hdfg159.chattogether.data
 * Created by hdfg159 on 2017-11-28 11:40.
 */
public interface MessageRepository extends JpaRepository<Message, Long> {
	List<Message> findAllByReceiveUser_UsernameAndSendUser_UsernameAndIsRead(String sendUsername, String receiveUsername, Integer isRead);
	
	@Query("select m from Message m where (m.sendUser.username = ?1 and m.receiveUser.username = ?2) or (m.sendUser.username = ?2 and m.receiveUser.username = ?1)")
	Page<Message> findAllByUsers(String username1, String username2, Pageable pageable);

//	List<Message> findAllByReceiveUserAndSendUserAndStateIsFalse(User receiveUser, User sendUser);
}
