package hdfg159.chattogether.controller.api;

import hdfg159.chattogether.annotation.CurrentUser;
import hdfg159.chattogether.domain.ao.ProfilePhotoAO;
import hdfg159.chattogether.domain.dto.BaseJsonObject;
import hdfg159.chattogether.service.UserProfileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

import static hdfg159.chattogether.constant.RoleConsts.USER;
import static hdfg159.chattogether.util.ResponseUtils.responseSuccess;
import static org.springframework.http.HttpStatus.CREATED;

/**
 * Project:ChatTogether
 * Package:hdfg159.chattogether.controller.api
 * Created by hdfg159 on 2018-1-25 17:54.
 */
@Slf4j
@RestController
@RequestMapping("/userprofile/api")
public class UserProfileApiController {
	private final UserProfileService userProfileService;
	
	@Autowired
	public UserProfileApiController(UserProfileService userProfileService) {this.userProfileService = userProfileService;}
	
	@Secured(USER)
	@PostMapping("/saveProfilePhoto")
	@ResponseStatus(CREATED)
	public BaseJsonObject<?> saveProfilePhoto(MultipartFile profilePhoto, @CurrentUser UserDetails userDetail, HttpServletRequest request) {
		ProfilePhotoAO profilePhotoAO = ProfilePhotoAO.builder()
				.webRootPath(request.getServletContext().getRealPath(""))
				.profilePhoto(profilePhoto)
				.username(userDetail.getUsername())
				.build();
		userProfileService.saveProfilePhoto(profilePhotoAO);
		return responseSuccess();
	}
}
