package sanchez.sanchez.sergio.bullkeeper.web.dto.response;

import java.io.Serializable;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Summary My Kid Result DTO
 * @author ssanchez
 *
 */
public final class SummaryMyKidResultDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Identity
	 */
	@JsonProperty("identity")
	private String identity;
	
	/**
	 * Fist Name
	 */
	@JsonProperty("first_name")
    private String firstName;
	
	/**
	 * Last Name
	 */
	@JsonProperty("last_name")
    private String lastName;
	
	/**
	 * Birthdate
	 */
	@JsonProperty("birthdate")
    private String birthdate;
	
	/**
	 * Age
	 */
	@JsonProperty("age")
    private Integer age;
	
	/**
	 * School
	 */
	@JsonProperty("school")
    private SchoolDTO school;
	
	/**
	 * Profile Image
	 */
	@JsonProperty("profile_image")
    private String profileImage;
	
    /**
     * Total Devices
     */
    @JsonProperty("total_devices")
    private long totalDevices;

    /**
     * Location
     */
    @JsonProperty("location")
    private LocationDTO location;

    /**
     * Social Medias
     */
    @JsonProperty("social_medias")
    private List<SocialMediaDTO> socialMedias;
    
    /**
     * Total Comments
     */
    @JsonProperty("total_comments")
    private long totalComments;

    /**
     * Total Comments Analyzed
     */
    @JsonProperty("total_comments_analyzed")
    private long totalCommentsAnalyzed;

    /**
     * Total Violent Comments
     */
    @JsonProperty("total_violent_comments")
    private long totalViolentComments;

    /**
     * Total Comments Adult Content
     */
    @JsonProperty("total_comments_adult_content")
    private long totalCommentsAdultContent;

    /**
     * Total Comments Drugs
     */
    @JsonProperty("total_comments_drugs")
    private long totalCommentsDrugs;

    /**
     * Total Comments Bullying
     */
    @JsonProperty("total_comments_bullying")
    private long totalCommentsBullying;

    /**
     * Total Comments Negative Sentiment
     */
    @JsonProperty("total_comments_negative_sentiment")
    private long totalCommentsNegativeSentiment;

    /**
     * Total Comments Positive Sentiment
     */
    @JsonProperty("total_comments_positive_sentiment")
    private long totalCommentsPositiveSentiment;

    /**
     * Total Comments Neutral Sentiment
     */
    @JsonProperty("total_comments_neutral_sentiment")
    private long totalCommentsNeutralSentiment;
    
    /**
     * 
     */
    public SummaryMyKidResultDTO() {}

    /**
     * @param identity
     * @param firstName
     * @param lastName
     * @param birthdate
     * @param age
     * @param school
     * @param profileImage
     * @param totalDevices
     * @param location
     * @param socialMedias
     * @param totalComments
     * @param totalCommentsAnalyzed
     * @param totalViolentComments
     * @param totalCommentsAdultContent
     * @param totalCommentsDrugs
     * @param totalCommentsBullying
     * @param totalCommentsNegativeSentiment
     * @param totalCommentsPositiveSentiment
     * @param totalCommentsNeutralSentiment
     */
	public SummaryMyKidResultDTO(final String identity, String firstName, String lastName, String birthdate, Integer age, SchoolDTO school,
			String profileImage, long totalDevices, LocationDTO location, List<SocialMediaDTO> socialMedias,
			long totalComments, long totalCommentsAnalyzed, long totalViolentComments, long totalCommentsAdultContent,
			long totalCommentsDrugs, long totalCommentsBullying, long totalCommentsNegativeSentiment,
			long totalCommentsPositiveSentiment, long totalCommentsNeutralSentiment) {
		super();
		this.identity = identity;
		this.firstName = firstName;
		this.lastName = lastName;
		this.birthdate = birthdate;
		this.age = age;
		this.school = school;
		this.profileImage = profileImage;
		this.totalDevices = totalDevices;
		this.location = location;
		this.socialMedias = socialMedias;
		this.totalComments = totalComments;
		this.totalCommentsAnalyzed = totalCommentsAnalyzed;
		this.totalViolentComments = totalViolentComments;
		this.totalCommentsAdultContent = totalCommentsAdultContent;
		this.totalCommentsDrugs = totalCommentsDrugs;
		this.totalCommentsBullying = totalCommentsBullying;
		this.totalCommentsNegativeSentiment = totalCommentsNegativeSentiment;
		this.totalCommentsPositiveSentiment = totalCommentsPositiveSentiment;
		this.totalCommentsNeutralSentiment = totalCommentsNeutralSentiment;
	}
	
	

	public String getIdentity() {
		return identity;
	}

	public void setIdentity(String identity) {
		this.identity = identity;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getBirthdate() {
		return birthdate;
	}

	public void setBirthdate(String birthdate) {
		this.birthdate = birthdate;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public SchoolDTO getSchool() {
		return school;
	}

	public void setSchool(SchoolDTO school) {
		this.school = school;
	}

	public String getProfileImage() {
		return profileImage;
	}

	public void setProfileImage(String profileImage) {
		this.profileImage = profileImage;
	}

	public long getTotalDevices() {
		return totalDevices;
	}

	public void setTotalDevices(long totalDevices) {
		this.totalDevices = totalDevices;
	}

	public LocationDTO getLocation() {
		return location;
	}

	public void setLocation(LocationDTO location) {
		this.location = location;
	}

	public List<SocialMediaDTO> getSocialMedias() {
		return socialMedias;
	}

	public void setSocialMedias(List<SocialMediaDTO> socialMedias) {
		this.socialMedias = socialMedias;
	}

	public long getTotalComments() {
		return totalComments;
	}

	public void setTotalComments(long totalComments) {
		this.totalComments = totalComments;
	}

	public long getTotalCommentsAnalyzed() {
		return totalCommentsAnalyzed;
	}

	public void setTotalCommentsAnalyzed(long totalCommentsAnalyzed) {
		this.totalCommentsAnalyzed = totalCommentsAnalyzed;
	}

	public long getTotalViolentComments() {
		return totalViolentComments;
	}

	public void setTotalViolentComments(long totalViolentComments) {
		this.totalViolentComments = totalViolentComments;
	}

	public long getTotalCommentsAdultContent() {
		return totalCommentsAdultContent;
	}

	public void setTotalCommentsAdultContent(long totalCommentsAdultContent) {
		this.totalCommentsAdultContent = totalCommentsAdultContent;
	}

	public long getTotalCommentsDrugs() {
		return totalCommentsDrugs;
	}

	public void setTotalCommentsDrugs(long totalCommentsDrugs) {
		this.totalCommentsDrugs = totalCommentsDrugs;
	}

	public long getTotalCommentsBullying() {
		return totalCommentsBullying;
	}

	public void setTotalCommentsBullying(long totalCommentsBullying) {
		this.totalCommentsBullying = totalCommentsBullying;
	}

	public long getTotalCommentsNegativeSentiment() {
		return totalCommentsNegativeSentiment;
	}

	public void setTotalCommentsNegativeSentiment(long totalCommentsNegativeSentiment) {
		this.totalCommentsNegativeSentiment = totalCommentsNegativeSentiment;
	}

	public long getTotalCommentsPositiveSentiment() {
		return totalCommentsPositiveSentiment;
	}

	public void setTotalCommentsPositiveSentiment(long totalCommentsPositiveSentiment) {
		this.totalCommentsPositiveSentiment = totalCommentsPositiveSentiment;
	}

	public long getTotalCommentsNeutralSentiment() {
		return totalCommentsNeutralSentiment;
	}

	public void setTotalCommentsNeutralSentiment(long totalCommentsNeutralSentiment) {
		this.totalCommentsNeutralSentiment = totalCommentsNeutralSentiment;
	}

	@Override
	public String toString() {
		return "SummaryMyKidResultDTO [identity=" + identity + ", firstName=" + firstName + ", lastName=" + lastName
				+ ", birthdate=" + birthdate + ", age=" + age + ", school=" + school + ", profileImage=" + profileImage
				+ ", totalDevices=" + totalDevices + ", location=" + location + ", socialMedias=" + socialMedias
				+ ", totalComments=" + totalComments + ", totalCommentsAnalyzed=" + totalCommentsAnalyzed
				+ ", totalViolentComments=" + totalViolentComments + ", totalCommentsAdultContent="
				+ totalCommentsAdultContent + ", totalCommentsDrugs=" + totalCommentsDrugs + ", totalCommentsBullying="
				+ totalCommentsBullying + ", totalCommentsNegativeSentiment=" + totalCommentsNegativeSentiment
				+ ", totalCommentsPositiveSentiment=" + totalCommentsPositiveSentiment
				+ ", totalCommentsNeutralSentiment=" + totalCommentsNeutralSentiment + "]";
	}

	

	

}
