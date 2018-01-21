package hdfg159.chattogether.domain;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.*;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

/**
 * Project:ChatTogether
 * Package:hdfg159.chattogether.domain
 * Created by hdfg159 on 2017-11-25 11:56.
 */
@Setter
@Getter
@ToString(exclude = {"sendUser", "receiveUser", "messageAttachments"})
@EqualsAndHashCode(exclude = {"sendUser", "receiveUser", "messageAttachments"})
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler"})
@Table(name = "message")
public class Message {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", insertable = false, updatable = false)
	private Long id;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "send_user_id", nullable = false)
	private User sendUser;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "receive_user_id", nullable = false)
	private User receiveUser;
	
	@Column(name = "is_read", nullable = false)
	private Integer isRead;
	
	@Column(name = "content", length = 5000)
	private String content;
	
	@Column(name = "gmt_create", nullable = false)
	private Date createTime;
	
	@Column(name = "gmt_modified", nullable = false)
	private Date modifiedTime;
	
	@JsonIgnore
	@OneToMany(targetEntity = MessageAttachment.class, cascade = CascadeType.REMOVE, fetch = FetchType.EAGER, mappedBy = "message")
	private Set<MessageAttachment> messageAttachments;
	
}
