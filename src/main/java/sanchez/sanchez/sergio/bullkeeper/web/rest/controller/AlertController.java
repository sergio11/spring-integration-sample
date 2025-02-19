package sanchez.sanchez.sergio.bullkeeper.web.rest.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedResources;
import org.springframework.hateoas.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.google.common.collect.Iterables;
import java.util.Date;
import javax.annotation.PostConstruct;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import sanchez.sanchez.sergio.bullkeeper.domain.service.IAlertService;
import sanchez.sanchez.sergio.bullkeeper.exception.NoAlertsFoundException;
import sanchez.sanchez.sergio.bullkeeper.exception.NoAlertsStatisticsForThisPeriodException;
import sanchez.sanchez.sergio.bullkeeper.exception.SocialMediaNotFoundException;
import sanchez.sanchez.sergio.bullkeeper.persistence.constraints.group.ICommonSequence;
import sanchez.sanchez.sergio.bullkeeper.persistence.entity.AlertLevelEnum;
import sanchez.sanchez.sergio.bullkeeper.util.ValidList;
import sanchez.sanchez.sergio.bullkeeper.web.dto.request.AddAlertDTO;
import sanchez.sanchez.sergio.bullkeeper.web.dto.response.AlertDTO;
import sanchez.sanchez.sergio.bullkeeper.web.dto.response.AlertsStatisticsDTO;
import sanchez.sanchez.sergio.bullkeeper.web.rest.ApiHelper;
import sanchez.sanchez.sergio.bullkeeper.web.rest.response.APIResponse;
import sanchez.sanchez.sergio.bullkeeper.web.rest.response.AlertResponseCode;
import sanchez.sanchez.sergio.bullkeeper.web.security.userdetails.CommonUserDetailsAware;
import sanchez.sanchez.sergio.bullkeeper.web.security.utils.CurrentUser;
import sanchez.sanchez.sergio.bullkeeper.web.security.utils.OnlyAccessForAdmin;
import sanchez.sanchez.sergio.bullkeeper.web.security.utils.OnlyAccessForGuardian;
import springfox.documentation.annotations.ApiIgnore;


/**
 * Alert Controller
 * @author sergiosanchezsanchez
 *
 */
@RestController("RestAlertsController")
@Validated
@RequestMapping("/api/v1/alerts/")
@Api(tags = "alerts", value = "/alerts/", 
description = "Administration of user alerts",
produces = "application/json")
public class AlertController extends BaseController {

    private static Logger logger = LoggerFactory.getLogger(AlertController.class);

    private final IAlertService alertService;

    /**
     * 
     * @param alertService
     */
    public AlertController(IAlertService alertService) {
        super();
        this.alertService = alertService;
    }

    /**
     * Get All Alerts
     * @param pageable
     * @param pagedAssembler
     * @param delivered
     * @return
     * @throws Throwable
     */
    @RequestMapping(value = {"/", "/all"}, method = RequestMethod.GET)
    @OnlyAccessForAdmin
    @ApiOperation(value = "GET_ALL_ALERT", nickname = "GET_ALL_ALERT", notes = "Get all alerts in the system",
            response = PagedResources.class)
    public ResponseEntity<APIResponse<PagedResources<Resource<AlertDTO>>>> getAllAlerts(
            @ApiIgnore @PageableDefault Pageable pageable,
            @ApiIgnore PagedResourcesAssembler<AlertDTO> pagedAssembler,
            @ApiParam(name = "delivered", value = "Notificaciones entregadas", required = false)
            @RequestParam(name = "delivered", defaultValue = "false", required = false) Boolean delivered) throws Throwable {

        final Page<AlertDTO> alertsPage = alertService.findPaginated(delivered, pageable);

        if (alertsPage.getNumberOfElements() == 0) {
            throw new NoAlertsFoundException();
        }

        return ApiHelper.<PagedResources<Resource<AlertDTO>>>createAndSendResponse(AlertResponseCode.ALL_ALERTS,
                HttpStatus.OK, pagedAssembler.toResource(alertsPage));
    }

