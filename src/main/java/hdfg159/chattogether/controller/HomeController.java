package hdfg159.chattogether.controller;

import hdfg159.chattogether.domain.MicroWord;
import hdfg159.chattogether.domain.User;
import hdfg159.chattogether.domain.vo.HomeSearchFormVO;
import hdfg159.chattogether.domain.vo.UserFormVO;
import hdfg159.chattogether.service.MicroWordService;
import hdfg159.chattogether.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

/**
 * Project:ChatTogether
 * Package:hdfg159.chattogether.controller
 * Created by hdfg159 on 2017-11-25 17:37.
 */
@Slf4j
@Controller
@RequestMapping("/")
public class HomeController {
	private final UserService userService;
	private final MicroWordService microWordService;
	
	@Autowired
	public HomeController(UserService userService, MicroWordService microWordService) {
		this.userService = userService;
		this.microWordService = microWordService;
	}
	
	@PostMapping("/register")
	public String processRegistration(@Valid UserFormVO userFormVO, Errors errors) {
		if (errors.hasErrors()) {
			return "register";
		}
		User user = User.builder()
				.username(userFormVO.getUsername())
				.password(userFormVO.getPassword())
				.build();
		userService.save(user);
		return "redirect:/";
	}
	
	@GetMapping("/register")
	public String forwardRegistration(Model model) {
		model.addAttribute("userForm", new UserFormVO());
		return "register";
	}
	
	@GetMapping("/")
	public String showAllMicroWords(Model model, @PageableDefault(sort = "createTime", direction = Sort.Direction.DESC) Pageable pageable) {
		Page<MicroWord> microWords = microWordService.findAll(pageable);
		model.addAttribute("microWords", microWords);
		return "index";
	}
	
	@GetMapping("/search")
	public String search(Model model, HomeSearchFormVO homeSearchFormVO, @PageableDefault Pageable pageable) {
		switch (homeSearchFormVO.getSearchItem()) {
			case "username":
				Page<User> users = userService.findByUsernameContaining(homeSearchFormVO.getSearchContent(), pageable);
				model.addAttribute("users", users);
				return "user/search";
			case "microWord":
				Page<MicroWord> microWords = microWordService.findAllByContentContaining(homeSearchFormVO.getSearchContent(), pageable);
				model.addAttribute("microWords", microWords);
				model.addAttribute("isSearchMicroWordsResult", true);
				model.addAttribute("homeSearchFormVO", homeSearchFormVO);
				return "index";
			default:
				break;
		}
		return "";
	}
}
