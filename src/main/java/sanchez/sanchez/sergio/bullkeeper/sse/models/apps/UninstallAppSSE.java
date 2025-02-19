package sanchez.sanchez.sergio.bullkeeper.sse.models.apps;

import java.io.Serializable;
import com.fasterxml.jackson.annotation.JsonProperty;
import sanchez.sanchez.sergio.bullkeeper.sse.models.AbstractSseData;

/**
 * Uninstall App SSE
 * @author sergiosanchezsanchez
 *
 */
public final class UninstallAppSSE
	extends AbstractSseData implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Event Type
	 */
	public final static String EVENT_TYPE = "UNINSTALL_APP_EVENT";
	
	/**
	 * Identity
	 */
	@JsonProperty("identity")
    private String identity;
	
	/**
	 * Package Name
	 */
	@JsonProperty("package_name")
	private String packageName;
	
	/**
	 * First Install Time
	 */
	@JsonProperty("first_install_time")
	private long firstInstallTime;
	
	/**
	 * Last Update Time
	 */
	@JsonProperty("last_update_time")
	private long lastUpdateTime;
	
	/**
	 * Version Name
	 */
	@JsonProperty("version_name")
	private String versionName;
	
	/**
	 * Version Code
	 */
	@JsonProperty("version_code")
	private String versionCode;
	
	/**
	 * App Name
	 */
	@JsonProperty("app_name")
	private String appName;
	
	/**
	 * App Rule
	 */
	@JsonProperty("app_rule")
	private String appRule;
	
	
	/**
	 * Icon Encoded String
	 */
	@JsonProperty("icon_encoded_string")
	private String iconEncodedString;
	
	/**
	 * KId
	 */
	@JsonProperty("kid")
	private String kid;
	
	/**
	 * Terminal ID
	 */
	@JsonProperty("terminal")
	private String terminalId;
	
	/**
	 * Disabled
	 */
	@JsonProperty("disabled")
	private Boolean disabled;
	
	/**
	 * 
	 */
	public UninstallAppSSE() {
		this.eventType = EVENT_TYPE;
	}

	/**
	 * 
	 * @param subscriberId
	 * @param identity
	 * @param packageName
	 * @param firstInstallTime
	 * @param lastUpdateTime
	 * @param versionName
	 * @param versionCode
	 * @param appName
	 * @param appRule
	 * @param iconEncodedString
	 * @param kid
	 * @param terminalId
	 * @param disabled
	 */
	public UninstallAppSSE(String subscriberId, String identity, String packageName,
			long firstInstallTime, long lastUpdateTime, String versionName, String versionCode, String appName,
			String appRule, String iconEncodedString, String kid, String terminalId, Boolean disabled) {
		super(EVENT_TYPE, subscriberId);
		this.identity = identity;
		this.packageName = packageName;
		this.firstInstallTime = firstInstallTime;
		this.lastUpdateTime = lastUpdateTime;
		this.versionName = versionName;
		this.versionCode = versionCode;
		this.appName = appName;
		this.appRule = appRule;
		this.iconEncodedString = iconEncodedString;
		this.kid = kid;
		this.terminalId = terminalId;
		this.disabled = disabled;
	}

	public String getIdentity() {
		return identity;
	}

	public String getPackageName() {
		return packageName;
	}

	public long getFirstInstallTime() {
		return firstInstallTime;
	}

	public long getLastUpdateTime() {
		return lastUpdateTime;
	}

	public String getVersionName() {
		return versionName;
	}

	public String getVersionCode() {
		return versionCode;
	}

	public String getAppName() {
		return appName;
	}

	public String getAppRule() {
		return appRule;
	}

	public String getIconEncodedString() {
		return iconEncodedString;
	}

	public String getKid() {
		return kid;
	}

	public String getTerminalId() {
		return terminalId;
	}

	public Boolean getDisabled() {
		return disabled;
	}

	public void setIdentity(String identity) {
		this.identity = identity;
	}

	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}

	public void setFirstInstallTime(long firstInstallTime) {
		this.firstInstallTime = firstInstallTime;
	}

	public void setLastUpdateTime(long lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}

	public void setVersionName(String versionName) {
		this.versionName = versionName;
	}

	public void setVersionCode(String versionCode) {
		this.versionCode = versionCode;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	public void setAppRule(String appRule) {
		this.appRule = appRule;
	}

	public void setIconEncodedString(String iconEncodedString) {
		this.iconEncodedString = iconEncodedString;
	}

	public void setKid(String kid) {
		this.kid = kid;
	}

	public void setTerminalId(String terminalId) {
		this.terminalId = terminalId;
	}

	public void setDisabled(Boolean disabled) {
		this.disabled = disabled;
	}

	@Override
	public String toString() {
		return "UninstallAppSSE [identity=" + identity + ", packageName=" + packageName + ", firstInstallTime="
				+ firstInstallTime + ", lastUpdateTime=" + lastUpdateTime + ", versionName=" + versionName
				+ ", versionCode=" + versionCode + ", appName=" + appName + ", appRule=" + appRule
				+ ", iconEncodedString=" + iconEncodedString + ", kid=" + kid + ", terminalId=" + terminalId
				+ ", disabled=" + disabled + "]";
	}

	

}
