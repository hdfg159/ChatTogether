package hdfg159.chattogether.controller.api;

import hdfg159.chattogether.domain.User;
import hdfg159.chattogether.domain.dto.BaseJsonObject;
import hdfg159.chattogether.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static hdfg159.chattogether.constant.RoleConsts.ADMIN_USER;
import static hdfg159.chattogether.util.ResponseUtils.responseDataSuccess;
import static hdfg159.chattogether.util.ResponseUtils.responseSuccess;

/**
 * Project:ChatTogether
 * Package:hdfg159.chattogether.controller.api
 * Created by hdfg159 on 2018-1-13 16:58.
 */
@Slf4j
@RestController
@RequestMapping("/user/api")
public class UserApiController {
	private final UserService userService;
	
	@Autowired
	public UserApiController(UserService userService) {this.userService = userService;}
	
	@Secured(ADMIN_USER)
	@GetMapping("/delete")
	public BaseJsonObject<?> delete(User user) {
		userService.deleteById(user.getId());
		return responseSuccess();
	}
	
	@Secured(ADMIN_USER)
	@GetMapping("/reset")
	public BaseJsonObject<?> reset(User user) {
		String newPassword = userService.resetPassword(user.getUsername());
		return responseDataSuccess(newPassword);
	}
	
	@Secured(ADMIN_USER)
	@GetMapping("/switchState")
	public BaseJsonObject<?> disable(User user) {
		userService.switchUserAccountStateById(user.getId());
		return responseSuccess();
	}
}
