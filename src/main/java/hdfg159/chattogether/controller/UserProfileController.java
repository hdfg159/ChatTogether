package hdfg159.chattogether.controller;

import hdfg159.chattogether.annotation.CurrentUser;
import hdfg159.chattogether.domain.UserProfile;
import hdfg159.chattogether.domain.vo.UserProfileFormVO;
import hdfg159.chattogether.service.UserFriendService;
import hdfg159.chattogether.service.UserProfileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

import static hdfg159.chattogether.constant.RoleConsts.USER;

/**
 * Project:ChatTogether
 * Package:hdfg159.chattogether.controller
 * Created by hdfg159 on 2018-1-12 11:00.
 */
@Slf4j
@Controller
@RequestMapping("/userprofile")
public class UserProfileController {
	private final UserProfileService userProfileService;
	private final UserFriendService userFriendService;
	
	@Autowired
	public UserProfileController(UserProfileService userProfileService, UserFriendService userFriendService) {
		this.userProfileService = userProfileService;
		this.userFriendService = userFriendService;
	}
	
	@GetMapping("/details/{username}")
	public String showDetails(Model model, @PathVariable String username, @CurrentUser UserDetails userDetails) {
		UserProfile userProfile = userProfileService.findByUsername(username);
		model.addAttribute("userProfile", userProfile);
		model.addAttribute("isExistUserFriend", userFriendService.isExistUserFriend(userDetails.getUsername(), username));
		return "user/details";
	}
	
	@Secured(USER)
	@PostMapping("/save")
	public String saveUserProfile(Model model, @Valid UserProfileFormVO userProfileFormVO, Errors errors) {
		if (errors.hasErrors()) {
			log.info(errors.toString());
			model.addAttribute("errors", errors);
			model.addAttribute("userProfile", userProfileService.findById(userProfileFormVO.getId()));
			return "user/details";
		}
		UserProfile userProfile = userProfileService.save(userProfileFormVO);
		model.addAttribute("username", userProfile.getUser().getUsername());
		return "redirect:/userprofile/details/{username}";
	}
}
