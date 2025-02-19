package sanchez.sanchez.sergio.bullkeeper.helper.impl;

import java.util.Collections;
import java.util.Date;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import sanchez.sanchez.sergio.bullkeeper.domain.service.IDeviceGroupsService;
import sanchez.sanchez.sergio.bullkeeper.domain.service.ITokenGeneratorService;
import sanchez.sanchez.sergio.bullkeeper.exception.DeviceAddToGroupFailedException;
import sanchez.sanchez.sergio.bullkeeper.exception.DeviceGroupCreateFailedException;
import sanchez.sanchez.sergio.bullkeeper.exception.RemoveDeviceFromGroupFailedException;
import sanchez.sanchez.sergio.bullkeeper.fcm.properties.FCMCustomProperties;
import sanchez.sanchez.sergio.bullkeeper.fcm.service.IPushNotificationsService;
import sanchez.sanchez.sergio.bullkeeper.helper.IDeviceHelper;
import sanchez.sanchez.sergio.bullkeeper.persistence.entity.PendingDeviceEntity;
import sanchez.sanchez.sergio.bullkeeper.persistence.repository.PendingDeviceRepository;
import sanchez.sanchez.sergio.bullkeeper.util.Unthrow;
import sanchez.sanchez.sergio.bullkeeper.web.dto.response.DeviceDTO;
import sanchez.sanchez.sergio.bullkeeper.web.dto.response.DeviceGroupDTO;

@Component
public class DeviceHelperImpl implements IDeviceHelper {
	
	private static Logger logger = LoggerFactory.getLogger(DeviceHelperImpl.class);
	
	
	private final IDeviceGroupsService deviceGroupsService;
    private final IPushNotificationsService pushNotificationsService;
    private final ITokenGeneratorService tokenGeneratorService;
	private final FCMCustomProperties firebaseCustomProperties;
	private final PendingDeviceRepository pendingDeviceRepository;
	
	@Autowired
	public DeviceHelperImpl(IDeviceGroupsService deviceGroupsService,
			IPushNotificationsService pushNotificationsService, ITokenGeneratorService tokenGeneratorService,
			FCMCustomProperties firebaseCustomProperties, PendingDeviceRepository pendingDeviceRepository) {
		super();
		this.deviceGroupsService = deviceGroupsService;
		this.pushNotificationsService = pushNotificationsService;
		this.tokenGeneratorService = tokenGeneratorService;
		this.firebaseCustomProperties = firebaseCustomProperties;
		this.pendingDeviceRepository = pendingDeviceRepository;
	}

	@Override
	public Optional<DeviceDTO> addDeviceToGroup(ObjectId owner, String deviceId, String registrationToken) {
		
		return Optional.ofNullable(deviceGroupsService.getDeviceGroupByOwner(owner))
    			.map(deviceGroup -> Unthrow.wrap(() -> {
		   			// Device Group exists. Add Device to this group
		   			return pushNotificationsService.addDeviceToGroup(
		   					deviceGroup.getNotificationKeyName(), 
		   					deviceGroup.getNotificationKey(),
		   					registrationToken)
		   					.handle((groupKey, ex)  -> {
		   						
		   						if(ex != null) {
		   							logger.error(ex.getMessage());
		   							throw new DeviceAddToGroupFailedException();
		   						}
		   						
		   						logger.debug("Device Group exists. Add Device to this group");
		   						return Optional
		   								.ofNullable(
		   										deviceGroupsService.addDeviceToGroup( deviceId, registrationToken, deviceGroup.getIdentity()))
		   								.<DeviceAddToGroupFailedException>orElseThrow(() -> new DeviceAddToGroupFailedException());
		   						}).get();
                       }));
	}

	@Override
	public DeviceDTO createDeviceGroupWithDevice(ObjectId owner, String deviceId, String registrationToken) {

		return Unthrow.wrap(() -> { 
   			
   			final String notificationGroupName = String.format("%s_%s", firebaseCustomProperties.getGroupPrefix(), tokenGeneratorService.generateToken(owner.toString()));
   			
   			return pushNotificationsService.createNotificationGroup(notificationGroupName, 
   				Collections.singletonList(registrationToken))
                           .handle((response, ex) -> {
                               
	   			if (
	                                   ex != null || 
	                                   response == null || 
	                                   response.getBody() == null || 
	                                   ( !response.getBody().containsKey("notification_key") 
	                                   || response.getBody().get("notification_key").isEmpty())) {
	                               throw new DeviceGroupCreateFailedException();
	                           }
	                           
	                           String groupKey = response.getBody().get("notification_key");
	                           logger.debug("Group Key -> " + 	groupKey);
	                           	logger.debug("Device Group Created With Key -> " + groupKey);
	                           	DeviceGroupDTO deviceGroup = deviceGroupsService.createDeviceGroup(groupKey, notificationGroupName, owner);
	   			logger.debug("Device Group -> " + deviceGroup.toString());
	   			return deviceGroupsService.addDeviceToGroup(deviceId, registrationToken, deviceGroup.getIdentity());
	   		}).get(); 
   			
   		});
	}

