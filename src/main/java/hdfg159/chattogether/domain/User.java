package hdfg159.chattogether.domain;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;

/**
 * Project:ChatTogether
 * Package:hdfg159.chattogether.domain
 * Created by hdfg159 on 2017-11-25 10:58.
 */
@Setter
@Getter
@ToString(exclude = {"userAccountState", "userProfile", "userAuthorizations", "activeUserFriends", "passiveUserFriends", "sendMessages", "receiveMessages", "sendMessageNotifications", "receiveMessageNotifications", "microWords", "microWordAgrees", "microWordComments", "microWordCommentAgrees"})
@EqualsAndHashCode(exclude = {"userAccountState", "userProfile", "userAuthorizations", "activeUserFriends", "passiveUserFriends", "sendMessages", "receiveMessages", "sendMessageNotifications", "receiveMessageNotifications", "microWords", "microWordAgrees", "microWordComments", "microWordCommentAgrees"})
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler"})
@Table(name = "user")
public class User implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", insertable = false, updatable = false)
	private Long id;
	
	@Column(name = "username", unique = true, nullable = false)
	private String username;
	
	@Column(name = "password", nullable = false)
	private String password;
	
	@Column(name = "gmt_create", nullable = false)
	private Date createTime;
	
	@Column(name = "gmt_modified", nullable = false)
	private Date modifiedTime;
	
	@OneToOne(targetEntity = UserAccountState.class, cascade = CascadeType.REMOVE, fetch = FetchType.EAGER, mappedBy = "user")
	private UserAccountState userAccountState;
	
	@OneToOne(targetEntity = UserProfile.class, cascade = CascadeType.REMOVE, fetch = FetchType.LAZY, mappedBy = "user")
	private UserProfile userProfile;
	
	@JsonIgnore
	@OneToMany(targetEntity = UserAuthorization.class, cascade = {CascadeType.REMOVE}, fetch = FetchType.EAGER, mappedBy = "user")
	private Set<UserAuthorization> userAuthorizations;
	
	@JsonIgnore
	@OneToMany(targetEntity = UserFriend.class, cascade = CascadeType.REMOVE, fetch = FetchType.LAZY, mappedBy = "activeUser")
	private Set<UserFriend> activeUserFriends;
	
	@JsonIgnore
	@OneToMany(targetEntity = UserFriend.class, cascade = CascadeType.REMOVE, fetch = FetchType.LAZY, mappedBy = "passiveUser")
	private Set<UserFriend> passiveUserFriends;
	
	@JsonIgnore
	@OneToMany(targetEntity = Message.class, cascade = CascadeType.REMOVE, fetch = FetchType.LAZY, mappedBy = "sendUser")
	private Set<Message> sendMessages;
	
	@JsonIgnore
	@OneToMany(targetEntity = Message.class, cascade = CascadeType.REMOVE, fetch = FetchType.LAZY, mappedBy = "receiveUser")
	private Set<Message> receiveMessages;
	
	@JsonIgnore
	@OneToMany(targetEntity = MessageNotification.class, cascade = CascadeType.REMOVE, fetch = FetchType.LAZY, mappedBy = "sendNotificationUser")
	private Set<MessageNotification> sendMessageNotifications;
	
	@JsonIgnore
	@OneToMany(targetEntity = MessageNotification.class, cascade = CascadeType.REMOVE, fetch = FetchType.LAZY, mappedBy = "receiveNotificationUser")
	private Set<MessageNotification> receiveMessageNotifications;
	
	@JsonIgnore
	@OneToMany(targetEntity = MicroWord.class, cascade = CascadeType.REMOVE, fetch = FetchType.LAZY, mappedBy = "user")
	private Set<MicroWord> microWords;
	
	@JsonIgnore
	@OneToMany(targetEntity = MicroWordAgree.class, cascade = CascadeType.REMOVE, fetch = FetchType.LAZY, mappedBy = "user")
	private Set<MicroWordAgree> microWordAgrees;
	
	@JsonIgnore
	@OneToMany(targetEntity = MicroWordComment.class, cascade = CascadeType.REMOVE, fetch = FetchType.LAZY, mappedBy = "commentUser")
	private Set<MicroWordComment> microWordComments;
	
	@JsonIgnore
	@OneToMany(targetEntity = MicroWordCommentAgree.class, cascade = CascadeType.REMOVE, fetch = FetchType.LAZY, mappedBy = "user")
	private Set<MicroWordCommentAgree> microWordCommentAgrees;
	
	@JsonIgnore
	@OneToMany(targetEntity = Suggestion.class, cascade = CascadeType.REMOVE, fetch = FetchType.LAZY, mappedBy = "user")
	private Set<Suggestion> suggestions;
}
