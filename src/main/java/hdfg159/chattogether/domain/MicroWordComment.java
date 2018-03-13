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
 * Created by hdfg159 on 2017-11-25 12:10.
 */
@Setter
@Getter
@ToString(exclude = {"microWord", "commentUser", "microWordCommentReplies", "microWordCommentAgrees"})
@EqualsAndHashCode(exclude = {"microWord", "commentUser", "microWordCommentReplies", "microWordCommentAgrees"})
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler"})
@Table(name = "microword_comment")
public class MicroWordComment {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", insertable = false, updatable = false)
	private Long id;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "microword_id", nullable = false)
	private MicroWord microWord;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "comment_user_id", nullable = false)
	private User commentUser;
	
	@Column(name = "content", nullable = false, length = 200)
	private String content;
	
	@Column(name = "gmt_create", nullable = false)
	private Date createTime;
	
	@Column(name = "gmt_modified", nullable = false)
	private Date modifiedTime;
	
	@JsonIgnore
	@OrderBy("createTime asc")
	@OneToMany(targetEntity = MicroWordCommentReply.class, fetch = FetchType.LAZY, cascade = CascadeType.REMOVE, mappedBy = "microWordComment")
	private Set<MicroWordCommentReply> microWordCommentReplies;
	
	@JsonIgnore
	@OneToMany(targetEntity = MicroWordCommentAgree.class, fetch = FetchType.LAZY, cascade = CascadeType.REMOVE, mappedBy = "microWordComment")
	private Set<MicroWordCommentAgree> microWordCommentAgrees;
}
