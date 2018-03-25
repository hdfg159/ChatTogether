package hdfg159.chattogether.controller;

import hdfg159.chattogether.service.SuggestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import static hdfg159.chattogether.constant.RoleConsts.USER;

/**
 * Project:ChatTogether
 * Package:hdfg159.chattogether.controller
 * Created by hdfg159 on 18-3-25 下午5:09.
 */
@Controller
@RequestMapping("/suggestion")
public class SuggestionController {
	private final SuggestionService suggestionService;
	
	@Autowired
	public SuggestionController(SuggestionService suggestionService) {this.suggestionService = suggestionService;}
	
	@Secured(USER)
	@GetMapping("/all")
	public String showAllSuggestions(Model model, @PageableDefault(sort = "createTime", direction = Sort.Direction.DESC) Pageable pageable) {
		model.addAttribute("suggestions", suggestionService.findAll(pageable));
		return "system/suggestion";
	}
}
