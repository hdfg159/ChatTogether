package hdfg159.chattogether.domain;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.*;

import javax.persistence.*;
import java.util.Date;

/**
 * Project:ChatTogether
 * Package:hdfg159.chattogether.domain
 * Created by hdfg159 on 2017-11-25 12:03.
 */
@Setter
@Getter
@ToString(exclude = {"sendNotificationUser", "receiveNotificationUser"})
@EqualsAndHashCode(exclude = {"sendNotificationUser", "receiveNotificationUser"})
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler"})
@Table(name = "message_notification")
public class MessageNotification {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", insertable = false, updatable = false)
	private Long id;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "send_user_id", nullable = false)
	private User sendNotificationUser;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "receive_user_id", nullable = false)
	private User receiveNotificationUser;
	
	@Column(name = "content", nullable = false)
	private String content;
	
	@Column(name = "url", nullable = false)
	private String url;
	
	@Column(name = "is_read", nullable = false)
	private Integer isRead;
	
	@Column(name = "type", nullable = false)
	private Integer type;
	
	@Column(name = "gmt_create", nullable = false)
	private Date createTime;
	
	@Column(name = "gmt_modified", nullable = false)
	private Date modifiedTime;
}
