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
 * Created by hdfg159 on 2017-11-25 11:58.
 */
@Setter
@Getter
@ToString(exclude = {"activeUser", "passiveUser"})
@EqualsAndHashCode(exclude = {"activeUser", "passiveUser"})
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler"})
@Table(name = "user_friend", uniqueConstraints = {@UniqueConstraint(columnNames = {"active_user_id", "passive_user_id"})})
public class UserFriend {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", insertable = false, updatable = false)
	private Long id;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "active_user_id", nullable = false)
	private User activeUser;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "passive_user_id", nullable = false)
	private User passiveUser;
	
	@Column(name = "gmt_create", nullable = false)
	private Date createTime;
	
	@Column(name = "gmt_modified", nullable = false)
	private Date modifiedTime;
}