    /**
     * Get All Self Alerts Paginated
     * @param pageable
     * @param pagedAssembler
     * @param selfParent
     * @param delivered
     * @return
     * @throws Throwable
     */
    @RequestMapping(value = {"/self"}, method = RequestMethod.GET)
    @OnlyAccessForGuardian
    @ApiOperation(value = "GET_ALL_SELF_ALERT_PAGINATED", nickname = "GET_ALL_SELF_ALERT_PAGINATED", notes = "Get all alerts for the currently authenticated user",
            response = PagedResources.class)
    public ResponseEntity<APIResponse<PagedResources<Resource<AlertDTO>>>> getAllSelfAlerts(
            @ApiIgnore @PageableDefault Pageable pageable,
            @ApiIgnore PagedResourcesAssembler<AlertDTO> pagedAssembler,
            @ApiIgnore @CurrentUser CommonUserDetailsAware<ObjectId> selfParent,
            @ApiParam(name = "delivered", value = "Notificaciones entregadas", required = false)
            @RequestParam(name = "delivered", defaultValue = "false", required = false) Boolean delivered) throws Throwable {

        Page<AlertDTO> alertsPage = alertService.findByGuardianPaginated(selfParent.getUserId(), delivered, pageable);

        if (alertsPage.getNumberOfElements() == 0) {
            throw new NoAlertsFoundException();
        }

        return ApiHelper.<PagedResources<Resource<AlertDTO>>>createAndSendResponse(AlertResponseCode.ALL_SELF_ALERTS,
                HttpStatus.OK, pagedAssembler.toResource(alertsPage));
    }

    
    /**
     * Get All Self Alerts
     * @param selfParent
     * @param delivered
     * @return
     * @throws Throwable
     */
    @RequestMapping(value = {"/self/all"}, method = RequestMethod.GET)
    @OnlyAccessForGuardian
    @ApiOperation(value = "GET_ALL_SELF_ALERT", nickname = "GET_ALL_SELF_ALERT", notes = "Get all alerts for the currently authenticated user",
            response = PagedResources.class)
    public ResponseEntity<APIResponse<Iterable<AlertDTO>>> getAllSelfAlerts(
            @ApiIgnore @CurrentUser CommonUserDetailsAware<ObjectId> selfParent,
            @ApiParam(name = "delivered", value = "Notificaciones entregadas", required = false)
            @RequestParam(name = "delivered", defaultValue = "false", required = false) Boolean delivered) throws Throwable {

        Iterable<AlertDTO> alertsPage = alertService.findByGuardian(selfParent.getUserId(), delivered);

        if (Iterables.size(alertsPage) == 0) {
            throw new NoAlertsFoundException();
        }

        return ApiHelper.<Iterable<AlertDTO>>createAndSendResponse(AlertResponseCode.ALL_SELF_ALERTS,
                HttpStatus.OK, alertsPage);
    }

  

    /**
     * Get Info Alerts
     * @param pageable
     * @param pagedAssembler
     * @param selfParent
     * @param delivered
     * @return
     * @throws Throwable
     */
    @RequestMapping(value = "/info", method = RequestMethod.GET)
    @OnlyAccessForGuardian
    @ApiOperation(value = "GET_INFO_ALERTS", nickname = "GET_INFO_ALERTS", notes = "Get all info alerts for the currently authenticated user",
            response = PagedResources.class)
    public ResponseEntity<APIResponse<PagedResources<Resource<AlertDTO>>>> getInfoAlerts(
            @ApiIgnore @PageableDefault Pageable pageable,
            @ApiIgnore PagedResourcesAssembler<AlertDTO> pagedAssembler,
            @CurrentUser CommonUserDetailsAware<ObjectId> selfParent,
            @ApiParam(name = "delivered", value = "Notificaciones entregadas", required = false)
            @RequestParam(name = "delivered", defaultValue = "false", required = false) Boolean delivered) throws Throwable {

        Page<AlertDTO> alertsPage = alertService.findByGuardianPaginated(selfParent.getUserId(), AlertLevelEnum.INFO, delivered, pageable);

        if (alertsPage.getNumberOfElements() == 0) {
            throw new NoAlertsFoundException();
        }

        return ApiHelper.<PagedResources<Resource<AlertDTO>>>createAndSendResponse(AlertResponseCode.ALL_INFO_ALERTS,
                HttpStatus.OK, pagedAssembler.toResource(alertsPage));
    }

    /**
     * Get Warning Alerts
     * @param pageable
     * @param pagedAssembler
     * @param selfParent
     * @param delivered
     * @return
     * @throws Throwable
     */
    @RequestMapping(value = "/warning", method = RequestMethod.GET)
    @OnlyAccessForGuardian
    @ApiOperation(value = "GET_WARNING_ALERTS", nickname = "GET_WARNING_ALERTS", notes = "Get warning alerts for the currently authenticated user",
            response = PagedResources.class)
    public ResponseEntity<APIResponse<PagedResources<Resource<AlertDTO>>>> getWarningAlerts(
            @ApiIgnore @PageableDefault Pageable pageable,
            @ApiIgnore PagedResourcesAssembler<AlertDTO> pagedAssembler,
            @ApiIgnore @CurrentUser CommonUserDetailsAware<ObjectId> selfParent,
            @ApiParam(name = "delivered", value = "Notificaciones entregadas", required = false)
            @RequestParam(name = "delivered", defaultValue = "false", required = false) Boolean delivered) throws Throwable {

        Page<AlertDTO> alertsPage = alertService.findByGuardianPaginated(selfParent.getUserId(), AlertLevelEnum.WARNING, delivered, pageable);

        if (alertsPage.getNumberOfElements() == 0) {
            throw new NoAlertsFoundException();
        }

        return ApiHelper.<PagedResources<Resource<AlertDTO>>>createAndSendResponse(AlertResponseCode.ALL_WARNING_ALERTS,
                HttpStatus.OK, pagedAssembler.toResource(alertsPage));
    }

