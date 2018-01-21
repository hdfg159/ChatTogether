package hdfg159.chattogether.controller.api;

import hdfg159.chattogether.annotation.CurrentUser;
import hdfg159.chattogether.domain.MicroWordCommentReply;
import hdfg159.chattogether.domain.dto.BaseJsonObject;
import hdfg159.chattogether.domain.vo.ReplyFormVO;
import hdfg159.chattogether.service.MicroWordCommentReplyService;
import hdfg159.chattogether.util.ResponseUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;

import static hdfg159.chattogether.constant.RoleConsts.USER;
import static hdfg159.chattogether.util.ResponseUtils.responseSuccess;
import static org.springframework.http.HttpStatus.CREATED;

/**
 * Project:ChatTogether
 * Package:hdfg159.chattogether.controller.api
 * Created by hdfg159 on 2017-12-5 15:25.
 */
@Slf4j
@RestController
@RequestMapping("/microwordcommentreply/api")
public class MicroWordCommentReplyApiController {
	private final MicroWordCommentReplyService microWordCommentReplyService;
	
	@Autowired
	public MicroWordCommentReplyApiController(MicroWordCommentReplyService microWordCommentReplyService) {this.microWordCommentReplyService = microWordCommentReplyService;}
	
	@Secured(USER)
	@PostMapping("/save")
	@ResponseStatus(CREATED)
	public BaseJsonObject<?> save(@CurrentUser UserDetails userDetail, @Valid ReplyFormVO replyFormVO, Errors errors, UriComponentsBuilder ucb) {
		if (errors.hasErrors()) {
			return ResponseUtils.responseFail(errors);
		}
		String uri = ucb.path("/microword/details/")
				.path(String.valueOf(replyFormVO.getMicroWordId()))
				.toUriString();
		microWordCommentReplyService.save(replyFormVO.getMicrowordCommentId(), userDetail.getUsername(), replyFormVO.getRepliedUserId(), replyFormVO.getContent(), uri);
		return ResponseUtils.responseSuccess();
	}
	
	@Secured(USER)
	@PostMapping("/delete")
	public BaseJsonObject<?> delete(MicroWordCommentReply microWordCommentReply) {
		microWordCommentReplyService.deleteById(microWordCommentReply.getId());
		return responseSuccess();
	}
}
