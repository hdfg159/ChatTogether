package hdfg159.chattogether.controller;

import hdfg159.chattogether.annotation.CurrentUser;
import hdfg159.chattogether.domain.User;
import hdfg159.chattogether.domain.ao.ChangePasswordAO;
import hdfg159.chattogether.domain.vo.ChangePasswordFormVO;
import hdfg159.chattogether.service.UserPermissionService;
import hdfg159.chattogether.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

import static hdfg159.chattogether.constant.RoleConsts.ADMIN_USER;
import static hdfg159.chattogether.constant.RoleConsts.USER;

/**
 * Project:ChatTogether
 * Package:hdfg159.chattogether.controller
 * Created by hdfg159 on 2017-11-29 17:49.
 */
@Controller
@RequestMapping("/user")
public class UserController {
	private final UserService userService;
	private final UserPermissionService userPermissionService;
	
	@Autowired
	public UserController(UserPermissionService userPermissionService, UserService userService) {
		this.userPermissionService = userPermissionService;
		this.userService = userService;
	}
	
	@Secured(USER)
	@PostMapping("/security")
	public String security(Model model, @CurrentUser UserDetails userDetails, @Valid ChangePasswordFormVO changePasswordFormVO, Errors errors) {
		if (errors.hasErrors()) {
			return "user/security";
		}
		ChangePasswordAO changePasswordAO = ChangePasswordAO.builder()
				.username(userDetails.getUsername())
				.oldPassword(changePasswordFormVO.getExistingPassword())
				.newPassword(changePasswordFormVO.getNewPassword())
				.build();
		userService.changePassword(changePasswordAO);
		SecurityContextHolder.clearContext();
		return "redirect:/login";
	}
	
	@Secured(USER)
	@GetMapping("/security")
	public String showSecurity(Model model) {
		model.addAttribute("changePasswordFormVO", new ChangePasswordFormVO());
		return "user/security";
	}
	
	@Secured(ADMIN_USER)
	@GetMapping("/users")
	public String showAllUsers(Model model, @PageableDefault(sort = "id", direction = Sort.Direction.ASC) Pageable pageable) {
		Page<User> users = userService.findAll(pageable);
		model.addAttribute("users", users);
		return "admin/usermanage";
	}
	
	@Secured(ADMIN_USER)
	@GetMapping("/authorize")
	public String showUserAuthority(Model model, User user) {
		model.addAttribute("username", user.getUsername());
		model.addAttribute("userPermissions", userPermissionService.findAllByUsernameIgnoreCase(user.getUsername()));
		model.addAttribute("allPermissions", userPermissionService.findAll());
		return "admin/authorize";
	}
}
