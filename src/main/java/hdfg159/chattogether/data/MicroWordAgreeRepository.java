package hdfg159.chattogether.data;

import hdfg159.chattogether.domain.MicroWord;
import hdfg159.chattogether.domain.MicroWordAgree;
import hdfg159.chattogether.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Project:ChatTogether
 * Package:hdfg159.chattogether.data
 * Created by hdfg159 on 2017-11-28 11:44.
 */
public interface MicroWordAgreeRepository extends JpaRepository<MicroWordAgree, Long> {
	MicroWordAgree findByMicroWordAndUser(MicroWord microWord, User user);
}
