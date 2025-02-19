package sanchez.sanchez.sergio.bullkeeper.mapper;

import java.util.List;

import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.api.services.oauth2.model.Userinfoplus;

import sanchez.sanchez.sergio.bullkeeper.i18n.service.I18NService;
import sanchez.sanchez.sergio.bullkeeper.web.dto.request.RegisterGuardianByGoogleDTO;

@Mapper(unmappedTargetPolicy = org.mapstruct.ReportingPolicy.IGNORE)
public abstract class GoogleMapper {
	
	@Autowired
	protected I18NService i18nService;
	
	@Mappings({
		@Mapping(source = "userInfo.givenName", target = "firstName"),
		@Mapping(source = "userInfo.familyName", target = "lastName"),
		@Mapping(source = "userInfo.email", target = "email"),
		@Mapping(source = "userInfo.id", target = "googleId"),
		@Mapping(source = "userInfo.id", target = "passwordClear"),
		@Mapping(source = "userInfo.id", target = "confirmPassword"),
		@Mapping(expression="java(i18nService.parseLocaleOrDefault(userInfo.getLocale()))", target = "locale")
	})
    @Named("userInfoPlusToRegisterParentByGoogleDTO")
	public abstract RegisterGuardianByGoogleDTO userInfoPlusToRegisterParentByGoogleDTO(Userinfoplus userInfo); 
	
    @IterableMapping(qualifiedByName = "userInfoPlusToRegisterParentByGoogleDTO")
    public abstract List<RegisterGuardianByGoogleDTO> usersInfoPlusToRegisterParentByGoogleDTO(List<Userinfoplus> usersInfo);

}
