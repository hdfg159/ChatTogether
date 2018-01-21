package hdfg159.chattogether.data;

import hdfg159.chattogether.domain.UserAccountState;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Project:ChatTogether
 * Package:hdfg159.chattogether.data
 * Created by hdfg159 on 2017-11-28 11:51.
 */
public interface UserAccountStateRepository extends JpaRepository<UserAccountState, Long> {
	Optional<UserAccountState> findByUser_Id(Long userId);
}
