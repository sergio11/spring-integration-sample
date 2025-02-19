package sanchez.sanchez.sergio.bullkeeper.web.rest.controller;

import java.util.Optional;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedResources;
import org.springframework.hateoas.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import sanchez.sanchez.sergio.bullkeeper.domain.service.ISocialMediaService;
import sanchez.sanchez.sergio.bullkeeper.exception.ResourceNotFoundException;
import sanchez.sanchez.sergio.bullkeeper.exception.SocialMediaNotFoundException;
import sanchez.sanchez.sergio.bullkeeper.persistence.constraints.ValidObjectId;
import sanchez.sanchez.sergio.bullkeeper.web.dto.response.SocialMediaDTO;
import sanchez.sanchez.sergio.bullkeeper.web.rest.ApiHelper;
import sanchez.sanchez.sergio.bullkeeper.web.rest.hal.ISocialMediaHAL;
import sanchez.sanchez.sergio.bullkeeper.web.rest.response.APIResponse;
import sanchez.sanchez.sergio.bullkeeper.web.rest.response.SocialMediaResponseCode;
import sanchez.sanchez.sergio.bullkeeper.web.security.utils.OnlyAccessForAdmin;
import springfox.documentation.annotations.ApiIgnore;

/**
 * Social Media Contoller
 * @author sergiosanchezsanchez
 *
 */
@RestController("RestSocialMediaController")
@Validated
@RequestMapping("/api/v1/social/")
@Api(tags = "social", value = "/social/", description = "Manejo de la información de los medios sociales registrados", produces = "application/json")
public class SocialMediaController extends BaseController implements ISocialMediaHAL {

    private static Logger logger = LoggerFactory.getLogger(SocialMediaController.class);
    
    private final ISocialMediaService socialMediaService;

    /**
     * 
     * @param socialMediaService
     */
    public SocialMediaController(ISocialMediaService socialMediaService) {
        this.socialMediaService = socialMediaService;
    }
    
    
    /**
     * 
     * @param pageable
     * @param pagedAssembler
     * @return
     * @throws Throwable
     */
    @RequestMapping(value = { "/", "/all" }, method = RequestMethod.GET)
    @OnlyAccessForAdmin
    @ApiOperation(value = "GET_ALL_SOCIAL_MEDIA", nickname = "GET_ALL_SOCIAL_MEDIA", 
            notes = "Get all Social Media", response = PagedResources.class)
    public ResponseEntity<APIResponse<PagedResources<Resource<SocialMediaDTO>>>> getAllSocialMedia(
    		@ApiIgnore @PageableDefault Pageable pageable, 
    		@ApiIgnore PagedResourcesAssembler<SocialMediaDTO> pagedAssembler) throws Throwable {
        logger.debug("Get all Social Media");
        return Optional.ofNullable(socialMediaService.findPaginated(pageable))
                .map(socialMediaPage -> addLinksToSocialMedia(socialMediaPage))
                .map(socialMediaPage -> pagedAssembler.toResource(socialMediaPage))
                .map(socialMediaPageResource -> ApiHelper.<PagedResources<Resource<SocialMediaDTO>>>createAndSendResponse(SocialMediaResponseCode.ALL_SOCIAL_MEDIA, 
                		HttpStatus.OK, socialMediaPageResource))
                .orElseThrow(() -> { throw new ResourceNotFoundException(); });
    }
    
    /**
     * 
     * @param id
     * @return
     * @throws Throwable
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @OnlyAccessForAdmin
    @ApiOperation(value = "GET_SOCIAL_MEDIA_BY_ID", nickname = "GET_SOCIAL_MEDIA_BY_USER_ID", notes = "Get Social Madia By User Id",
            response = SocialMediaDTO.class)
    public ResponseEntity<APIResponse<SocialMediaDTO>> getSocialMediaById(
    		@ApiParam(name = "id", value = "Social Media Identifier", required = true)
    			@Valid @ValidObjectId(message = "{id.not.valid}")
             		@PathVariable String id) throws Throwable {
        logger.debug("Get Social Media by Id " + id);
        return Optional.ofNullable(socialMediaService.getSocialMediaById(id))
                .map(socialMediaResource -> addLinksToSocialMedia(socialMediaResource))
                .map(socialMediaResource -> ApiHelper.<SocialMediaDTO>createAndSendResponse(SocialMediaResponseCode.SOCIAL_MEDIA_BY_CHILD, 
                		HttpStatus.OK, socialMediaResource))
                .orElseThrow(() -> { throw new SocialMediaNotFoundException(); });
    }
    
    
    
}
