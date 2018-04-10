package hdfg159.chattogether.controller;

import hdfg159.chattogether.annotation.CurrentUser;
import hdfg159.chattogether.domain.Message;
import hdfg159.chattogether.service.MessageService;
import lombok.extern.slf4j.Slf4j;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import static hdfg159.chattogether.constant.RoleConsts.USER;

/**
 * Project:ChatTogether
 * Package:hdfg159.chattogether.controller
 * Created by hdfg159 on 2018-1-18 15:26.
 */
@Slf4j
@Controller
@RequestMapping("/message")
public class MessageController {
	private final MessageService messageService;
	
	@Autowired
	public MessageController(MessageService messageService) {
		this.messageService = messageService;
	}
	
	@Secured(USER)
	@GetMapping("/chat/{username}")
	public String chat(Model model, @CurrentUser UserDetails userDetails, @PathVariable String username, @PageableDefault(sort = "id", direction = Sort.Direction.DESC, size = 20) Pageable pageable) {
		Page<Message> messages = messageService.findAllByUsers(userDetails.getUsername(), username, pageable);
		model.addAttribute("messages", messages);
		model.addAttribute("chatUsername", username);
		return "user/chat";
	}
}
