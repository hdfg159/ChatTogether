package hdfg159.chattogether.controller.api;

import hdfg159.chattogether.annotation.CurrentUser;
import hdfg159.chattogether.domain.dto.BaseJsonObject;
import hdfg159.chattogether.service.MicroWordCommentAgreeService;
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
 * Created by hdfg159 on 18-3-13 下午9:29.
 */
@Slf4j
@RestController
@RequestMapping("/microwordcommentagree/api")
public class MicroWordCommentAgreeApiController {
	private final MicroWordCommentAgreeService microWordCommentAgreeService;
	
	@Autowired
	public MicroWordCommentAgreeApiController(MicroWordCommentAgreeService microWordCommentAgreeService) {this.microWordCommentAgreeService = microWordCommentAgreeService;}
	
	@Secured(USER)
	@PostMapping("/save")
	@ResponseStatus(CREATED)
	public BaseJsonObject<?> save(@CurrentUser UserDetails userDetail, Long microWordId, Long microWordCommentId) {
		String uri = "microword/details/" + String.valueOf(microWordId);
		microWordCommentAgreeService.save(userDetail.getUsername(), microWordCommentId, uri);
		return responseSuccess();
	}
}
