package sanchez.sanchez.sergio.bullkeeper.persistence.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import org.bson.types.ObjectId;
import org.joda.time.LocalTime;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import sanchez.sanchez.sergio.bullkeeper.persistence.utils.CascadeSave;

/**
 * Scheduled Block 
 * @author sergiosanchezsanchez
 */
@Document(collection = ScheduledBlockEntity.COLLECTION_NAME)
public class ScheduledBlockEntity implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Collection Name
	 */
	public final static String COLLECTION_NAME = "scheduled_blocks";
	
	@Id
    private ObjectId id;
	
	/**
	 * Name
	 */
	@Field("name")
    private String name;
	
	/**
	 * Enable
	 */
	@Field("enable")
    private Boolean enable;
	
	/**
	 * Create At
	 */
	@Field("create_at")
	private Date createAt = new Date();
	
	/**
	 * Repeatable
	 */
	@Field("repeatable")
    private Boolean repeatable;
	
	
	/**
	 * Allow Calls
	 */
	@Field("allow_calls")
    private Boolean allowCalls;
	
	/**
	 * Description
	 */
	@Field("description")
	private String description;
	
	/**
	 * Start at
	 */
	@Field("start_at")
    private LocalTime startAt;
	
	/**
	 * End at
	 */
	@Field("end_at")
    private LocalTime endAt;
	
	/**
	 * Weekly Frequency
	 */
	@Field("weekly_frequency")
	private int[] weeklyFrequency;
	
	/**
	 * Kid
	 */
	@DBRef
	@Field("kid")
	private KidEntity kid;
	
	/**
	 * Image
	 */
	@Field("image")
	private String image;

	/**
	 * Apps Allowed
	 */
	@Field("apps_allowed")
	@CascadeSave
	private Iterable<AppAllowedByScheduledBlockEntity> appAllowed = new ArrayList<>();
	
	/**
	 * Geofence
	 */
	@DBRef
	@Field("geofence")
	private GeofenceEntity geofence;
	
	
	/**
	 * 
	 */
	public ScheduledBlockEntity() {}

	/**
	 * 
	 * @param id
	 * @param name
	 * @param enable
	 * @param createAt
	 * @param repeatable
	 * @param allowCalls
	 * @param description
	 * @param startAt
	 * @param endAt
	 * @param weeklyFrequency
	 * @param kid
	 * @param image
	 * @param appAllowed
	 * @param geofence
	 */
	@PersistenceConstructor
	public ScheduledBlockEntity(ObjectId id, String name, Boolean enable, 
			final Date createAt, Boolean repeatable, 
			Boolean allowCalls, String description, LocalTime startAt, LocalTime endAt, 
			int[] weeklyFrequency, KidEntity kid, final String image,
			final Iterable<AppAllowedByScheduledBlockEntity> appAllowed,
			final GeofenceEntity geofence) {
		super();
		this.id = id;
		this.name = name;
		this.enable = enable;
		this.repeatable = repeatable;
		this.allowCalls = allowCalls;
		this.description = description;
		this.startAt = startAt;
		this.endAt = endAt;
		this.weeklyFrequency = weeklyFrequency;
		this.kid = kid;
		this.image = image;
		this.appAllowed = appAllowed;
		this.geofence = geofence;
	}

	public ObjectId getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public Boolean isEnable() {
		return enable;
	}

	public Date getCreateAt() {
		return createAt;
	}

	public void setCreateAt(Date createAt) {
		this.createAt = createAt;
	}

	public Boolean isRepeatable() {
		return repeatable;
	}

	public LocalTime getStartAt() {
		return startAt;
	}

	public LocalTime getEndAt() {
		return endAt;
	}

	public int[] getWeeklyFrequency() {
		return weeklyFrequency;
	}

	public void setId(ObjectId id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setEnable(Boolean enable) {
		this.enable = enable;
	}

	public void setRepeatable(Boolean repeatable) {
		this.repeatable = repeatable;
	}


	public boolean isAllowCalls() {
		return allowCalls;
	}

	public void setAllowCalls(Boolean allowCalls) {
		this.allowCalls = allowCalls;
	}
	
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setStartAt(LocalTime startAt) {
		this.startAt = startAt;
	}

	public void setEndAt(LocalTime endAt) {
		this.endAt = endAt;
	}

	public void setWeeklyFrequency(int[] weeklyFrequency) {
		this.weeklyFrequency = weeklyFrequency;
	}


	public KidEntity getKid() {
		return kid;
	}

	public void setKid(KidEntity kid) {
		this.kid = kid;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public Iterable<AppAllowedByScheduledBlockEntity> getAppAllowed() {
		return appAllowed;
	}

	public void setAppAllowed(Iterable<AppAllowedByScheduledBlockEntity> appAllowed) {
		this.appAllowed = appAllowed;
	}

	public GeofenceEntity getGeofence() {
		return geofence;
	}

	public void setGeofence(GeofenceEntity geofence) {
		this.geofence = geofence;
	}
	
}