	@Override
	public Optional<DeviceDTO> updateDevice(ObjectId owner, String deviceId, String registrationToken) {
		return Optional.ofNullable(deviceGroupsService.getDeviceByDeviceId(deviceId))
				 .map((deviceSaved) -> deviceSaved.getOwner() != null && deviceSaved.getOwner().equals(owner.toString())  ? 
						 deviceSaved.getRegistrationToken() != null && 
		                   !deviceSaved.getRegistrationToken().isEmpty() &&
		                   deviceSaved.getRegistrationToken().equals(registrationToken) ? 
		                   deviceSaved : Unthrow.wrap(() -> pushNotificationsService.updateDeviceToken(deviceSaved.getNotificationKeyName(), 
		                           deviceSaved.getNotificationKey(), deviceSaved.getRegistrationToken(), registrationToken)
							   		.handle((result, ex) -> {
							                       if(ex != null) {
							                           logger.error(ex.getMessage());
							                           throw new DeviceAddToGroupFailedException();
							                       }
							                       logger.debug("Registration Token for device " + deviceSaved.getDeviceId() + " updated");
							                       deviceGroupsService.updateDeviceToken(deviceSaved.getDeviceId(), registrationToken);
							                       deviceSaved.setRegistrationToken( registrationToken);
							                       return deviceSaved;
							   		}).get()) : removeDeviceFromGroup(deviceId).orElseGet(() -> 
						   								addDeviceToGroup(owner, deviceId, registrationToken)
											   				.orElseGet(() -> createDeviceGroupWithDevice(owner, deviceId, registrationToken)))
	     );
	}

	@Override
	public Optional<DeviceDTO> removeDeviceFromGroup(String deviceId) {
		return Optional.ofNullable(deviceGroupsService.getDeviceByDeviceId(deviceId))
            	.map(device -> pushNotificationsService.removeDeviceFromGroup(device.getNotificationKeyName(), 
            		device.getNotificationKey(), device.getRegistrationToken())
            		.handle((groupKey, ex)
            				-> Optional.ofNullable(deviceGroupsService.removeDevice(device.getDeviceId()))
	                        .<RemoveDeviceFromGroupFailedException>orElseThrow(() -> {
	                            throw new RemoveDeviceFromGroupFailedException();
	                        })))
            .map(completableFuture -> Unthrow.wrap(() -> completableFuture.get()));
	}

	@Override
	public DeviceDTO createOrUpdateDevice(ObjectId owner, String deviceId, String registrationToken) {
		
		try {
			
			DeviceDTO device = updateDevice(owner, deviceId, registrationToken)
	   		// Device not Exists. First check if device group already exists
	   		.orElseGet(() -> 
	   			addDeviceToGroup(owner, deviceId, registrationToken)
	   			// Device Group not exists. Create new Device Group with this device
			   	.orElseGet(() -> createDeviceGroupWithDevice(owner, deviceId, registrationToken)));
			
			pendingDeviceRepository.deleteByDeviceId(device.getDeviceId());
			
			return device;
			
		} catch (Exception ex) {
			
			PendingDeviceEntity pendingDevice = pendingDeviceRepository.findByDeviceIdAndOwner(deviceId, owner);
			if(pendingDevice == null) {
				pendingDevice = new PendingDeviceEntity(deviceId, registrationToken, owner);
			} else {
				if(pendingDevice.getFailedAttempts() < 5) {
					pendingDevice.setFailedAttempts(pendingDevice.getFailedAttempts() + 1);
					pendingDevice.setLastTimeTried(new Date());
				} else {
					pendingDeviceRepository.delete(pendingDevice);
					
				}
				
			}
			
			pendingDeviceRepository.save(pendingDevice);
			throw ex;
		}
	}

}