    /**
     * Get Danger Alerts
     * @param pageable
     * @param pagedAssembler
     * @param selfParent
     * @param delivered
     * @return
     * @throws Throwable
     */
    @RequestMapping(value = "/danger", method = RequestMethod.GET)
    @OnlyAccessForGuardian
    @ApiOperation(value = "GET_DANGER_ALERTS", nickname = "GET_DANGER_ALERTS", notes = "Get danger alerts for the currently authenticated user",
            response = PagedResources.class)
    public ResponseEntity<APIResponse<PagedResources<Resource<AlertDTO>>>> getDangerAlerts(
            @ApiIgnore @PageableDefault Pageable pageable,
            @ApiIgnore PagedResourcesAssembler<AlertDTO> pagedAssembler,
            @ApiIgnore @CurrentUser CommonUserDetailsAware<ObjectId> selfParent,
            @ApiParam(name = "delivered", value = "Notificaciones entregadas", required = false)
            @RequestParam(name = "delivered", defaultValue = "false", required = false) Boolean delivered) throws Throwable {

        Page<AlertDTO> alertsPage = alertService.findByGuardianPaginated(selfParent.getUserId(), AlertLevelEnum.DANGER, delivered, pageable);

        if (alertsPage.getNumberOfElements() == 0) {
            throw new NoAlertsFoundException();
        }

        return ApiHelper.<PagedResources<Resource<AlertDTO>>>createAndSendResponse(AlertResponseCode.ALL_DANGER_ALERTS,
                HttpStatus.OK, pagedAssembler.toResource(alertsPage));
    }

    /**
     * Get Success Alerts
     * @param pageable
     * @param pagedAssembler
     * @param selfParent
     * @param delivered
     * @return
     * @throws Throwable
     */
    @RequestMapping(value = "/success", method = RequestMethod.GET)
    @OnlyAccessForGuardian
    @ApiOperation(value = "GET_SUCCESS_ALERTS", nickname = "GET_SUCCESS_ALERTS", notes = "Get success alerts for the currently authenticated user",
            response = PagedResources.class)
    public ResponseEntity<APIResponse<PagedResources<Resource<AlertDTO>>>> getSuccessAlerts(
            @ApiIgnore @PageableDefault Pageable pageable,
            @ApiIgnore PagedResourcesAssembler<AlertDTO> pagedAssembler,
            @ApiIgnore @CurrentUser CommonUserDetailsAware<ObjectId> selfParent,
            @ApiParam(name = "delivered", value = "Notificaciones entregadas", required = false)
            @RequestParam(name = "delivered", defaultValue = "false", required = false) Boolean delivered) throws Throwable {

        Page<AlertDTO> alertsPage = alertService.findByGuardianPaginated(selfParent.getUserId(), AlertLevelEnum.SUCCESS, delivered, pageable);

        if (alertsPage.getNumberOfElements() == 0) {
            throw new NoAlertsFoundException();
        }

        return ApiHelper.<PagedResources<Resource<AlertDTO>>>createAndSendResponse(AlertResponseCode.ALL_DANGER_ALERTS,
                HttpStatus.OK, pagedAssembler.toResource(alertsPage));
    }
    
    /**
     * Get Alerts Statistics
     * @param selfParent
     * @param identities
     * @param from
     * @return
     * @throws Throwable
     */
    @RequestMapping(value = {"/statistics/alerts"}, method = RequestMethod.GET)
    @OnlyAccessForGuardian
    @ApiOperation(value = "GET_ALERTS_STATISTICS", nickname = "GET_ALERTS_STATISTICS", notes = "Get Alerts Statistics",
            response = AlertsStatisticsDTO.class)
    public ResponseEntity<APIResponse<AlertsStatisticsDTO>> getAlertsStatistics(
            @ApiIgnore @CurrentUser CommonUserDetailsAware<ObjectId> selfParent,
            @ApiParam(name = "identities", value = "Children's Identifiers", required = false)
            	@RequestParam(name="identities" , required=false)
            		ValidList<ObjectId> identities,
            @ApiParam(name = "days_ago", value = "Days limit", required = false)
    			@RequestParam(name = "days_ago", defaultValue = "1", required = false) 
            	Date from) throws Throwable {
    	
    	AlertsStatisticsDTO alertsStatisticsDTO = alertService.getAlertsStatistics(selfParent.getUserId(), identities, from);
    	
    	if(alertsStatisticsDTO.getAlerts().isEmpty())
    		throw new NoAlertsStatisticsForThisPeriodException(from);
    	
        return ApiHelper.<AlertsStatisticsDTO>createAndSendResponse(AlertResponseCode.ALERTS_STATISTICS,
                HttpStatus.OK, alertsStatisticsDTO);
    }
    

    @PostConstruct
    protected void init() {
        Assert.notNull(alertService, "Alert Service cannot be a null");
    }
}
