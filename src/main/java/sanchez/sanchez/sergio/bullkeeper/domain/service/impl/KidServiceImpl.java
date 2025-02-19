package sanchez.sanchez.sergio.bullkeeper.domain.service.impl;


import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.google.common.collect.Iterables;
import io.jsonwebtoken.lang.Assert;
import sanchez.sanchez.sergio.bullkeeper.domain.service.IKidService;
import sanchez.sanchez.sergio.bullkeeper.domain.service.ITerminalService;
import sanchez.sanchez.sergio.bullkeeper.mapper.KidEntityMapper;
import sanchez.sanchez.sergio.bullkeeper.mapper.LocationEntityMapper;
import sanchez.sanchez.sergio.bullkeeper.mapper.SupervisedChildrenEntityMapper;
import sanchez.sanchez.sergio.bullkeeper.persistence.entity.KidEntity;
import sanchez.sanchez.sergio.bullkeeper.persistence.entity.LocationEntity;
import sanchez.sanchez.sergio.bullkeeper.persistence.entity.SupervisedChildrenEntity;
import sanchez.sanchez.sergio.bullkeeper.persistence.repository.AlertRepository;
import sanchez.sanchez.sergio.bullkeeper.persistence.repository.CommentRepository;
import sanchez.sanchez.sergio.bullkeeper.persistence.repository.KidRepository;
import sanchez.sanchez.sergio.bullkeeper.persistence.repository.SocialMediaRepository;
import sanchez.sanchez.sergio.bullkeeper.persistence.repository.SupervisedChildrenRepository;
import sanchez.sanchez.sergio.bullkeeper.persistence.repository.TaskRepository;
import sanchez.sanchez.sergio.bullkeeper.web.dto.request.SaveGuardianDTO;
import sanchez.sanchez.sergio.bullkeeper.web.dto.request.SaveLocationDTO;
import sanchez.sanchez.sergio.bullkeeper.web.dto.response.ImageDTO;
import sanchez.sanchez.sergio.bullkeeper.web.dto.response.KidDTO;
import sanchez.sanchez.sergio.bullkeeper.web.dto.response.KidGuardianDTO;
import sanchez.sanchez.sergio.bullkeeper.web.dto.response.LocationDTO;
import sanchez.sanchez.sergio.bullkeeper.web.uploads.models.RequestUploadFile;
import sanchez.sanchez.sergio.bullkeeper.web.uploads.service.IUploadFilesService;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.annotation.PostConstruct;

/**
 * 
 * @author sergiosanchezsanchez
 *
 */
@Service
public class KidServiceImpl implements IKidService {

    private static Logger logger = LoggerFactory.getLogger(KidServiceImpl.class);

    /**
     * Kid Repository
     */
    private final KidRepository kidRepository;
    
    /**
     * Kid Entity Mapper
     */
    private final KidEntityMapper kidEntityMapper;
    
    /**
     * Alert Repository
     */
    private final AlertRepository alertRepository;
    
    /**
     * Comment Repository
     */
    private final CommentRepository commentRepository;
    
    /**
     * Social Media Repository
     */
    private final SocialMediaRepository socialMediaRepository;
    
    /**
     * Task Repository
     */
    private final TaskRepository taskRepository;
    
    /**
     * Upload Files Service
     */
    private final IUploadFilesService uploadFilesService;
    
    /**
     * Supervised Children Repository
     */
    private final SupervisedChildrenRepository supervisedChildrenRepository;
    
    /**
     * Supervised Children Entity Mapper
     */
    private final SupervisedChildrenEntityMapper supervisedChildrenEntityMapper;
    
    /**
     * Location Entity Mapper
     */
    private final LocationEntityMapper locationEntityMapper;
    
    /**
     * Terminal Service
     */
    private final ITerminalService terminalService;
    
    

