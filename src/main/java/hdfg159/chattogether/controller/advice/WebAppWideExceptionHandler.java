package hdfg159.chattogether.controller.advice;

import hdfg159.chattogether.domain.vo.CustomExceptionVO;
import hdfg159.chattogether.exception.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import static org.apache.commons.lang3.exception.ExceptionUtils.getMessage;
import static org.apache.commons.lang3.exception.ExceptionUtils.getStackTrace;
import static org.springframework.http.HttpStatus.*;

/**
 * Project:ChatTogether
 * Package:hdfg159.chattogether.controller.advice
 * Created by hdfg159 on 2017/7/4 16:36.
 */
@Slf4j
@ControllerAdvice
public class WebAppWideExceptionHandler {
	private static final String VIEW = "error/error";
	
	@ExceptionHandler(UserNotFoundException.class)
	@ResponseStatus(value = NOT_FOUND)
	public ModelAndView userNotFoundHandler(Exception e) {
		return getModelAndView(e);
	}
	
	private ModelAndView getModelAndView(Exception e) {
		CustomExceptionVO customException = new CustomExceptionVO(getMessage(e), getStackTrace(e));
		log.error(customException.toString());
		
		ModelAndView mav = new ModelAndView();
		mav.addObject("exception", customException);
		mav.setViewName(VIEW);
		return mav;
	}
	
	@ExceptionHandler(UserExistException.class)
	@ResponseStatus(value = CONFLICT)
	public ModelAndView userExistHandler(Exception e) {
		return getModelAndView(e);
	}
	
	@ExceptionHandler(UserFriendExistException.class)
	@ResponseStatus(value = CONFLICT)
	public ModelAndView userFriendExistExceptionHandler(Exception e) {
		return getModelAndView(e);
	}
	
	@ExceptionHandler(UserAccountStateNotFoundException.class)
	@ResponseStatus(value = NOT_FOUND)
	public ModelAndView userAccountStateNotFoundExceptionHandler(Exception e) {
		return getModelAndView(e);
	}
	
	@ExceptionHandler(UserFriendNotFoundException.class)
	@ResponseStatus(value = NOT_FOUND)
	public ModelAndView userFriendNotFoundExceptionHandler(Exception e) {
		return getModelAndView(e);
	}
	
	@ExceptionHandler(UserPermissionNotFoundException.class)
	@ResponseStatus(value = NOT_FOUND)
	public ModelAndView userPermissionNotFoundHandler(Exception e) {
		return getModelAndView(e);
	}
	
	@ExceptionHandler(MicroWordAgreeExistException.class)
	@ResponseStatus(value = CONFLICT)
	public ModelAndView microWordAgreeExistHandler(Exception e) {
		return getModelAndView(e);
	}
	
	@ExceptionHandler(MicroWordCommentAgreeExistException.class)
	@ResponseStatus(value = CONFLICT)
	public ModelAndView microWordCommentAgreeExistHandler(Exception e) {
		return getModelAndView(e);
	}
	
	@ExceptionHandler(MicroWordNotFoundException.class)
	@ResponseStatus(value = NOT_FOUND)
	public ModelAndView microWordNotFoundHandler(Exception e) {
		return getModelAndView(e);
	}
	
	@ExceptionHandler(MicroWordCommentNotFoundException.class)
	@ResponseStatus(value = NOT_FOUND)
	public ModelAndView microWordCommentNotFoundHandler(Exception e) {
		return getModelAndView(e);
	}
	
	@ExceptionHandler(PasswordNotMatchException.class)
	@ResponseStatus(value = NOT_ACCEPTABLE)
	public ModelAndView passwordNotMatchExceptionHandler(Exception e) {
		return getModelAndView(e);
	}
	
	@ExceptionHandler(UserProfileNotFoundException.class)
	@ResponseStatus(value = NOT_FOUND)
	public ModelAndView userProfileNotFoundExceptionHandler(Exception e) {
		return getModelAndView(e);
	}
	
	@ResponseStatus(value = INTERNAL_SERVER_ERROR)
	@ExceptionHandler(Exception.class)
	public ModelAndView allExceptionHandler(Exception e) {
		return getModelAndView(e);
	}
}
