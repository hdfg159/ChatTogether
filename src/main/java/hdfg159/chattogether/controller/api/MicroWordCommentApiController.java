package hdfg159.chattogether.controller.api;

import hdfg159.chattogether.annotation.CurrentUser;
import hdfg159.chattogether.domain.MicroWordComment;
import hdfg159.chattogether.domain.dto.BaseJsonObject;
import hdfg159.chattogether.service.MicroWordCommentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import static hdfg159.chattogether.constant.RoleConsts.USER;
import static hdfg159.chattogether.util.ResponseUtils.responseSuccess;
import static org.springframework.http.HttpStatus.CREATED;

/**
 * Project:ChatTogether
 * Package:hdfg159.chattogether.controller.api
 * Created by hdfg159 on 2017-12-4 17:39.
 */
@Slf4j
@RestController
@RequestMapping("/microwordcomment/api")
public class MicroWordCommentApiController {
	private final MicroWordCommentService microWordCommentService;
	
	@Autowired
	public MicroWordCommentApiController(MicroWordCommentService microWordCommentService) {this.microWordCommentService = microWordCommentService;}
	
	@Secured(USER)
	@PostMapping("/save")
	@ResponseStatus(CREATED)
	public BaseJsonObject<?> save(@CurrentUser UserDetails userDetail, Long microWordId, String content) {
//		String uri = ucb.path("/microword/details/")
//				.path(String.valueOf(microWordId))
//				.toUriString();
		String uri = "microword/details/" + String.valueOf(microWordId);
		microWordCommentService.save(userDetail.getUsername(), microWordId, content, uri);
		return responseSuccess();
	}
	
	@Secured(USER)
	@PostMapping("/delete")
	public BaseJsonObject<?> delete(MicroWordComment microWordComment) {
		microWordCommentService.deleteById(microWordComment.getId());
		return responseSuccess();
	}
}