    /**
     * 
     * @param kidRepository
     * @param kidEntityMapper
     * @param alertRepository
     * @param commentRepository
     * @param socialMediaRepository
     * @param taskRepository
     * @param uploadFilesService
     * @param supervisedChildrenRepository
     * @param supervisedChildrenEntityMapper
     * @param locationEntityMapper
     * @param funTimeScheduledEntityMapper
     * @param terminalService
     */
    public KidServiceImpl(KidRepository kidRepository, 
            KidEntityMapper kidEntityMapper, AlertRepository alertRepository,
            CommentRepository commentRepository, SocialMediaRepository socialMediaRepository, 
            TaskRepository taskRepository, IUploadFilesService uploadFilesService,
            final SupervisedChildrenRepository supervisedChildrenRepository,
            final SupervisedChildrenEntityMapper supervisedChildrenEntityMapper,
            final LocationEntityMapper locationEntityMapper,
            final ITerminalService terminalService) {
        super();
        this.kidRepository = kidRepository;
        this.kidEntityMapper = kidEntityMapper;
        this.alertRepository = alertRepository;
        this.commentRepository = commentRepository;
        this.socialMediaRepository = socialMediaRepository;
        this.taskRepository = taskRepository;
        this.uploadFilesService = uploadFilesService;
        this.supervisedChildrenRepository = supervisedChildrenRepository;
        this.supervisedChildrenEntityMapper = supervisedChildrenEntityMapper;
        this.locationEntityMapper = locationEntityMapper;
        this.terminalService = terminalService;
    }

    /**
     * 
     */
    @Override
    public Page<KidDTO> findPaginated(Integer page, Integer size) {
        Pageable pageable = new PageRequest(page, size);
        Page<KidEntity> childrenPage = kidRepository.findAll(pageable);
        return childrenPage.map(new Converter<KidEntity, KidDTO>() {
            @Override
            public KidDTO convert(KidEntity sonEntity) {
                return kidEntityMapper.kidEntityToKidDTO(sonEntity);
            }
        });
        
    }

    /**
     * 
     */
    @Override
    public Page<KidDTO> findPaginated(Pageable pageable) {
        Page<KidEntity> childrenPage = kidRepository.findAll(pageable);
        return childrenPage.map(new Converter<KidEntity, KidDTO>() {
            @Override
            public KidDTO convert(KidEntity sonEntity) {
                return kidEntityMapper.kidEntityToKidDTO(sonEntity);
            }
        });
    }

    /**
     * 
     */
    @Override
    public KidDTO getKidById(String id) {
        KidEntity sonEntity = kidRepository.findOne(new ObjectId(id));
        return kidEntityMapper.kidEntityToKidDTO(sonEntity);
    }

    /**
     * 
     */
    @Override
    public Long getTotalKids() {
        return kidRepository.count();
    }
    
    /**
     * 
     */
    @Override
    public String getProfileImage(ObjectId id) {
        return kidRepository.getProfileImageIdByUserId(id);
    }
    
    /**
     * 
     */
    @Override
    public void deleteById(String id) {
        Assert.notNull(id, "Id can not be null");
        Assert.isTrue(ObjectId.isValid(id), "Id should be a valid Object Id");
        deleteById(new ObjectId(id));
    }
    
    /**
     * 
     */
    @Override
    public void deleteById(ObjectId id) {
    	Assert.notNull(id, "Id can not be null");
    	terminalService.deleteByKid(id);
    	final String imageId = getProfileImage(id);
    	if(imageId != null && !imageId.isEmpty())
    		uploadFilesService.delete(imageId);
    	alertRepository.deleteByKidId(id);
        commentRepository.deleteByKid(id);
        socialMediaRepository.deleteByKidId(id);
        taskRepository.deleteByKidId(id);
        kidRepository.delete(id);
        supervisedChildrenRepository.deleteByKidId(id);
    }
    
