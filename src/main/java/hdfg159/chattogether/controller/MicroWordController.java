package hdfg159.chattogether.controller;

import hdfg159.chattogether.annotation.CurrentUser;
import hdfg159.chattogether.domain.MicroWord;
import hdfg159.chattogether.domain.vo.MicroWordDetailVO;
import hdfg159.chattogether.service.MicroWordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import static hdfg159.chattogether.constant.RoleConsts.USER;

/**
 * Project:ChatTogether
 * Package:hdfg159.chattogether.controller
 * Created by hdfg159 on 2017-12-4 16:16.
 */
@Controller
@RequestMapping("/microword")
public class MicroWordController {
	private final MicroWordService microWordService;
	
	@Autowired
	public MicroWordController(MicroWordService microWordService) {this.microWordService = microWordService;}
	
	@GetMapping("/details/{microWordId}")
	public String showMicroWordDetails(Model model, @PathVariable Long microWordId, @PageableDefault(sort = "createTime", direction = Sort.Direction.DESC) Pageable commentPageable) {
		MicroWordDetailVO microWordDetailVO = microWordService.findMicroWordDetailById(microWordId, commentPageable);
		model.addAttribute("microWordDetailVO", microWordDetailVO);
		return "microword/details";
	}
	
	@GetMapping("/{username}")
	public String showUserMicroWords(Model model, @PathVariable String username, @PageableDefault(sort = "createTime", direction = Sort.Direction.DESC) Pageable pageable) {
		model.addAttribute("microWordList", microWordService.findAllByUsername(username, pageable));
		model.addAttribute("username", username);
		return "microword/list";
	}
	
	@Secured(USER)
	@GetMapping("/userfriend")
	public String showUserFriendMicroWords(Model model, @CurrentUser UserDetails userDetail, @PageableDefault(sort = "createTime", direction = Sort.Direction.DESC) Pageable pageable) {
		Page<MicroWord> microWords = microWordService.findAllByUserFriend(userDetail.getUsername(), pageable);
		model.addAttribute("microWords", microWords);
		return "index";
	}
}
