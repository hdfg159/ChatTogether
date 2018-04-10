package hdfg159.chattogether.controller.api;

import hdfg159.chattogether.annotation.CurrentUser;
import hdfg159.chattogether.domain.User;
import hdfg159.chattogether.domain.dto.BaseJsonObject;
import hdfg159.chattogether.service.UserFriendService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static hdfg159.chattogether.constant.RoleConsts.USER;
import static hdfg159.chattogether.util.ResponseUtils.responseDataSuccess;
import static hdfg159.chattogether.util.ResponseUtils.responseSuccess;

/**
 * Project:ChatTogether
 * Package:hdfg159.chattogether.controller.api
 * Created by hdfg159 on 2018-1-17 14:28.
 */
@Slf4j
@RestController
@RequestMapping("/userfriend/api")
public class UserFriendApiController {
	private final UserFriendService userFriendService;
	
	@Autowired
	public UserFriendApiController(UserFriendService userFriendService) {this.userFriendService = userFriendService;}
	
	@Secured(USER)
	@GetMapping("/delete")
	public BaseJsonObject<?> delete(@CurrentUser UserDetails userDetails, User user) {
		userFriendService.unfriend(userDetails.getUsername(), user.getUsername());
		return responseSuccess();
	}
	
	@Secured(USER)
	@GetMapping("/addfriend")
	public BaseJsonObject<?> save(@CurrentUser UserDetails userDetails, User user) {
		String uri = "userfriend/api/accept/" + userDetails.getUsername();
		userFriendService.buildFriend(userDetails.getUsername(), user.getUsername(), uri);
		return responseSuccess();
	}
	
	@Secured(USER)
	@GetMapping("/accept/{username}")
	public BaseJsonObject<?> accept(@CurrentUser UserDetails userDetails, @PathVariable String username) {
		userFriendService.acceptFriend(userDetails.getUsername(), username);
		return responseSuccess();
	}
	
	@Secured(USER)
	@GetMapping("/existfriends/{username}")
	public BaseJsonObject<?> isExistFriends(@CurrentUser UserDetails userDetails, @PathVariable String username) {
		return responseDataSuccess(userFriendService.isExistUserFriend(userDetails.getUsername(), username));
	}
}
