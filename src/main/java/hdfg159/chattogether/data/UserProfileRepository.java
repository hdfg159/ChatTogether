package hdfg159.chattogether.data;

import hdfg159.chattogether.domain.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Project:ChatTogether
 * Package:hdfg159.chattogether.data
 * Created by hdfg159 on 2017-11-28 11:56.
 */
public interface UserProfileRepository extends JpaRepository<UserProfile, Long> {
	Optional<UserProfile> findByUser_Username(String username);
	
	Optional<UserProfile> findById(Long id);
}
