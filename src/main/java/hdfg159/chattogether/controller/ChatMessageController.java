package hdfg159.chattogether.controller;

import hdfg159.chattogether.domain.dto.MessageJsonObject;
import hdfg159.chattogether.domain.vo.ChatMessageFormVO;
import hdfg159.chattogether.service.ChatMessageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;

import java.security.Principal;

/**
 * Project:ChatTogether
 * Package:hdfg159.chattogether.controller
 * Created by hdfg159 on 18-3-23 上午10:54.
 */
@Slf4j
@Controller
public class ChatMessageController {
	private final ChatMessageService chatMessageService;
	
	@Autowired
	public ChatMessageController(ChatMessageService chatMessageService) {this.chatMessageService = chatMessageService;}
	
	@MessageMapping("/chat")
	@SendToUser("/queue/chat")
	public MessageJsonObject<?> handleChatMessage(Principal principal, ChatMessageFormVO form) {
		return chatMessageService.sendChatMessage(principal.getName(), form);
	}
}
