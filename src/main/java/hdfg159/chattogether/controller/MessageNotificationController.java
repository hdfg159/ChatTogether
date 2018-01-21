package hdfg159.chattogether.controller;

import hdfg159.chattogether.annotation.CurrentUser;
import hdfg159.chattogether.domain.MessageNotification;
import hdfg159.chattogether.service.MessageNotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
 * Created by hdfg159 on 2018-1-14 19:00.
 */
@Controller
@RequestMapping("/messagenotification")
public class MessageNotificationController {
	private final MessageNotificationService messageNotificationService;
	
	@Autowired
	public MessageNotificationController(MessageNotificationService messageNotificationService) {this.messageNotificationService = messageNotificationService;}
	
	@Secured(USER)
	@GetMapping("/shownotifications")
	public String showUserAllNotifications(Model model, @CurrentUser UserDetails userDetails, @PageableDefault(sort = "modifiedTime", direction = Sort.Direction.DESC) Pageable pageable) {
		Page<MessageNotification> notifications = messageNotificationService.findAllByReceiveNotificationUser(userDetails.getUsername(), pageable);
		model.addAttribute("notifications", notifications);
		return "notification/details";
	}
}
