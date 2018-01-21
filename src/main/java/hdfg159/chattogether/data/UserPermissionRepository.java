package hdfg159.chattogether.data;

import hdfg159.chattogether.domain.UserPermission;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Project:ChatTogether
 * Package:hdfg159.chattogether.data
 * Created by hdfg159 on 2017-11-25 19:00.
 */
public interface UserPermissionRepository extends JpaRepository<UserPermission, Long> {
	UserPermission findByRoleNameIgnoreCase(String role);
}
