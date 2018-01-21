package hdfg159.chattogether.annotation;

import org.springframework.security.core.annotation.AuthenticationPrincipal;

import java.lang.annotation.*;

/**
 * Project:ChatTogether
 * Package:hdfg159.chattogether.annotation
 * Created by hdfg159 on 2017/7/9 10:01.
 */
@Target({ElementType.PARAMETER, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@AuthenticationPrincipal
public @interface CurrentUser {}
