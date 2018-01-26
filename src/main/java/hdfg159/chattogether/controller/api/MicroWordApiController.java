package hdfg159.chattogether.controller.api;

import hdfg159.chattogether.annotation.CurrentUser;
import hdfg159.chattogether.domain.MicroWord;
import hdfg159.chattogether.domain.dto.BaseJsonObject;
import hdfg159.chattogether.domain.vo.MicroWordFormVO;
import hdfg159.chattogether.service.MicroWordService;
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

import static hdfg159.chattogether.constant.RoleConsts.ADMIN_MICROWORD;
import static hdfg159.chattogether.constant.RoleConsts.USER;
import static hdfg159.chattogether.util.ResponseUtils.responseFail;
import static hdfg159.chattogether.util.ResponseUtils.responseSuccess;
import static org.springframework.http.HttpStatus.CREATED;

/**
 * Project:ChatTogether
 * Package:hdfg159.chattogether.controller.api
 * Created by hdfg159 on 2017-12-1 11:45.
 */
@Slf4j
@RestController
@RequestMapping("/microword/api")
public class MicroWordApiController {
	private final MicroWordService microWordService;
	
	@Autowired
	public MicroWordApiController(MicroWordService microWordService) {this.microWordService = microWordService;}
	
	@Secured(USER)
	@PostMapping("/save")
	@ResponseStatus(CREATED)
	public BaseJsonObject<?> save(@CurrentUser UserDetails userDetail, @Valid MicroWordFormVO microWordFormVO, Errors errors) {
		if (errors.hasErrors()) {
			return responseFail(errors);
		}
		MicroWord microWord = MicroWord.builder()
				.content(microWordFormVO.getContent())
				.build();
		microWordService.save(userDetail.getUsername(), microWord);
		return responseSuccess();
	}
	
	@Secured(USER)
	@PostMapping("/delete")
	public BaseJsonObject<?> delete(Long id) {
		microWordService.deleteById(id);
		return responseSuccess();
	}
	
	@Secured(ADMIN_MICROWORD)
	@PostMapping("/deleteBatch")
	public BaseJsonObject<?> deleteBatch(Long... microWordIds) {
		microWordService.deleteBatch(microWordIds);
		return responseSuccess();
	}
}
