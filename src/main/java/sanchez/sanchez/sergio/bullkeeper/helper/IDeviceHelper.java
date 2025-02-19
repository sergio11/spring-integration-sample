package sanchez.sanchez.sergio.bullkeeper.helper;

import java.util.Optional;

import org.bson.types.ObjectId;

import sanchez.sanchez.sergio.bullkeeper.web.dto.response.DeviceDTO;

public interface IDeviceHelper {
	
	Optional<DeviceDTO> addDeviceToGroup(final ObjectId owner, final String deviceId, final String registrationToken);
	DeviceDTO createDeviceGroupWithDevice(final ObjectId owner, final String deviceId, final String registrationToken);
	Optional<DeviceDTO> updateDevice(final ObjectId owner, final String deviceId, final String registrationToken);
	DeviceDTO createOrUpdateDevice(final ObjectId owner, final String deviceId, final String registrationToken);
	Optional<DeviceDTO> removeDeviceFromGroup(final String deviceId);

}
