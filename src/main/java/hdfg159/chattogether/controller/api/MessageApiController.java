package hdfg159.chattogether.controller.api;

import hdfg159.chattogether.annotation.CurrentUser;
import hdfg159.chattogether.domain.ao.ChatMessageAO;
import hdfg159.chattogether.domain.dto.BaseJsonObject;
import hdfg159.chattogether.domain.vo.ChatMessageFormVO;
import hdfg159.chattogether.service.MessageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import static hdfg159.chattogether.constant.RoleConsts.USER;
import static hdfg159.chattogether.util.ResponseUtils.responseDataSuccess;
import static hdfg159.chattogether.util.ResponseUtils.responseFail;
import static org.springframework.http.HttpStatus.CREATED;

/**
 * Project:ChatTogether
 * Package:hdfg159.chattogether.controller.api
 * Created by hdfg159 on 2018-1-19 11:58.
 */
@Slf4j
@RestController
@RequestMapping("/message/api")
public class MessageApiController {
	private final MessageService messageService;
	
	@Autowired
	public MessageApiController(MessageService messageService) {this.messageService = messageService;}
	
	@Secured(USER)
	@PostMapping("/save")
	@ResponseStatus(CREATED)
	public BaseJsonObject<?> save(@CurrentUser UserDetails userDetails, @Valid ChatMessageFormVO chatMessageFormVO, Errors errors) {
		if (errors.hasErrors()) {
			return responseFail(errors);
		}
		String uri = "message/chat/" + userDetails.getUsername();
		ChatMessageAO chatMessageAO = ChatMessageAO.builder()
				.uri(uri)
				.receiveUsername(chatMessageFormVO.getReceiveUsername())
				.sendUsername(userDetails.getUsername())
				.chatMessageFormVO(chatMessageFormVO)
				.build();
		return responseDataSuccess(messageService.save(chatMessageAO));
	}
}
