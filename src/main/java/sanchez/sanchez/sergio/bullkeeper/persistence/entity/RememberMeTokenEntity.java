package sanchez.sanchez.sergio.bullkeeper.persistence.entity;

import java.util.Date;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;


/**
 * Remember
 * @author sergiosanchezsanchez
 *
 */
@Document(collection = RememberMeTokenEntity.COLLECTION_NAME)
public class RememberMeTokenEntity {
	
	public final static String COLLECTION_NAME = "remembermetokens";
	
	/**
	 * Id
	 */
	@Id
    private ObjectId id;
	
	/**
	 * Series
	 */
	@Field("series")
    private String series;
	
	/**
	 * Username
	 */
	@Field("username")
    private String username;
	
	/**
	 * Token Values
	 */
	@Field("token_values")
    private String tokenValue;
	
	/**
	 * Date
	 */
	@Field("date")
    private Date date;
    
    public RememberMeTokenEntity(){}
    
    @PersistenceConstructor
    public RememberMeTokenEntity(String series, String username, String tokenValue, Date date) {
		super();
		this.series = series;
		this.username = username;
		this.tokenValue = tokenValue;
		this.date = date;
	}

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getSeries() {
        return series;
    }

    public void setSeries(String series) {
        this.series = series;
    }

    public String getTokenValue() {
        return tokenValue;
    }

    public void setTokenValue(String tokenValue) {
        this.tokenValue = tokenValue;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

}
