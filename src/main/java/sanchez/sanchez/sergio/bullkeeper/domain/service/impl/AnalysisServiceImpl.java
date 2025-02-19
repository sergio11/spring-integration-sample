package sanchez.sanchez.sergio.bullkeeper.domain.service.impl;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import org.apache.commons.collections.ListUtils;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import com.google.common.collect.ImmutableMap;

import sanchez.sanchez.sergio.bullkeeper.domain.service.IAnalysisService;
import sanchez.sanchez.sergio.bullkeeper.persistence.entity.AnalysisStatusEnum;
import sanchez.sanchez.sergio.bullkeeper.persistence.entity.AnalysisTypeEnum;
import sanchez.sanchez.sergio.bullkeeper.persistence.repository.CommentRepository;
import sanchez.sanchez.sergio.bullkeeper.web.dto.response.IterationWithTasksDTO;

@Service("analysisService")
public class AnalysisServiceImpl implements IAnalysisService {
	
	private Logger logger = LoggerFactory.getLogger(AnalysisServiceImpl.class);
	
	private final RestTemplate analysisRestTemplate;
	private final CommentRepository commentRepository;
	
	private final static String CHILD_ID_ARG = "childId";
	private final static String COMMENT_IDS_ARG = "commentIds";
	private final static Map<AnalysisTypeEnum, String> analysisServices;
	
	static {
		
		analysisServices = ImmutableMap.<AnalysisTypeEnum, String>builder()
			    .put(AnalysisTypeEnum.SENTIMENT, "sentiment")
			    .put(AnalysisTypeEnum.ADULT, "adult")
			    .put(AnalysisTypeEnum.VIOLENCE, "violence")
			    .put(AnalysisTypeEnum.DRUGS, "drugs")
			    .put(AnalysisTypeEnum.BULLYING, "bullying")
			    .build();
	}
	
	@Value("${analysis.service.base.url}")
	private String analysisServiceBaseUrl;
	
	@Autowired
	public AnalysisServiceImpl(@Qualifier("analysisRestTemplate") RestTemplate analysisRestTemplate, CommentRepository commentRepository) {
		super();
		this.analysisRestTemplate = analysisRestTemplate;
		this.commentRepository = commentRepository;
	}
	
	/**
	 * 
	 * @param type
	 * @param kid
	 * @param commentIds
	 * @return
	 */
	private ResponseEntity<Void> startAnalysisFor(final AnalysisTypeEnum type, final ObjectId kid, final Collection<ObjectId> commentIds){
		
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		
		// create form parameters as a MultiValueMap
		final MultiValueMap<String, String> formVars = new LinkedMultiValueMap<>();
		formVars.add(CHILD_ID_ARG, kid.toString());
		formVars.add(COMMENT_IDS_ARG, String.join("_", commentIds.parallelStream().map((commentObjectId) -> commentObjectId.toString()).collect(Collectors.toList())));


		HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(formVars, headers);

		ResponseEntity<Void> response = analysisRestTemplate.postForEntity(analysisServiceBaseUrl + "/" + analysisServices.get(type) + "/", 
				request,  Void.class);
		
		return response;
		
		
	} 
	
	/**
	 * 
	 */
	@Override
	public void startAnalysisFor(final AnalysisTypeEnum type, final Map<ObjectId, List<ObjectId>> commentsByKid) {
		
		for (Map.Entry<ObjectId, List<ObjectId>> commentsBySonEntry : commentsByKid.entrySet())
	     {
			 
			 final ObjectId kidId = commentsBySonEntry.getKey();
			 final Collection<ObjectId> commentIds = commentsBySonEntry.getValue();
			 
			 logger.debug(String.format("Start %s analysis for %s with %d comments", type.toString(), kidId.toString(), commentIds.size()));
			 
			 try {
				 
				 commentRepository.updateAnalysisStatusFor(type, AnalysisStatusEnum.IN_PROGRESS, commentIds);
				 ResponseEntity<Void> response = startAnalysisFor(type, kidId, commentIds);
				 
				 if(response.getStatusCode().equals(HttpStatus.INTERNAL_SERVER_ERROR) || response.getStatusCode().equals(HttpStatus.NOT_FOUND)) {
					 logger.error(String.format("Failed call to the %s analysis service with code %d ", type.toString(), response.getStatusCode().name()));
					 commentRepository.updateAnalysisStatusFor(type, AnalysisStatusEnum.PENDING, commentIds);
				 }
			 } catch (Exception e) {
				 logger.error(String.format("Failed call to the %s analysis service with err %s ", type.toString(), e.getMessage()));
				 commentRepository.updateAnalysisStatusFor(type, AnalysisStatusEnum.PENDING, commentIds);
			 }
			 
			 
	     }
		
	}
	
	/**
	 * 
	 */
	@Override
	public void startSentimentAnalysisFor(Map<ObjectId, List<ObjectId>> commentsByKid) {
		startAnalysisFor(AnalysisTypeEnum.SENTIMENT, commentsByKid);
	}

	/**
	 * 
	 */
	@Override
	public void startAnalysisFor(IterationWithTasksDTO iteration) {
		
		logger.debug("Start Analysis for Iteration");
		
		@SuppressWarnings("unchecked")
		Map<ObjectId, List<ObjectId>> commentsByKidForIteration = iteration.getTasks().parallelStream()
        		.filter(task -> task.getSuccess() && task.getComments().size() > 0)
        		.collect(Collectors.toMap(
        				task -> new ObjectId(task.getKid().getIdentity()), 
        				task -> task.getComments().parallelStream()
        				.map(comment -> new ObjectId(comment.getIdentity()))
        				.collect(Collectors.toList()),
        				(comments1, comments2) -> ListUtils.union(comments1, comments2)));
		
		
		if(!commentsByKidForIteration.isEmpty()) {
			
			startSentimentAnalysisFor(commentsByKidForIteration);
			
		} else {
			logger.debug("No Comments To Analisis ");
		}
	}
	
	/**
	 * Start Violence Analysis
	 */
	@Override
	public void startViolenceAnalysisFor(Map<ObjectId, List<ObjectId>> commentsByKid) {
		startAnalysisFor(AnalysisTypeEnum.VIOLENCE, commentsByKid);
	}

	/**
	 * Start Drug Analysis
	 */
	@Override
	public void startDrugAnalysisFor(Map<ObjectId, List<ObjectId>> commentsByKid) {
		startAnalysisFor(AnalysisTypeEnum.DRUGS, commentsByKid);
	}

	/**
	 * Start Adult
	 */
	@Override
	public void startAdultContentAnalysisFor(Map<ObjectId, List<ObjectId>> commentsByKid) {
		startAnalysisFor(AnalysisTypeEnum.ADULT, commentsByKid);
		
	}

	/**
	 * Start Bullying
	 */
	@Override
	public void startBullyingAnalysisFor(Map<ObjectId, List<ObjectId>> commentsByKid) {
		startAnalysisFor(AnalysisTypeEnum.BULLYING, commentsByKid);
		
	}
	
	@PostConstruct
    protected void init() {
        Assert.notNull(analysisRestTemplate, "Analysis RestTemplate can not be null");
        Assert.notNull(commentRepository, "Comment Repository cannot be null");
    }

}
