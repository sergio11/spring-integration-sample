package sanchez.sanchez.sergio.bullkeeper.i18n.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.MissingResourceException;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import sanchez.sanchez.sergio.bullkeeper.i18n.service.I18NService;

/**
 * 
 * @author sergiosanchezsanchez
 *
 */
@Service("i18nService")
public final class i18nServiceImpl implements I18NService {
	
	private static Logger logger = LoggerFactory.getLogger(i18nServiceImpl.class);
	
	
	private List<Locale> localeSupported = new ArrayList<>();
    
	/**
	 * 
	 * @param defaultLocale
	 */
    @Value("${i18n.default.locale}")
    public void setDefaultLocale(String defaultLocale) {
    	if(defaultLocale != null && !defaultLocale.isEmpty()) {
    		final Locale localeConfig = parseLocale(defaultLocale);
    		if(isValid(localeConfig)) {
    			logger.debug("Default Locale -> " + localeConfig.toString());
    			Locale.setDefault(localeConfig);
    		} else {
    			logger.debug("Invalid default locale ");
    		}
    	}
    }
    
    /**
     * Set Locale Supported
     * @param localeSupportedConfig
     */
    @Value("${i18n.locale.supported}")
    public void setLocaleSupported(String localeSupportedConfig) {
    	
    	final String[] localeSupportedArr = localeSupportedConfig.split(",");
    	for(String localeString: localeSupportedArr) {
    		final Locale currentLocale = parseLocale(localeString);
    		if(isValid(currentLocale)) {
    			localeSupported.add(currentLocale);
    		}
    	}
    }
    
	/**
	 * Parse Locale
	 */ 
	@Override
	public Locale parseLocale(String locale) {
		String[] parts = locale.split("_");
		switch (parts.length) {
		case 3:
			return new Locale(parts[0], parts[1], parts[2]);
		case 2:
			return new Locale(parts[0], parts[1]);
		case 1:
			return new Locale(parts[0]);
		default:
			throw new IllegalArgumentException("Invalid locale: " + locale);
		}
	}

	/**
	 * Is Valid
	 */
	@Override
	public boolean isValid(Locale locale) {
		try {
			return locale.getISO3Language() != null && locale.getISO3Country() != null;
		} catch (MissingResourceException e) {
			return false;
		}
	}

	/**
	 * Parse Locale or default
	 */
	@Override
	public Locale parseLocaleOrDefault(String locale) {
		Locale userLocale = Locale.getDefault();
		
		if(locale != null && !locale.isEmpty()) {
			final Locale localeParsed =  parseLocale(locale);
			logger.debug("Locale Parsed -> " + localeParsed);
    		if(isValid(localeParsed) && localeSupported.contains(localeParsed)) {
    			userLocale = localeParsed;
    		}
		}
		
		return userLocale;
	}

	/**
	 * Parse Locale Or Default
	 */
	@Override
	public Locale parseRangeLocaleOrDefault(String rangeLocale) {
		if (StringUtils.isBlank(rangeLocale)) {
        	return Locale.getDefault();
        }
        List<Locale.LanguageRange> list = Locale.LanguageRange.parse(rangeLocale);
        Locale locale = Locale.lookup(list, localeSupported);
        return locale;
	}
	
	@PostConstruct
    protected void init(){
    	logger.debug("Default locale -> " + Locale.getDefault());
    	logger.debug("Locale Supported -> " + localeSupported.toString());
    	
    }

}
