package sanchez.sanchez.sergio.bullkeeper.web.dto.response;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.springframework.hateoas.ResourceSupport;
import com.fasterxml.jackson.annotation.JsonProperty;

import sanchez.sanchez.sergio.bullkeeper.persistence.entity.AlertLevelEnum;

public class KidDTO extends ResourceSupport implements Serializable{
	

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
	 * Alert Statistics
	 */
	@JsonProperty("alert_statistics")
	private Map<AlertLevelEnum, Long> alertsStatistics;
	
	/**
	 * Terminals
	 */
	@JsonProperty("terminals")
	private Iterable<TerminalDTO> terminals;
	
	/**
	 * Current Location
	 */
	@JsonProperty("current_location")
	private LocationDTO currentLocation;
	
	
    public KidDTO(){}
   
    /**
     * 
     * @param identity
     * @param firstName
     * @param lastName
     * @param birthdate
     * @param age
     * @param school
     * @param profileImage
     * @param alertsStatistics
     * @param terminals
     * @param currentLocation
     */
	public KidDTO(String identity, String firstName, String lastName, 
			String birthdate, Integer age, SchoolDTO school, String profileImage, 
			Map<AlertLevelEnum, Long> alertsStatistics, Iterable<TerminalDTO> terminals,
			final LocationDTO currentLocation) {
		super();
		this.identity = identity;
		this.firstName = firstName;
		this.lastName = lastName;
		this.birthdate = birthdate;
		this.age = age;
		this.school = school;
		this.profileImage = profileImage;
		this.alertsStatistics = alertsStatistics;
		this.terminals = terminals;
		this.currentLocation = currentLocation;
	}

	public String getBirthdate() {
		return birthdate;
	}

	public void setBirthdate(String birthdate) {
		this.birthdate = birthdate;
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
    
	public Map<AlertLevelEnum, Long> getAlertsStatistics() {
		return alertsStatistics;
	}

	public void setAlertsStatistics(Map<AlertLevelEnum, Long> alertsStatistics) {
		this.alertsStatistics = alertsStatistics;
	}
	
	public Iterable<TerminalDTO> getTerminals() {
		return terminals;
	}

	public void setTerminals(Iterable<TerminalDTO> terminals) {
		this.terminals = terminals;
	}
	
	

	public LocationDTO getCurrentLocation() {
		return currentLocation;
	}

	public void setCurrentLocation(LocationDTO currentLocation) {
		this.currentLocation = currentLocation;
	}

	@Override
	public String toString() {
		return "KidDTO [identity=" + identity + ", firstName=" + firstName + ", lastName=" + lastName + ", birthdate="
				+ birthdate + ", age=" + age + ", school=" + school + ", profileImage=" + profileImage
				+ ", alertsStatistics=" + alertsStatistics + ", terminals=" + terminals + "]";
	}

	
}
