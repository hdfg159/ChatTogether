package hdfg159.chattogether.controller.api;

import hdfg159.chattogether.annotation.CurrentUser;
import hdfg159.chattogether.domain.dto.BaseJsonObject;
import hdfg159.chattogether.service.MessageNotificationService;
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
 * Created by hdfg159 on 2018-1-14 18:07.
 */
@RestController
@RequestMapping("/messagenotification/api")
public class MessageNotificationApiController {
	private final MessageNotificationService messageNotificationService;
	
	@Autowired
	public MessageNotificationApiController(MessageNotificationService messageNotificationService) {this.messageNotificationService = messageNotificationService;}
	
	@Secured(USER)
	@GetMapping("/count")
	public BaseJsonObject<?> count(@CurrentUser UserDetails userDetails) {
		Long count = messageNotificationService.countByReceiveNotificationUserAndIsRead(userDetails.getUsername());
		return responseDataSuccess(count);
	}
	
	@Secured(USER)
	@GetMapping("/markread")
	public BaseJsonObject<?> markNotificationsAsRead(@CurrentUser UserDetails userDetails) {
		messageNotificationService.markNotificationsAsRead(userDetails.getUsername());
		return responseSuccess();
	}
	
	@Secured(USER)
	@GetMapping("/markread/{notificationId}")
	public BaseJsonObject<?> markNotificationAsRead(@PathVariable Long notificationId) {
		messageNotificationService.markNotificationAsRead(notificationId);
		return responseSuccess();
	}
}
