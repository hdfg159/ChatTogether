package hdfg159.chattogether.domain;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Project:ChatTogether
 * Package:hdfg159.chattogether.domain
 * Created by hdfg159 on 18-3-25 下午4:33.
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
@Table(name = "suggestion")
public class Suggestion implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", insertable = false, updatable = false)
	private Long id;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "user_id", nullable = false)
	private User user;
	
	/**
	 * 详细分类见SuggestionConsts
	 */
	@Column(name = "type", nullable = false)
	private Integer type;
	
	@Column(name = "title", nullable = false, length = 20)
	private String title;
	
	@Column(name = "content", nullable = false, length = 100)
	private String content;
	
	@Column(name = "gmt_create", nullable = false)
	private Date createTime;
	
	@Column(name = "gmt_modified", nullable = false)
	private Date modifiedTime;
}
