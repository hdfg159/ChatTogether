package hdfg159.chattogether.service.impl;

import hdfg159.chattogether.data.UserRepository;
import hdfg159.chattogether.domain.User;
import hdfg159.chattogether.domain.UserAuthorization;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.ArrayList;
import java.util.List;

/**
 * Project:ChatTogether
 * Package:hdfg159.chattogether.service
 * Created by hdfg159 on 2017-11-25 18:00.
 */
@Slf4j
public class CustomUserDetailsServiceImpl implements UserDetailsService {
	@Autowired
	private UserRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findByUsernameIgnoreCase(username)
				.orElseThrow(() -> new UsernameNotFoundException("User '" + username + "' Not Found!"));
		List<GrantedAuthority> authorities = new ArrayList<>();
		for (UserAuthorization authorization : user.getUserAuthorizations()) {
			String role = authorization.getUserPermission().getRoleName();
			authorities.add(new SimpleGrantedAuthority(role));
			log.info("User:\"{}\",Permission:\"{}\"", authorization.getUser().getUsername(), role);
		}
		return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), isEnable(user), true, true, true, authorities);
	}
	
	private boolean isEnable(User user) {
		return user.getUserAccountState().getIsEnabled() == 1;
	}
}
