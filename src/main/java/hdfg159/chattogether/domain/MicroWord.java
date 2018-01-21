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
 * Created by hdfg159 on 2017-11-25 12:06.
 */
@Setter
@Getter
@ToString(exclude = {"user", "microWordAttachments", "microWordAgrees", "microWordComments"})
@EqualsAndHashCode(exclude = {"user", "microWordAttachments", "microWordAgrees", "microWordComments"})
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler"})
@Table(name = "microword")
public class MicroWord {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", insertable = false, updatable = false)
	private Long id;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "user_id", nullable = false)
	private User user;
	
	@Column(name = "content", nullable = false, length = 200)
	private String content;
	
	@Column(name = "gmt_create", nullable = false)
	private Date createTime;
	
	@Column(name = "gmt_modified", nullable = false)
	private Date modifiedTime;
	
	@JsonIgnore
	@OneToMany(targetEntity = MicroWordAttachment.class, fetch = FetchType.LAZY, cascade = CascadeType.REMOVE, mappedBy = "microWord")
	private Set<MicroWordAttachment> microWordAttachments;
	
	@JsonIgnore
	@OneToMany(targetEntity = MicroWordAgree.class, fetch = FetchType.LAZY, cascade = CascadeType.REMOVE, mappedBy = "microWord", orphanRemoval = true)
	private Set<MicroWordAgree> microWordAgrees;
	
	@JsonIgnore
	@OneToMany(targetEntity = MicroWordComment.class, fetch = FetchType.LAZY, cascade = CascadeType.REMOVE, mappedBy = "microWord")
	private Set<MicroWordComment> microWordComments;
}