    /**
     * Save Guardians
     */
    @Override
	public Iterable<KidGuardianDTO> save(List<SaveGuardianDTO> guardians, final ObjectId kid) {
    	Assert.notNull(guardians, "Guardians can not be null");
    	Assert.notNull(kid, "Kid can not be null");
    	
    	Iterable<KidGuardianDTO> result = new ArrayList<KidGuardianDTO>();
    	
    	// Guardians To Save
    	final Iterable<SupervisedChildrenEntity> guardiansToSave = 
    			supervisedChildrenEntityMapper
    				.saveGuardianDTOToSupervisedChildrenEntity(guardians);
        
    	// Get Current Guardians
        final List<SupervisedChildrenEntity> guardiansSaved = supervisedChildrenRepository
        		.findByKidId(kid);
        
        
        if(guardiansSaved.isEmpty()) {
        	// Save Guardians
        	final List<SupervisedChildrenEntity> supervisedChildrenSaved = 
        			supervisedChildrenRepository.save(guardiansToSave);
        	
        	result = supervisedChildrenEntityMapper
        				.supervisedChildrenEntityToKidGuardiansDTO(supervisedChildrenSaved);
        	
        } else if (Iterables.size(guardiansToSave) == 0) {
        	// Delete all by kid id
        	supervisedChildrenRepository.deleteByKidId(kid);
        } else {
        	
        	final List<SupervisedChildrenEntity> supervisedChildToSave = 
    				new ArrayList<>();
        	
        	if(guardiansSaved.size() > Iterables.size(guardiansToSave)) {
   
        		logger.debug("uardiansSaved.size() > Iterables.size(guardiansToSave)");
        		
        		for(final SupervisedChildrenEntity supervisedChildren: guardiansSaved) {
        			boolean isFound = false;
        			final Iterator<SupervisedChildrenEntity> iterator = guardiansToSave.iterator();
        			while(iterator.hasNext()) {
        				final SupervisedChildrenEntity currentSupervisedChild = iterator.next();
        				if(currentSupervisedChild.getKid().getId()
    							.equals(supervisedChildren.getKid().getId()) && 
    							currentSupervisedChild.getGuardian().getId()
    							.equals(supervisedChildren.getGuardian().getId())) {
        					// Set Role
        					supervisedChildren
        						.setRole(currentSupervisedChild.getRole());
        					
        					supervisedChildToSave.add(supervisedChildren);
        					
        					isFound = true;
    					}
        			}
        			
        			if(!isFound)
        				supervisedChildrenRepository.delete(supervisedChildren);
        	
        			
        		}
        		
        	} else {
        		
        		final Iterator<SupervisedChildrenEntity> iterator = guardiansToSave.iterator();
    			while(iterator.hasNext()) {
    				// Current Supervised Child
    				final SupervisedChildrenEntity currentSupervisedChild = iterator.next();
    				
    				boolean isFound = false;
					for(final SupervisedChildrenEntity supervisedChildren: guardiansSaved) {
    					
    					if(currentSupervisedChild.getKid().getId()
    							.equals(supervisedChildren.getKid().getId()) && 
    							currentSupervisedChild.getGuardian().getId()
    							.equals(supervisedChildren.getGuardian().getId())) {
    						isFound = true;
    						// Set Role
    						supervisedChildren.setRole(currentSupervisedChild.getRole());
    						supervisedChildToSave.add(supervisedChildren);
    						break;
    					}
    					
    				}
					
					if(!isFound)
						supervisedChildToSave.add(currentSupervisedChild);
    		
    			}
    			
    			
    			
    
    			// Guardians to Delete
    			final List<SupervisedChildrenEntity> guardiansToDelete = new ArrayList<>();
    			
    			for(final SupervisedChildrenEntity guardianSaved: guardiansSaved) {
    				boolean isFound = false;
    				for(final SupervisedChildrenEntity guardianToSave: supervisedChildToSave) {
    					if(guardianSaved.getKid().getId()
    							.equals(guardianToSave.getKid().getId()) && 
    							guardianSaved.getGuardian().getId()
    							.equals(guardianToSave.getGuardian().getId())) {
    						isFound = true;
    					}
    				}
    				
    				if(!isFound)
    					guardiansToDelete.add(guardianSaved);
    				
    			}
    			
    			logger.debug("Total Guardians To Save -> " + supervisedChildToSave.size());
    			logger.debug("Total Guardians Saved -> " + guardiansSaved.size());
    			logger.debug("Total Guardians To Delete -> " + guardiansToDelete.size());
        		
    			supervisedChildrenRepository.delete(guardiansToDelete);
    			
        	}

        	// Save Supervised Child
    		result = supervisedChildrenEntityMapper
    				.supervisedChildrenEntityToKidGuardiansDTO(
    						supervisedChildrenRepository.save(supervisedChildToSave));
        	
        }
        
        
        return result;
	}
    
    /**
     * Get Guardians
     * @param guardian
     * @param kid
     */
    @Override
	public Iterable<KidGuardianDTO> findSupervisedChildrenConfirmed(final ObjectId guardian, final ObjectId kid) {
    	Assert.notNull(guardian, "Guardian can not be null");
    	Assert.notNull(kid, "Kid can not be null");
    	
    	// Find By Guardian id and kid id and is confirmed true
    	final List<SupervisedChildrenEntity> supervisedChildrenEntities = supervisedChildrenRepository
    		.findByGuardianIdAndKidIdAndIsConfirmedTrue(guardian, kid);
    	// map results
    	return supervisedChildrenEntityMapper
    			.supervisedChildrenEntityToKidGuardiansDTO(supervisedChildrenEntities);
	}
    
