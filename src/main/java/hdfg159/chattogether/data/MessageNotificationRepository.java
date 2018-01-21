package hdfg159.chattogether.data;

import hdfg159.chattogether.domain.MessageNotification;
import hdfg159.chattogether.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * Project:ChatTogether
 * Package:hdfg159.chattogether.data
 * Created by hdfg159 on 2017-11-28 11:42.
 */
public interface MessageNotificationRepository extends JpaRepository<MessageNotification, Long> {
	Page<MessageNotification> findAllByReceiveNotificationUser_Username(String receiveNotificationUsername, Pageable pageable);
	
	Long countByReceiveNotificationUser_UsernameAndIsReadAndType(String receiveNotificationUsername, Integer isRead, Integer type);
	
	Long countByReceiveNotificationUser_UsernameAndIsRead(String receiveNotificationUsername, Integer isRead);
	
	List<MessageNotification> findAllByReceiveNotificationUser_UsernameAndIsReadAndType(String receiveNotificationUsername, Integer isRead, Integer type);
	
	List<MessageNotification> findAllByReceiveNotificationUser_UsernameAndSendNotificationUser_UsernameAndIsReadAndType(String receiveNotificationUsername, String sendNotificationUsername, Integer isRead, Integer type);
	
	List<MessageNotification> findAllByReceiveNotificationUser_UsernameAndIsRead(String receiveNotificationUsername, Integer isRead);
	
	Optional<MessageNotification> findFirstByTypeAndSendNotificationUserAndReceiveNotificationUser(Integer type, User sendUser, User receiveUser);
}
