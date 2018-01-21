package hdfg159.chattogether.data;

import hdfg159.chattogether.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Project:ChatTogether
 * Package:hdfg159.chattogether.data
 * Created by hdfg159 on 2017-11-25 18:08.
 */
public interface UserRepository extends JpaRepository<User, Long> {
	Page<User> findAllByUsernameContaining(String username, Pageable pageable);
	
	Optional<User> findByUsernameIgnoreCase(String username);
}
