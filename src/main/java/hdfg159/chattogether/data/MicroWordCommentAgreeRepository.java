package hdfg159.chattogether.data;

import hdfg159.chattogether.domain.MicroWordCommentAgree;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Project:ChatTogether
 * Package:hdfg159.chattogether.data
 * Created by hdfg159 on 18-3-13 下午8:00.
 */
public interface MicroWordCommentAgreeRepository extends JpaRepository<MicroWordCommentAgree, Long> {
	Optional<MicroWordCommentAgree> findByUser_UsernameAndMicroWordComment_Id(String username, Long microWordCommentId);
}
