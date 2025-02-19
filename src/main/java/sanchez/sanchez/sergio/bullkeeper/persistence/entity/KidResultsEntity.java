package sanchez.sanchez.sergio.bullkeeper.persistence.entity;

import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import sanchez.sanchez.sergio.bullkeeper.persistence.utils.CascadeSave;

/**
 * Kid Results Entity
 * @author sergiosanchezsanchez
 *
 */
@Document
public class KidResultsEntity {
	
	/**
	 * Sentiment
	 */
	@Field("sentiment")
	@CascadeSave
	private SentimentResultsEntity sentiment = new SentimentResultsEntity();
	
	/**
	 * Violence
	 */
	@Field("violence")
	@CascadeSave
	private ViolenceResultsEntity violence = new ViolenceResultsEntity();
	
	/**
	 * Drugs
	 */
	@Field("drugs")
	@CascadeSave
	private DrugsResultsEntity drugs = new DrugsResultsEntity();
	
	/**
	 * Adult
	 */
	@Field("adult")
	@CascadeSave
	private AdultResultsEntity adult = new AdultResultsEntity();
	
	/**
	 * Bullying
	 */
	@Field("bullying")
	@CascadeSave
	private BullyingResultsEntity bullying = new BullyingResultsEntity();
	
	
	public KidResultsEntity(){}
	
	/**
	 * 
	 * @param sentiment
	 * @param violence
	 * @param drugs
	 * @param adult
	 * @param bullying
	 */
	@PersistenceConstructor
	public KidResultsEntity(SentimentResultsEntity sentiment, ViolenceResultsEntity violence, DrugsResultsEntity drugs,
			AdultResultsEntity adult, BullyingResultsEntity bullying) {
		super();
		this.sentiment = sentiment;
		this.violence = violence;
		this.drugs = drugs;
		this.adult = adult;
		this.bullying = bullying;
	}
	

	public SentimentResultsEntity getSentiment() {
		return sentiment;
	}

	public void setSentiment(SentimentResultsEntity sentiment) {
		this.sentiment = sentiment;
	}

	public ViolenceResultsEntity getViolence() {
		return violence;
	}

	public void setViolence(ViolenceResultsEntity violence) {
		this.violence = violence;
	}

	public DrugsResultsEntity getDrugs() {
		return drugs;
	}

	public void setDrugs(DrugsResultsEntity drugs) {
		this.drugs = drugs;
	}

	

	public AdultResultsEntity getAdult() {
		return adult;
	}

	public void setAdult(AdultResultsEntity adult) {
		this.adult = adult;
	}

	public BullyingResultsEntity getBullying() {
		return bullying;
	}

	public void setBullying(BullyingResultsEntity bullying) {
		this.bullying = bullying;
	}
	
}
