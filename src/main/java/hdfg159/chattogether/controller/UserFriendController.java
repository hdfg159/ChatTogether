package hdfg159.chattogether.controller;

import hdfg159.chattogether.annotation.CurrentUser;
import hdfg159.chattogether.domain.UserFriend;
import hdfg159.chattogether.service.UserFriendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import static hdfg159.chattogether.constant.RoleConsts.USER;

/**
 * Project:ChatTogether
 * Package:hdfg159.chattogether.controller
 * Created by hdfg159 on 2018-1-17 18:02.
 */
@Controller
@RequestMapping("/userfriend")
public class UserFriendController {
	private final UserFriendService userFriendService;
	
	@Autowired
	public UserFriendController(UserFriendService userFriendService) {this.userFriendService = userFriendService;}
	
	@Secured(USER)
	@GetMapping("/friends")
	public String showFriends(Model model, @CurrentUser UserDetails userDetails, @PageableDefault Pageable pageable) {
		Page<UserFriend> userFriendPage = userFriendService.findAllByActiveUser(userDetails.getUsername(), pageable);
		model.addAttribute("userFriendPage", userFriendPage);
		return "user/friends";
	}
}
