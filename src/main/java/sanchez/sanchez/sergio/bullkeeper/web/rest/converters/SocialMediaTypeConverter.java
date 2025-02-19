package sanchez.sanchez.sergio.bullkeeper.web.rest.converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import sanchez.sanchez.sergio.bullkeeper.persistence.entity.SocialMediaTypeEnum;

@Component
public class SocialMediaTypeConverter implements Converter<String, SocialMediaTypeEnum> {

	@Override
	public SocialMediaTypeEnum convert(String socialMedia) {
		return SocialMediaTypeEnum.valueOf(socialMedia);
	}

}
