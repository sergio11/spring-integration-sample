package sanchez.sanchez.sergio.bullkeeper.web.dto.request;


import org.hibernate.validator.constraints.NotBlank;
import com.fasterxml.jackson.annotation.JsonProperty;

import sanchez.sanchez.sergio.bullkeeper.persistence.constraints.DeviceShouldExists;


public class UpdateDeviceDTO {
	
	@DeviceShouldExists(message = "{device.should.exists}")
	@JsonProperty("device_id")
	private String deviceId;
	
	@NotBlank(message = "{device.registration.token.not.blank}")
	@JsonProperty("registration_token")
	private String registrationToken;
	
	
	public UpdateDeviceDTO(){}
	

	public UpdateDeviceDTO(String deviceId, String registrationToken) {
		super();
		this.deviceId = deviceId;
		this.registrationToken = registrationToken;
	}


	public String getDeviceId() {
		return deviceId;
	}


	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}


	public String getRegistrationToken() {
		return registrationToken;
	}


	public void setRegistrationToken(String registrationToken) {
		this.registrationToken = registrationToken;
	}
}
