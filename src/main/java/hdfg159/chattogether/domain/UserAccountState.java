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
 * Created by hdfg159 on 2017-11-25 11:15.
 */
@Setter
@Getter
@ToString(exclude = {"user"})
@EqualsAndHashCode(exclude = {"user"})
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler"})
@Table(name = "user_account_state")
public class UserAccountState {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", insertable = false, updatable = false)
	private Long id;
	
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "user_id", nullable = false, unique = true)
	private User user;
	
	@Column(name = "is_enabled", nullable = false)
	private Integer isEnabled;
	
	@Column(name = "gmt_create", nullable = false)
	private Date createTime;
	
	@Column(name = "gmt_modified", nullable = false)
	private Date modifiedTime;
}
