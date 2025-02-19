package sanchez.sanchez.sergio.bullkeeper.domain.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import sanchez.sanchez.sergio.bullkeeper.domain.service.IIterationService;
import sanchez.sanchez.sergio.bullkeeper.mapper.IterationEntityMapper;
import sanchez.sanchez.sergio.bullkeeper.persistence.entity.IterationEntity;
import sanchez.sanchez.sergio.bullkeeper.persistence.repository.IterationRepository;
import sanchez.sanchez.sergio.bullkeeper.persistence.repository.KidRepository;
import sanchez.sanchez.sergio.bullkeeper.web.dto.response.CommentsByKidDTO;
import sanchez.sanchez.sergio.bullkeeper.web.dto.response.IterationDTO;
import sanchez.sanchez.sergio.bullkeeper.web.dto.response.IterationWithTasksDTO;
import sanchez.sanchez.sergio.bullkeeper.web.websocket.constants.WebSocketConstants;

/**
 * @author sergio
 */
@Service("iterationService")
public class IterationServiceImpl implements IIterationService {
    
    private Logger logger = LoggerFactory.getLogger(IterationServiceImpl.class);
    
    private final IterationRepository iterationRepository;
    private final IterationEntityMapper iterationEntityMapper;
    private final SimpMessagingTemplate simpMessagingTemplate;
    
    /**
     * 
     * @param iterationRepository
     * @param iterationEntityMapper
     * @param simpMessagingTemplate
     * @param sonRepository
     */
    public IterationServiceImpl(final IterationRepository iterationRepository, 
            final IterationEntityMapper iterationEntityMapper,final  SimpMessagingTemplate simpMessagingTemplate,
             KidRepository sonRepository) {
        this.iterationRepository = iterationRepository;
        this.iterationEntityMapper = iterationEntityMapper;
        this.simpMessagingTemplate = simpMessagingTemplate;
    }
    
    
    /**
     * Save Iteration
     */
    @Override
    public IterationWithTasksDTO save(IterationEntity iterationEntity) {
    
    	logger.debug("Save Iteration ...");
    	//Save Iteration
        IterationEntity iterationSave = iterationRepository.save(iterationEntity);
       
        //notify information about the last iteration
        simpMessagingTemplate.convertAndSend(WebSocketConstants.NEW_ITERATION_TOPIC, 
                iterationEntityMapper.iterationEntityToIterationDTO(iterationSave));
        
  
        simpMessagingTemplate.convertAndSend(WebSocketConstants.LAST_ITERATION_COMMENTS_BY_SON_TOPIC,
        		getCommentsBySonForIteration(iterationSave));
        
        logger.debug("TOTAL TASK ->" + iterationSave.getTotalTasks());
        logger.debug("TOTAL TASK FAILED -> " + iterationSave.getTotalFailedTasks());
        logger.debug("AVG DURATION -> " + iterationRepository.getAvgDuration().getAvgDuration());
        logger.debug("ITERATION FINISH AT -> " + iterationSave.getFinishDate());
        //logger.debug("SCHEDULED FOR -> " + scheduledFor.toString());
        
        
        return iterationEntityMapper.iterationEntityToIterationWithTasksDTO(iterationSave);
        
 
    }
    
   /**
    * Get Total Iterations
    */
    @Override
    public Long getTotalIterations() {
        return iterationRepository.count();
    }

    /**
     * Find Paginated
     */
    @Override
    public Page<IterationDTO> findPaginated(Integer page, Integer size) {
        Pageable pageable = new PageRequest(page, size);
        Page<IterationEntity> iterationsPage = iterationRepository.findAll(pageable);
        return iterationsPage.map(new Converter<IterationEntity, IterationDTO>(){
            @Override
            public IterationDTO convert(IterationEntity ite) {
               return iterationEntityMapper.iterationEntityToIterationDTO(ite);
            }
        });
    }
    
    /**
     * Get Last Probing
     */
    @Override
	public Date getLastProbing() {
    	PageRequest request = new PageRequest(0, 1, new Sort(Sort.Direction.DESC, "finishDate"));
    	List<IterationEntity> iterations = iterationRepository.findAll(request).getContent();
    	return iterations.size() > 0 ? iterations.get(0).getFinishDate() : null;
	}
    
    /**
     *
     */
    @Override
	public Page<IterationDTO> findPaginated(Pageable pageable) {
    	Page<IterationEntity> iterationsPage = iterationRepository.findAll(pageable);
        return iterationsPage.map(new Converter<IterationEntity, IterationDTO>(){
            @Override
            public IterationDTO convert(IterationEntity ite) {
               return iterationEntityMapper.iterationEntityToIterationDTO(ite);
            }
        });
	}
        
    @Override
    public List<IterationDTO> allIterations() {
        return iterationEntityMapper.iterationEntitiesToIterationDTOs(iterationRepository.findAll());
    }
    
    @Override
	public List<CommentsByKidDTO> getCommentsBySonForLastIteration() {
    	PageRequest request = new PageRequest(0, 1, new Sort(Sort.Direction.DESC, "finishDate"));
    	List<IterationEntity> iterations = iterationRepository.findAll(request).getContent();
    	IterationEntity lastIteration = iterations.size() > 0 ? iterations.get(0) : null;
    	return lastIteration != null ? getCommentsBySonForIteration(lastIteration) : new ArrayList<>();
	}
   
    @Override
	public Double getAvgDuration() {
		return iterationRepository.getAvgDuration().getAvgDuration();
	}
    
    /**
     * 
     * Private Methods
     * ===================
     * 
     */
    
    /**
     * 
     * @param iterationEntity
     * @return
     */
    private List<CommentsByKidDTO> getCommentsBySonForIteration(IterationEntity iterationEntity) {
    	Map<String, Long> commentsBySon = new HashMap<String, Long>();
    	iterationEntity.getTasks().stream().flatMap(task -> task.getComments().stream()).forEach(comment -> 
			commentsBySon.compute(comment.getKid().getFullName(), (k, v) -> (v == null) ? 1 : ++v));
        
        return commentsBySon
        	.entrySet()
        		.stream()
        			.map(entry -> new CommentsByKidDTO(entry.getKey(), entry.getValue()))
        				.collect(Collectors.toList());
    }
    
    
  
    
    @PostConstruct
    protected void init() {
        Assert.notNull(iterationRepository, "IterationRepository cannot be null");
        Assert.notNull(iterationEntityMapper, "IIterationEntityMapper cannot be null");
        Assert.notNull(simpMessagingTemplate, "SimpMessagingTemplate cannot be null");
    }
	
}
