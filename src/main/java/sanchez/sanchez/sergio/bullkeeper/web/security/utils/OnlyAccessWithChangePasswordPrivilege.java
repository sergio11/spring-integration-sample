package sanchez.sanchez.sergio.bullkeeper.web.security.utils;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.security.access.prepost.PreAuthorize;

/**
 *
 * @author sergio
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@PreAuthorize("@authorizationService.hasChangePasswordPrivilege()")
public @interface OnlyAccessWithChangePasswordPrivilege {}
