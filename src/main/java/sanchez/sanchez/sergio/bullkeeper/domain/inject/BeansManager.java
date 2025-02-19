package sanchez.sanchez.sergio.bullkeeper.domain.inject;

import java.util.HashSet;
import java.util.Set;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import sanchez.sanchez.sergio.bullkeeper.domain.service.IAlertService;
import sanchez.sanchez.sergio.bullkeeper.domain.service.IAnalysisService;
import sanchez.sanchez.sergio.bullkeeper.domain.service.IAuthenticationService;
import sanchez.sanchez.sergio.bullkeeper.domain.service.IAuthorizationService;
import sanchez.sanchez.sergio.bullkeeper.domain.service.ICommentsService;
import sanchez.sanchez.sergio.bullkeeper.domain.service.IDeletePendingEmailService;
import sanchez.sanchez.sergio.bullkeeper.domain.service.IDeviceGroupsService;
import sanchez.sanchez.sergio.bullkeeper.domain.service.IGuardianService;
import sanchez.sanchez.sergio.bullkeeper.domain.service.IIterationService;
import sanchez.sanchez.sergio.bullkeeper.domain.service.IKidService;
import sanchez.sanchez.sergio.bullkeeper.domain.service.IPasswordResetTokenService;
import sanchez.sanchez.sergio.bullkeeper.domain.service.ISchoolService;
import sanchez.sanchez.sergio.bullkeeper.domain.service.ISocialMediaService;
import sanchez.sanchez.sergio.bullkeeper.domain.service.IAnalysisStatisticsService;
import sanchez.sanchez.sergio.bullkeeper.domain.service.ITerminalService;
import sanchez.sanchez.sergio.bullkeeper.domain.service.ITokenGeneratorService;
import sanchez.sanchez.sergio.bullkeeper.domain.service.IUserSystemService;

/**
 * Beans Manager
 * @author sergiosanchezsanchez
 *
 */
@Component
public class BeansManager {
	
	@Autowired
	private IAlertService alertService;
	
	@Autowired
	private IAnalysisService analysisService;
	
	@Autowired
	private IAuthenticationService authenticationService;
	
	@Autowired
	private IAuthorizationService authorizationService;
	
	@Autowired
	private ICommentsService commentsService;
	
	@Autowired
	private IDeletePendingEmailService deletePendingEmailService;
	
	@Autowired
	private IDeviceGroupsService deviceGroupsService;
	
	@Autowired
	private IIterationService iterationService;
	
	@Autowired
	private IGuardianService guardianService;
	
	@Autowired
	private IPasswordResetTokenService passwordResetTokenService;
	
	@Autowired
	private ISchoolService schoolService;
	
	@Autowired
	private ISocialMediaService socialMediaService;
	
	@Autowired
	private IKidService kidService;
	
	@Autowired
	private IAnalysisStatisticsService statisticsService;
	
	@Autowired
	private ITokenGeneratorService tokenGeneratorService;
	
	@Autowired
	private IUserSystemService userSystemService;
	
	@Autowired
	private ITerminalService terminalService;
	
	@Autowired
	private Set<Injectable> injectables = new HashSet();
	
	

	public IAlertService getAlertService() {
		return alertService;
	}

	public void setAlertService(IAlertService alertService) {
		this.alertService = alertService;
	}

	public IAnalysisService getAnalysisService() {
		return analysisService;
	}

	public void setAnalysisService(IAnalysisService analysisService) {
		this.analysisService = analysisService;
	}

	public IAuthenticationService getAuthenticationService() {
		return authenticationService;
	}

	public void setAuthenticationService(IAuthenticationService authenticationService) {
		this.authenticationService = authenticationService;
	}

	public IAuthorizationService getAuthorizationService() {
		return authorizationService;
	}

	public void setAuthorizationService(IAuthorizationService authorizationService) {
		this.authorizationService = authorizationService;
	}

	public ICommentsService getCommentsService() {
		return commentsService;
	}

	public void setCommentsService(ICommentsService commentsService) {
		this.commentsService = commentsService;
	}

	public IDeletePendingEmailService getDeletePendingEmailService() {
		return deletePendingEmailService;
	}

	public void setDeletePendingEmailService(IDeletePendingEmailService deletePendingEmailService) {
		this.deletePendingEmailService = deletePendingEmailService;
	}

	public IDeviceGroupsService getDeviceGroupsService() {
		return deviceGroupsService;
	}

	public void setDeviceGroupsService(IDeviceGroupsService deviceGroupsService) {
		this.deviceGroupsService = deviceGroupsService;
	}

	public IIterationService getIterationService() {
		return iterationService;
	}

	public void setIterationService(IIterationService iterationService) {
		this.iterationService = iterationService;
	}

	public IGuardianService getParentsService() {
		return guardianService;
	}

	public void setParentsService(IGuardianService parentsService) {
		this.guardianService = parentsService;
	}

	public IPasswordResetTokenService getPasswordResetTokenService() {
		return passwordResetTokenService;
	}

	public void setPasswordResetTokenService(IPasswordResetTokenService passwordResetTokenService) {
		this.passwordResetTokenService = passwordResetTokenService;
	}

	public ISchoolService getSchoolService() {
		return schoolService;
	}

	public void setSchoolService(ISchoolService schoolService) {
		this.schoolService = schoolService;
	}

	public ISocialMediaService getSocialMediaService() {
		return socialMediaService;
	}

	public void setSocialMediaService(ISocialMediaService socialMediaService) {
		this.socialMediaService = socialMediaService;
	}

	public IKidService getSonService() {
		return kidService;
	}

	public void setSonService(IKidService sonService) {
		this.kidService = sonService;
	}

	public IAnalysisStatisticsService getStatisticsService() {
		return statisticsService;
	}

	public void setStatisticsService(IAnalysisStatisticsService statisticsService) {
		this.statisticsService = statisticsService;
	}

	public ITokenGeneratorService getTokenGeneratorService() {
		return tokenGeneratorService;
	}

	public void setTokenGeneratorService(ITokenGeneratorService tokenGeneratorService) {
		this.tokenGeneratorService = tokenGeneratorService;
	}


	public ITerminalService getTerminalService() {
		return terminalService;
	}

	public void setTerminalService(ITerminalService terminalService) {
		this.terminalService = terminalService;
	}

	public IUserSystemService getUserSystemService() {
		return userSystemService;
	}

	public void setUserSystemService(IUserSystemService userSystemService) {
		this.userSystemService = userSystemService;
	}
	
	@PostConstruct
	private void inject() {
	   for (Injectable injectableItem : injectables) {
	       injectableItem.inject(this);
	   }
	}

}
