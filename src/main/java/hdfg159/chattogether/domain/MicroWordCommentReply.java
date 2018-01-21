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
 * Created by hdfg159 on 2017-11-25 12:12.
 */
@Setter
@Getter
@ToString(exclude = {"microWordComment", "replyUser", "repliedUser"})
@EqualsAndHashCode(exclude = {"microWordComment", "replyUser", "repliedUser"})
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler"})
@Table(name = "microword_comment_reply")
public class MicroWordCommentReply {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", insertable = false, updatable = false)
	private Long id;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "microword_comment_id", nullable = false)
	private MicroWordComment microWordComment;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "reply_user_id", nullable = false)
	private User replyUser;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "replied_user_id", nullable = false)
	private User repliedUser;
	
	@Column(name = "content", nullable = false, length = 200)
	private String content;
	
	@Column(name = "gmt_create", nullable = false)
	private Date createTime;
	
	@Column(name = "gmt_modified", nullable = false)
	private Date modifiedTime;
}
