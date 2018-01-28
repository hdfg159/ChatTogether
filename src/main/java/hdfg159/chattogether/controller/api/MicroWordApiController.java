package hdfg159.chattogether.controller.api;

import hdfg159.chattogether.annotation.CurrentUser;
import hdfg159.chattogether.domain.MicroWord;
import hdfg159.chattogether.domain.ao.MicroWordAO;
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
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.IOException;
import java.io.InputStream;

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
	public BaseJsonObject<?> save(@CurrentUser UserDetails userDetail, @Valid MicroWordFormVO microWordFormVO, MultipartFile[] pictures, Errors errors, HttpServletRequest request) throws IOException {
		for (MultipartFile picture : pictures) {
			InputStream file = picture.getInputStream();
			if (!picture.isEmpty() && ImageIO.read(file) == null) {
				return responseFail("不能上传非图片格式类型文件");
			}
		}
		MicroWord microWord = MicroWord.builder()
				.content(microWordFormVO.getContent())
				.build();
		MicroWordAO microWordAO = MicroWordAO.builder()
				.microWord(microWord)
				.pictures(pictures)
				.username(userDetail.getUsername())
				.webRootPath(request.getServletContext().getRealPath(""))
				.build();
		microWordService.save(microWordAO);
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
