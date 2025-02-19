package sanchez.sanchez.sergio.bullkeeper.mapper;

import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;

import sanchez.sanchez.sergio.bullkeeper.persistence.entity.DeviceEntity;
import sanchez.sanchez.sergio.bullkeeper.persistence.entity.DeviceGroupEntity;
import sanchez.sanchez.sergio.bullkeeper.web.dto.response.DeviceDTO;

/**
 * @author sergio
 */
@Mapper(unmappedTargetPolicy = org.mapstruct.ReportingPolicy.IGNORE)
public abstract class DeviceEntityMapper {
	
	/**
	 * 
	 * @param deviceEntity
	 * @return
	 */
	protected String parseOwnerId(DeviceEntity deviceEntity){
		String ownerId = null;
		if(deviceEntity.getDeviceGroup() != null) {
			final DeviceGroupEntity deviceGroup = deviceEntity.getDeviceGroup();
			if(deviceGroup.getOwner() != null)
				ownerId = deviceGroup.getOwner().getId().toString();
		}
		return ownerId;
	}
	

	/**
	 * 
	 * @param deviceEntity
	 * @return
	 */
    @Mappings({
    	@Mapping(source = "deviceEntity.createAt", target = "createAt", dateFormat = "yyyy/MM/dd"),
    	@Mapping(source = "deviceEntity.deviceGroup.notificationKeyName", target = "notificationKeyName"),
    	@Mapping(source = "deviceEntity.deviceGroup.notificationKey", target = "notificationKey"),
    	@Mapping(expression="java(parseOwnerId(deviceEntity))", target = "owner")
    })
    @Named("deviceEntityToDeviceDTO")
    public abstract DeviceDTO deviceEntityToDeviceDTO(DeviceEntity deviceEntity); 
	
    /**
     * 
     */
    @IterableMapping(qualifiedByName = "deviceEntityToDeviceDTO")
    public abstract Iterable<DeviceDTO> deviceEntitiesToDeviceDTO(Iterable<DeviceEntity> deviceEntities);
}
