package sanchez.sanchez.sergio.bullkeeper.persistence.constraints;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

import sanchez.sanchez.sergio.bullkeeper.persistence.repository.DeviceRepository;

public class DeviceShouldNotExistsValidator implements ConstraintValidator<DeviceShouldNotExists, String> {

    @Autowired
    private DeviceRepository deviceRepository;

    @Override
    public void initialize(DeviceShouldNotExists constraintAnnotation) {
    }

    @Override
    public boolean isValid(String deviceId, ConstraintValidatorContext context) {
        return deviceRepository.countByDeviceId(deviceId) == 0 ? Boolean.TRUE : Boolean.FALSE;
    }
}
