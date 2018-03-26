package hdfg159.chattogether.controller;

import com.google.code.kaptcha.Producer;
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
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.awt.image.BufferedImage;
import java.io.IOException;

import static com.google.code.kaptcha.Constants.KAPTCHA_SESSION_KEY;
import static org.apache.commons.lang3.StringUtils.equalsIgnoreCase;

/**
 * Project:ChatTogether
 * Package:hdfg159.chattogether.controller
 * Created by hdfg159 on 2017-11-25 17:37.
 */
@Slf4j
@Controller
@RequestMapping("/")
@SessionAttributes(value = {KAPTCHA_SESSION_KEY})
public class HomeController {
	private final UserService userService;
	private final MicroWordService microWordService;
	private final Producer kaptchaProducer;
	
	@Autowired
	public HomeController(UserService userService, MicroWordService microWordService, Producer kaptchaProducer) {
		this.userService = userService;
		this.microWordService = microWordService;
		this.kaptchaProducer = kaptchaProducer;
	}
	
	@PostMapping("/register")
	public String processRegistration(@ModelAttribute(KAPTCHA_SESSION_KEY) String validCode, Model model, @Valid UserFormVO userFormVO, Errors errors) {
		boolean isValidCodeSuccess = isValidCodeSuccess(validCode, userFormVO);
		if (!isValidCodeSuccess || errors.hasErrors()) {
			if (!isValidCodeSuccess) {
				model.addAttribute("validCodeError", "验证码错误");
			}
			return "register";
		}
		User user = User.builder()
				.username(userFormVO.getUsername())
				.password(userFormVO.getPassword())
				.build();
		userService.save(user);
		return "redirect:/";
	}
	
	private boolean isValidCodeSuccess(@ModelAttribute(KAPTCHA_SESSION_KEY) String validCode, @Valid UserFormVO userFormVO) {
		return equalsIgnoreCase(validCode, userFormVO.getValidCode());
	}
	
	@GetMapping("/register")
	public String forwardRegistration(Model model) {
		model.addAttribute("userFormVO", new UserFormVO());
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
				model.addAttribute("homeSearchFormVO", homeSearchFormVO);
				return "user/search";
			case "microWord":
				Page<MicroWord> microWords = microWordService.findAllByContentContaining(homeSearchFormVO.getSearchContent(), pageable);
				model.addAttribute("microWords", microWords);
				model.addAttribute("homeSearchFormVO", homeSearchFormVO);
				return "index";
			default:
				break;
		}
		return "";
	}
	
	@GetMapping("/validcode")
	public void getVaildCode(Model model, HttpServletResponse response) {
		response.setDateHeader("Expires", 0);
		response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
		response.addHeader("Cache-Control", "post-check=0, pre-check=0");
		response.setHeader("Pragma", "no-cache");
		response.setContentType("image/png");
		String capText = kaptchaProducer.createText();
		model.addAttribute(KAPTCHA_SESSION_KEY, capText);
		BufferedImage bi = kaptchaProducer.createImage(capText);
		try (ServletOutputStream out = response.getOutputStream()) {
			ImageIO.write(bi, "png", out);
			out.flush();
		} catch (IOException e) {
			log.error("生成验证码失败！");
		}
	}
}