    /**
     * Find Supervised Children No Confirmed
     */
    @Override
	public Iterable<KidGuardianDTO> findSupervisedChildrenNoConfirmed(final ObjectId guardian, 
			final ObjectId kid) {
    	Assert.notNull(guardian, "Guardian can not be null");
    	Assert.notNull(kid, "Kid can not be null");
		
    	// Find By Guardian id and kid id and is confirmed false
    	final List<SupervisedChildrenEntity> supervisedChildrenEntities = supervisedChildrenRepository
    		.findByGuardianIdAndKidIdAndIsConfirmedFalse(guardian, kid);
    	
    	// map results
    	return supervisedChildrenEntityMapper
    			.supervisedChildrenEntityToKidGuardiansDTO(supervisedChildrenEntities);
	}

    /**
     * Delete Supervised Children No Confirmed
     */
	@Override
	public void deleteSupervisedChildrenNoConfirmed(final ObjectId guardian, final ObjectId kid) {
		Assert.notNull(guardian, "Guardian can not be null");
    	Assert.notNull(kid, "Kid can not be null");
    	
    	supervisedChildrenRepository
    		.deleteByGuardianIdAndKidIdAndIsConfirmedFalse(guardian, kid);
		
	}

	
	/**
	 * Find Supervised Children Confirmed
	 */
	@Override
	public Iterable<KidGuardianDTO> findSupervisedChildrenConfirmed(final ObjectId guardian) {
		Assert.notNull(guardian, "Guardian can not be null");
		
		// Find Supervised Children Entities
		final List<SupervisedChildrenEntity> supervisedChildrenEntities = 
				supervisedChildrenRepository.findByGuardianIdAndIsConfirmedTrue(guardian);
		
		// Map Results
		return supervisedChildrenEntityMapper
				.supervisedChildrenEntityToKidGuardiansDTO(supervisedChildrenEntities);
	}

	/**
	 * Find Supervised Children no Confirmed
	 */
	@Override
	public Iterable<KidGuardianDTO> findSupervisedChildrenNoConfirmed(final ObjectId guardian) {
		Assert.notNull(guardian, "Guardian can not be null");
		
		// Find Supervised Children Entities
		final List<SupervisedChildrenEntity> supervisedChildrenEntities = 
						supervisedChildrenRepository.findByGuardianIdAndIsConfirmedFalse(guardian);
				
		// Map Results
		return supervisedChildrenEntityMapper
						.supervisedChildrenEntityToKidGuardiansDTO(supervisedChildrenEntities);
	}

	/**
	 * Delete Supervised Children No Confirmed
	 */
	@Override
	public void deleteSupervisedChildrenNoConfirmed(final ObjectId guardian) {
		Assert.notNull(guardian, "Guardian can not be null");
		
		supervisedChildrenRepository.deleteByGuardianIdAndIsConfirmedFalse(guardian);
	}

	/**
	 * Accept Supervised Children No Confirmed
	 */
	@Override
	public void acceptSupervisedChildrenNoConfirmed(final ObjectId guardian) {
		Assert.notNull(guardian, "Guardian can not be null");
	
		supervisedChildrenRepository.acceptSupervisedChildrenNoConfirm(guardian);
		
	}
	
	/**
	 * Find Supervised Children No Confirmed By Id
	 */
	@Override
	public KidGuardianDTO findSupervisedChildrenNoConfirmedById(final ObjectId id) {
		Assert.notNull(id, "Id can not be null");
		
		final SupervisedChildrenEntity supervisedChildrenEntity = 
				supervisedChildrenRepository.findOne(id);
		// Map Result
		return supervisedChildrenEntityMapper
				.supervisedChildrenEntityToKidGuardiansDTO(supervisedChildrenEntity);
	}
	
	/**
	 * Delete Supervised Children No Confirmed By Id
	 */
	@Override
	public void deleteSupervisedChildrenNoConfirmedById(final ObjectId id) {
		Assert.notNull(id, "Id can not be null");
		
		supervisedChildrenRepository.deleteByIdAndIsConfirmedFalse(id);
	}

	/**
	 * Accept Supervised Children No Confirmed By Id
	 */
	@Override
	public void acceptSupervisedChildrenNoConfirmedById(final ObjectId id) {
		Assert.notNull(id, "Id can not be null");
		
		supervisedChildrenRepository.acceptSupervisedChildrenNoConfirm(id);
		
	}
	
	/**
	 * Delete Supervised Children Confirmed By Id
	 * @param id
	 */
	@Override
	public void deleteSupervisedChildrenConfirmedById(ObjectId id) {
		Assert.notNull(id, "Id can not be null");
		
		supervisedChildrenRepository.deleteByIdAndIsConfirmedTrue(id);
	}

