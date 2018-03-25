package hdfg159.chattogether.controller.api;

import hdfg159.chattogether.annotation.CurrentUser;
import hdfg159.chattogether.domain.Suggestion;
import hdfg159.chattogether.domain.dto.BaseJsonObject;
import hdfg159.chattogether.domain.vo.SuggestionFormVO;
import hdfg159.chattogether.service.SuggestionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import static hdfg159.chattogether.constant.RoleConsts.ADMIN_SUGGESTION;
import static hdfg159.chattogether.constant.RoleConsts.USER;
import static hdfg159.chattogether.util.ResponseUtils.responseSuccess;
import static org.springframework.http.HttpStatus.CREATED;

/**
 * Project:ChatTogether
 * Package:hdfg159.chattogether.controller.api
 * Created by hdfg159 on 18-3-25 下午4:56.
 */
@Slf4j
@RestController
@RequestMapping("/suggestion/api")
public class SuggestionApiController {
	private final SuggestionService suggestionService;
	
	@Autowired
	public SuggestionApiController(SuggestionService suggestionService) {this.suggestionService = suggestionService;}
	
	@Secured(USER)
	@PostMapping("/save")
	@ResponseStatus(CREATED)
	public BaseJsonObject<?> save(@CurrentUser UserDetails userDetails, @Valid SuggestionFormVO suggestionFormVO) {
		suggestionService.save(suggestionFormVO, userDetails.getUsername());
		return responseSuccess();
	}
	
	@Secured(ADMIN_SUGGESTION)
	@PostMapping("/delete")
	public BaseJsonObject<?> delete(Suggestion suggestion) {
		suggestionService.deleteById(suggestion.getId());
		return responseSuccess();
	}
}
