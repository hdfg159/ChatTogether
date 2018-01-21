package hdfg159.chattogether.controller.api;

import hdfg159.chattogether.annotation.CurrentUser;
import hdfg159.chattogether.domain.dto.BaseJsonObject;
import hdfg159.chattogether.service.MicroWordAgreeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import static hdfg159.chattogether.constant.RoleConsts.USER;
import static hdfg159.chattogether.util.ResponseUtils.responseSuccess;
import static org.springframework.http.HttpStatus.CREATED;

/**
 * Project:ChatTogether
 * Package:hdfg159.chattogether.controller.api
 * Created by hdfg159 on 2017-12-3 11:14.
 */
@Slf4j
@RestController
@RequestMapping("/microwordagree/api")
public class MicroWordAgreeApiController {
	private final MicroWordAgreeService microWordAgreeService;
	
	@Autowired
	public MicroWordAgreeApiController(MicroWordAgreeService microWordAgreeService) {this.microWordAgreeService = microWordAgreeService;}
	
	@Secured(USER)
	@PostMapping("/save")
	@ResponseStatus(CREATED)
	public BaseJsonObject<?> save(@CurrentUser UserDetails userDetail, Long microWordId, UriComponentsBuilder ucb) {
		String uri = ucb.path("/microword/details/")
				.path(String.valueOf(microWordId))
				.toUriString();
		microWordAgreeService.save(userDetail.getUsername(), microWordId, uri);
		return responseSuccess();
	}
}
