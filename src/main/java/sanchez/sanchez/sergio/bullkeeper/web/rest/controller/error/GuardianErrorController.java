package sanchez.sanchez.sergio.bullkeeper.web.rest.controller.error;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import sanchez.sanchez.sergio.bullkeeper.exception.EmailAlreadyExistsException;
import sanchez.sanchez.sergio.bullkeeper.exception.GetInformationFromFacebookException;
import sanchez.sanchez.sergio.bullkeeper.exception.GetInformationFromGoogleException;
import sanchez.sanchez.sergio.bullkeeper.exception.GuardianNotFoundException;
import sanchez.sanchez.sergio.bullkeeper.exception.InvalidFacebookIdException;
import sanchez.sanchez.sergio.bullkeeper.exception.NoChildrenFoundForGuardianException;
import sanchez.sanchez.sergio.bullkeeper.exception.NoChildrenFoundForSelfGuardianException;
import sanchez.sanchez.sergio.bullkeeper.exception.NoGuardiansFoundException;
import sanchez.sanchez.sergio.bullkeeper.exception.NoSupervisedChildrenConfirmedFoundException;
import sanchez.sanchez.sergio.bullkeeper.exception.NoSupervisedChildrenNoConfirmedFoundException;
import sanchez.sanchez.sergio.bullkeeper.exception.SupervisedChildrenConfirmedNotFoundException;
import sanchez.sanchez.sergio.bullkeeper.exception.SupervisedChildrenNoConfirmedNotFoundException;
import sanchez.sanchez.sergio.bullkeeper.web.rest.ApiHelper;
import sanchez.sanchez.sergio.bullkeeper.web.rest.controller.BaseController;
import sanchez.sanchez.sergio.bullkeeper.web.rest.response.APIResponse;
import sanchez.sanchez.sergio.bullkeeper.web.rest.response.GuardianResponseCode;

/**
 *
 * @author sergio
 */

