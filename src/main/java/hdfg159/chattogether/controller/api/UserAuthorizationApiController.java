package hdfg159.chattogether.controller.api;

import hdfg159.chattogether.domain.dto.BaseJsonObject;
import hdfg159.chattogether.domain.vo.UserAuthorizationFormVO;
import hdfg159.chattogether.service.UserAuthorizationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import static hdfg159.chattogether.constant.RoleConsts.ADMIN_USER;
import static hdfg159.chattogether.util.ResponseUtils.responseSuccess;
import static org.springframework.http.HttpStatus.CREATED;

/**
 * Project:ChatTogether
 * Package:hdfg159.chattogether.controller.api
 * Created by hdfg159 on 2018-1-12 22:30.
 */
@Slf4j
@RestController
@RequestMapping("/userauthorization/api")
public class UserAuthorizationApiController {
	private final UserAuthorizationService userAuthorizationService;
	
	@Autowired
	public UserAuthorizationApiController(UserAuthorizationService userAuthorizationService) {this.userAuthorizationService = userAuthorizationService;}
	
	@Secured(ADMIN_USER)
	@PostMapping("/save")
	@ResponseStatus(CREATED)
	public BaseJsonObject<?> save(UserAuthorizationFormVO userAuthorizationFormVO) {
		userAuthorizationService.save(userAuthorizationFormVO);
		return responseSuccess();
	}
}
