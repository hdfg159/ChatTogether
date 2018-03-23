package hdfg159.chattogether.service.impl;

import hdfg159.chattogether.domain.Message;
import hdfg159.chattogether.domain.ao.ChatMessageAO;
import hdfg159.chattogether.domain.dto.MessageJsonObject;
import hdfg159.chattogether.domain.vo.ChatMessageFormVO;
import hdfg159.chattogether.service.ChatMessageService;
import hdfg159.chattogether.service.MessageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Project:ChatTogether
 * Package:hdfg159.chattogether.service.impl
 * Created by hdfg159 on 18-3-23 上午10:59.
 */
@Slf4j
@Service
@Transactional
public class ChatMessageServiceImpl implements ChatMessageService {
	private final SimpMessagingTemplate messagingTemplate;
	private final MessageService messageService;
	
	@Autowired
	public ChatMessageServiceImpl(SimpMessagingTemplate messagingTemplate, MessageService messageService) {
		this.messagingTemplate = messagingTemplate;
		this.messageService = messageService;
	}
	
	@Override
	public MessageJsonObject<?> sendChatMessage(String sendUsername, ChatMessageFormVO form) {
		ChatMessageAO chatMessageAO = ChatMessageAO.builder()
				.sendUsername(sendUsername)
				.receiveUsername(form.getReceiveUsername())
				.chatMessageFormVO(form)
				.uri("message/chat/" + sendUsername)
				.build();
		Message msg = messageService.save(chatMessageAO);
		
		MessageJsonObject<?> message = buildMessageJsonObject(sendUsername, form, msg);
		messagingTemplate.convertAndSendToUser(form.getReceiveUsername(), "/queue/chat",
				message);
		return message;
	}
	
	private MessageJsonObject<Object> buildMessageJsonObject(String sendUsername, ChatMessageFormVO form, Message msg) {
		return MessageJsonObject
				.builder()
				.sendUsername(sendUsername)
				.receiveUsername(form.getReceiveUsername())
				.content(msg)
				.build();
	}
}