@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class GuardianErrorController extends BaseController{
	
    private static Logger logger = LoggerFactory.getLogger(GuardianErrorController.class);
    
    /**
     * 
     * @param badCredentialsException
     * @param request
     * @return
     */
    @ExceptionHandler(BadCredentialsException.class)
    @ResponseBody
    protected ResponseEntity<APIResponse<String>> handleBadCredentialsException(
    		BadCredentialsException badCredentialsException, HttpServletRequest request) {
        return ApiHelper.<String>createAndSendErrorResponseWithHeader(
        		GuardianResponseCode.BAD_CREDENTIALS, HttpStatus.BAD_REQUEST, 
        		messageSourceResolver.resolver("bad.credentials"));
    }
    
    /**
     * 
     * @param usernameNotFoundException
     * @param request
     * @return
     */
    @ExceptionHandler(UsernameNotFoundException.class)
    @ResponseBody
    protected ResponseEntity<APIResponse<String>> handleBadCredentialsException(UsernameNotFoundException usernameNotFoundException, HttpServletRequest request) {
        return ApiHelper.<String>createAndSendErrorResponseWithHeader(
        		GuardianResponseCode.USERNAME_NOT_FOUND, HttpStatus.BAD_REQUEST, 
        		messageSourceResolver.resolver("username.not.found"));
    }
   
    /**
     * 
     * @param disabledException
     * @param request
     * @return
     */
    @ExceptionHandler(DisabledException.class)
    @ResponseBody
    protected ResponseEntity<APIResponse<String>> handleDisabledException(
    		DisabledException disabledException, HttpServletRequest request) {
        return ApiHelper.<String>createAndSendErrorResponseWithHeader(GuardianResponseCode.ACCOUNT_DISABLED, HttpStatus.FORBIDDEN, 
        		messageSourceResolver.resolver("account.disabled"));
    }

    /**
     * 
     * @param guardianNotFoundException
     * @param request
     * @return
     */
    @ExceptionHandler(GuardianNotFoundException.class)
    @ResponseBody
    protected ResponseEntity<APIResponse<String>> handleGuardianNotFoundException(
    		final GuardianNotFoundException guardianNotFoundException, 
    		final HttpServletRequest request) {
    	
        return ApiHelper.<String>createAndSendErrorResponseWithHeader(
        		GuardianResponseCode.GUARDIAN_NOT_FOUND, HttpStatus.NOT_FOUND,
        		messageSourceResolver.resolver("guardian.not.found"));
    }
    
    /**
     * 
     * @param noChildrenFoundForSelfGuardianException
     * @param request
     * @return
     */
    @ExceptionHandler(NoChildrenFoundForSelfGuardianException.class)
    @ResponseBody
    protected ResponseEntity<APIResponse<String>> handleNoChildrenFoundForSelfGuardianException(
    		NoChildrenFoundForSelfGuardianException noChildrenFoundForSelfGuardianException, 
    		HttpServletRequest request) {
    	return ApiHelper.<String>createAndSendErrorResponseWithHeader(
    			GuardianResponseCode.NO_CHILDREN_FOUND_FOR_SELF_GUARDIAN, HttpStatus.NOT_FOUND,
    			messageSourceResolver.resolver("self.guardian.not.children.found"));
    }
    
    /**
     * 
     * @param noChildrenFoundForParentException
     * @param request
     * @return
     */
    @ExceptionHandler(NoChildrenFoundForGuardianException.class)
    @ResponseBody
    protected ResponseEntity<APIResponse<String>> handleNoChildrenFoundForParentException(NoChildrenFoundForGuardianException noChildrenFoundForParentException, HttpServletRequest request) {
    	return ApiHelper.<String>createAndSendErrorResponseWithHeader(
    			GuardianResponseCode.NO_CHILDREN_FOUND_FOR_GUARDIAN, HttpStatus.NOT_FOUND,
    			messageSourceResolver.resolver("guardian.not.children.found"));
    }
    
    /**
     * 
     * @param noGuardianFoundException
     * @param request
     * @return
     */
    @ExceptionHandler(NoGuardiansFoundException.class)
    @ResponseBody
    protected ResponseEntity<APIResponse<String>> handleNoGuardiansFoundException(
    		final NoGuardiansFoundException noGuardianFoundException, 
    		final HttpServletRequest request) {
    	return ApiHelper.<String>createAndSendErrorResponseWithHeader(
    			GuardianResponseCode.GUARDIANS_NOT_FOUND, HttpStatus.NOT_FOUND,
    			messageSourceResolver.resolver("guardians.not.found"));
    }
    
    /**
     * 
     * @param getInformationFromFacebookException
     * @param request
     * @return
     */
    @ExceptionHandler(GetInformationFromFacebookException.class)
    @ResponseBody
    protected ResponseEntity<APIResponse<String>> handleGetInformationFromFacebookException(
    		final GetInformationFromFacebookException getInformationFromFacebookException, 
    		final HttpServletRequest request) {
    	return ApiHelper.<String>createAndSendErrorResponseWithHeader(
    			GuardianResponseCode.GUARDIANS_NOT_FOUND, HttpStatus.NOT_FOUND,
    			messageSourceResolver.resolver("guardian.get.information.from.facebook.failed"));
    }
    
    /**
     * 
     * @param getInformationFromGoogleException
     * @param request
     * @return
     */
    @ExceptionHandler(GetInformationFromGoogleException.class)
    @ResponseBody
    protected ResponseEntity<APIResponse<String>> handleGetInformationFromGoogleException(
    		GetInformationFromGoogleException getInformationFromGoogleException,
    		HttpServletRequest request) {
    	return ApiHelper.<String>createAndSendErrorResponseWithHeader(
    			GuardianResponseCode.GUARDIANS_NOT_FOUND, HttpStatus.NOT_FOUND,
    			messageSourceResolver.resolver("guardian.get.information.from.google.failed"));
    }
    
    /**
     * 
     * @param invalidFacebookIdException
     * @param request
     * @return
     */
    @ExceptionHandler(InvalidFacebookIdException.class)
    @ResponseBody
    protected ResponseEntity<APIResponse<String>> handleInvalidFacebookIdException(
    		InvalidFacebookIdException invalidFacebookIdException, HttpServletRequest request) {
    	return ApiHelper.<String>createAndSendErrorResponseWithHeader(
    			GuardianResponseCode.INVALID_FACEBOOK_ID, HttpStatus.BAD_REQUEST,
    			messageSourceResolver.resolver("guardian.invalid.facebook.id"));
    }
    
    /**
     * 
     * @param emailAlreadyExistsException
     * @param request
     * @return
     */
    @ExceptionHandler(EmailAlreadyExistsException.class)
    @ResponseBody
    protected ResponseEntity<APIResponse<String>> handleEmailAlreadyExistsException(EmailAlreadyExistsException emailAlreadyExistsException, HttpServletRequest request) {
    	return ApiHelper.<String>createAndSendErrorResponseWithHeader(GuardianResponseCode.EMAIL_ALREADY_EXISTS, HttpStatus.NOT_FOUND,
    			messageSourceResolver.resolver("guardian.email.already.exists"));
    }
    
    /**
     * 
     * @param noSupervisedChildrenConfirmedFoundException
     * @param request
     * @return
     */
    @ExceptionHandler(NoSupervisedChildrenConfirmedFoundException.class)
    @ResponseBody
    protected ResponseEntity<APIResponse<String>> handleNoSupervisedChildrenConfirmedFoundException(NoSupervisedChildrenConfirmedFoundException noSupervisedChildrenConfirmedFoundException, HttpServletRequest request) {
    	return ApiHelper.<String>createAndSendErrorResponseWithHeader(GuardianResponseCode.NO_SUPERVISED_CHILDREN_CONFIRMED_FOUND, 
    			HttpStatus.NOT_FOUND, messageSourceResolver.resolver("no.supervised.children.confirmed.found"));
    }
    
    /**
     * 
     * @param noSupervisedChildrenNoConfirmedFoundException
     * @param request
     * @return
     */
    @ExceptionHandler(NoSupervisedChildrenNoConfirmedFoundException.class)
    @ResponseBody
    protected ResponseEntity<APIResponse<String>> handleNoSupervisedChildrenNoConfirmedFoundException(NoSupervisedChildrenNoConfirmedFoundException noSupervisedChildrenNoConfirmedFoundException, HttpServletRequest request) {
    	return ApiHelper.<String>createAndSendErrorResponseWithHeader(GuardianResponseCode.NO_SUPERVISED_CHILDREN_NO_CONFIRMED_FOUND, 
    			HttpStatus.NOT_FOUND, messageSourceResolver.resolver("no.supervised.children.no.confirmed.found"));
    }
    
    /**
     * 
     * @param supervisedChildrenConfirmedNotFoundException
     * @param request
     * @return
     */
    @ExceptionHandler(SupervisedChildrenConfirmedNotFoundException.class)
    @ResponseBody
    protected ResponseEntity<APIResponse<String>> handleSupervisedChildrenConfirmedNotFoundException(SupervisedChildrenConfirmedNotFoundException supervisedChildrenConfirmedNotFoundException, HttpServletRequest request) {
    	return ApiHelper.<String>createAndSendErrorResponseWithHeader(GuardianResponseCode.SUPERVISED_CHILDREN_CONFIRMED_NOT_FOUND_EXCEPTION, 
    			HttpStatus.NOT_FOUND, messageSourceResolver.resolver("supervised.children.confirmed.not.found.exception"));
    }
    
    
    /**
     * 
     * @param supervisedChildrenConfirmedNotFoundException
     * @param request
     * @return
     */
    @ExceptionHandler(SupervisedChildrenNoConfirmedNotFoundException.class)
    @ResponseBody
    protected ResponseEntity<APIResponse<String>> handleSupervisedChildrenNoConfirmedNotFoundException(SupervisedChildrenNoConfirmedNotFoundException supervisedChildrenNoConfirmedNotFoundException, HttpServletRequest request) {
    	return ApiHelper.<String>createAndSendErrorResponseWithHeader(GuardianResponseCode.SUPERVISED_CHILDREN_NOT_CONFIRMED_NOT_FOUND_EXCEPTION, 
    			HttpStatus.NOT_FOUND, messageSourceResolver.resolver("supervised.children.no.confirmed.not.found"));
    }
    
    
    
    /**
     * 
     */
    @PostConstruct
    protected void init(){
    	Assert.notNull(messageSourceResolver, "Message Source Resolver can not be null");
    }
}