	/**
	 * Find Supervised Children Confirmed By Id
	 */
	@Override
	public KidGuardianDTO findSupervisedChildrenConfirmedById(ObjectId id) {
		Assert.notNull(id, "Id can not be null");
		
		final SupervisedChildrenEntity supervisedChildrenEntity = 
				supervisedChildrenRepository.findByIdAndIsConfirmedTrue(id);
		
		return supervisedChildrenEntityMapper
				.supervisedChildrenEntityToKidGuardiansDTO(supervisedChildrenEntity);
		
	}
	
	
	/**
	 * Get Guardians
	 */
	@Override
	public Iterable<KidGuardianDTO> getGuardians(final ObjectId id) {
		Assert.notNull(id, "id can not be null");
		
		// Find Supervised Children
		final List<SupervisedChildrenEntity> supervisedChildrenEntities = 
				supervisedChildrenRepository.findByKidId(id);
		
		return supervisedChildrenEntityMapper
					.supervisedChildrenEntityToKidGuardiansDTO(supervisedChildrenEntities);
	
	}
    
	/**
	 * Save Current Location
	 * @param kid
	 * @param location
	 */
	@Override
	public LocationDTO saveCurrentLocation(final String kid, final SaveLocationDTO location) {
		Assert.notNull(kid, "Kid can not be null");
		Assert.notNull(location, "Location can not be null");
		// Map Location
		final LocationEntity locationEntity =  locationEntityMapper.saveLocationToLocationEntity(location);
		// Update Current Location
		kidRepository.updateCurrentLocation(new ObjectId(kid), locationEntity);
		// Map Result
		return locationEntityMapper.locationEntityToLocationDTO(locationEntity);
	}

	/**
	 * Get Current Location
	 * @param kid
	 */
	@Override
	public LocationDTO getCurrentLocation(final String kid) {
		Assert.notNull(kid, "Kid can not be null");
	
		// Get Current Location
		final LocationEntity location = kidRepository.getCurrentLocation(new ObjectId(kid));
		// Map Result
		return locationEntityMapper.locationEntityToLocationDTO(location);
	}
	
	/**
	 * Get Confirmed Guardians
	 * @param id
	 */
	@Override
	public Iterable<KidGuardianDTO> getConfirmedGuardians(final ObjectId id) {
		Assert.notNull(id, "id can not be null");
		
		// Find Supervised Children
		final List<SupervisedChildrenEntity> supervisedChildrenEntities = 
						supervisedChildrenRepository.findByKidIdAndIsConfirmedTrue(id);
				
		return supervisedChildrenEntityMapper
							.supervisedChildrenEntityToKidGuardiansDTO(supervisedChildrenEntities);
	}
	
	/**
	 * @param guardian
	 * @param kid
	 */
	@Override
	public KidGuardianDTO findSupervisedChildConfirmedById(final ObjectId guardian, final ObjectId kid) {
		Assert.notNull(guardian, "Guardian can not be null");
		Assert.notNull(kid, "Kid can not be null");
		
		final SupervisedChildrenEntity supervisedChildrenEntity = 
				supervisedChildrenRepository.findByGuardianIdAndKidId(guardian, kid);
		
		return supervisedChildrenEntityMapper
				.supervisedChildrenEntityToKidGuardiansDTO(supervisedChildrenEntity);
	}
	
	/**
	 * Upload Kid Profile Image
	 * @param kid
	 * @param requestUploadFile
	 */
	@Override
	public ImageDTO uploadKidProfileImage(final ObjectId kid, final RequestUploadFile requestUploadFile) {
		Assert.notNull(kid, "User Id can not be null");
	    Assert.notNull(requestUploadFile, "Request Upload File can not be null");
	    
	    final KidEntity kidEntity = kidRepository.findOne(kid);
	    final String profileImageId = uploadFilesService.save(requestUploadFile);
	    if(kidEntity.getProfileImage() != null)
	    	uploadFilesService.delete(kidEntity.getProfileImage());
	    kidEntity.setProfileImage(profileImageId);
	    kidRepository.save(kidEntity);
	    return uploadFilesService.getImage(profileImageId);
	    
	}
	
	
    @PostConstruct
    protected void init() {
        Assert.notNull(kidRepository, "Son Repository can not be null");
        Assert.notNull(kidEntityMapper, "Son Entity Mapper can not be null");
        Assert.notNull(alertRepository, "Alert Repository can not be null");
        Assert.notNull(commentRepository, "Comment Repository can not be null");
        Assert.notNull(socialMediaRepository, "Social Media Repository can not be null");
        Assert.notNull(taskRepository, "Task Repository can not be null");
        Assert.notNull(uploadFilesService, "Upload File Service can not be null");
    }
	
}
