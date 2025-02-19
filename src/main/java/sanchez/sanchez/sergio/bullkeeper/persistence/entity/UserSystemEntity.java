
package sanchez.sanchez.sergio.bullkeeper.persistence.entity;

import java.util.Date;
import java.util.Locale;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * @author sergio
 */
@Document(collection = UserSystemEntity.COLLECTION_NAME)
public class UserSystemEntity extends PersonEntity {

    public final static String COLLECTION_NAME = "users";

    /**
     * Email
     */
    @Field("email")
    protected String email;
    
    /**
     * User Name
     */
    @Field("user_name")
    protected String userName;

    /**
     * Password
     */
    @Field("password")
    protected String password;

    /**
     * Password Request At
     */
    @Field("password_requested_at")
    protected String passwordRequestedAt;

    /**
     * Is Active
     */
    @Field("is_active")
    protected Boolean active = Boolean.TRUE;

    /**
     * Is Locked
     */
    @Field("is_locked")
    protected Boolean locked = Boolean.FALSE;

    /**
     * Last Login Access
     */
    @Field("last_login_access")
    protected Date lastLoginAccess;

    /**
     * Pending Deletion
     */
    @Field("pending_deletion")
    protected Boolean pendingDeletion = Boolean.FALSE;

    /**
     * Locale
     */
    @Field("locale")
    protected Locale locale = Locale.getDefault();

    /**
     * Last Password Reset Date
     */
    @Field("last_password_reset_date")
    protected Date lastPasswordResetDate;

 
    @Field("confirmation_token")
    protected String confirmationToken;

    /**
     * Last Access To Alerts
     */
    @Field("last_access_to_alerts")
    protected Date lastAccessToAlerts = new Date();
    
    /**
     * Preferences
     */
    @Field("preferences")
    protected PreferencesEntity preferences = new PreferencesEntity();

    /**
     * Authority
     */
    @DBRef
    protected AuthorityEntity authority;

    public UserSystemEntity() {
    }

    @PersistenceConstructor
    public UserSystemEntity(String firstName, String lastName, Date birthdate, String profileImage, String email,
            String userName, String password, String passwordRequestedAt, Boolean active, Boolean locked, Date lastLoginAccess,
            Boolean pendingDeletion, Locale locale, Date lastPasswordResetDate, String confirmationToken,
            Date lastAccessToAlerts, PreferencesEntity preferences, AuthorityEntity authority) {
        super(firstName, lastName, birthdate, profileImage);
        this.userName = userName;
        this.email = email;
        this.password = password;
        this.passwordRequestedAt = passwordRequestedAt;
        this.active = active;
        this.locked = locked;
        this.lastLoginAccess = lastLoginAccess;
        this.pendingDeletion = pendingDeletion;
        this.locale = locale;
        this.lastPasswordResetDate = lastPasswordResetDate;
        this.confirmationToken = confirmationToken;
        this.lastAccessToAlerts = lastAccessToAlerts;
        this.preferences = preferences;
        this.authority = authority;
    }

    public UserSystemEntity(String firstName, String lastName, Date birthdate,  String email, String userName, String password, AuthorityEntity authority) {
        super(firstName, lastName, birthdate);
        this.userName = userName;
        this.email = email;
        this.password = password;
        this.authority = authority;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    
    

    public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Boolean getActive() {
		return active;
	}

	public Boolean getLocked() {
		return locked;
	}

	public Boolean getPendingDeletion() {
		return pendingDeletion;
	}

	public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean isLocked() {
        return locked;
    }

    public void setLocked(Boolean locked) {
        this.locked = locked;
    }

    public Date getLastLoginAccess() {
        return lastLoginAccess;
    }

    public void setLastLoginAccess(Date lastLoginAccess) {
        this.lastLoginAccess = lastLoginAccess;
    }

    public AuthorityEntity getAuthority() {
        return authority;
    }

    public void setAuthority(AuthorityEntity authority) {
        this.authority = authority;
    }

    public String getPasswordRequestedAt() {
        return passwordRequestedAt;
    }

    public void setPasswordRequestedAt(String passwordRequestedAt) {
        this.passwordRequestedAt = passwordRequestedAt;
    }

    public Boolean isActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public String getConfirmationToken() {
        return confirmationToken;
    }

    public void setConfirmationToken(String confirmationToken) {
        this.confirmationToken = confirmationToken;
    }

    public Boolean isPendingDeletion() {
        return pendingDeletion;
    }

    public void setPendingDeletion(Boolean pendingDeletion) {
        this.pendingDeletion = pendingDeletion;
    }

    public Locale getLocale() {
        return locale;
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }

    public Date getLastPasswordResetDate() {
        return lastPasswordResetDate;
    }

    public void setLastPasswordResetDate(Date lastPasswordResetDate) {
        this.lastPasswordResetDate = lastPasswordResetDate;
    }


    public Date getLastAccessToAlerts() {
        return lastAccessToAlerts;
    }

    public void setLastAccessToAlerts(Date lastAccessToAlerts) {
        this.lastAccessToAlerts = lastAccessToAlerts;
    }
    

	public PreferencesEntity getPreferences() {
		return preferences;
	}

	public void setPreferences(PreferencesEntity preferences) {
		this.preferences = preferences;
	}

	@Override
	public String toString() {
		return "UserSystemEntity [email=" + email + ", userName=" + userName + ", password=" + password
				+ ", passwordRequestedAt=" + passwordRequestedAt + ", active=" + active + ", locked=" + locked
				+ ", lastLoginAccess=" + lastLoginAccess + ", pendingDeletion=" + pendingDeletion + ", locale=" + locale
				+ ", lastPasswordResetDate=" + lastPasswordResetDate + ", confirmationToken=" + confirmationToken
				+ ", lastAccessToAlerts=" + lastAccessToAlerts + ", preferences=" + preferences + ", authority="
				+ authority + "]";
	}

	
}
