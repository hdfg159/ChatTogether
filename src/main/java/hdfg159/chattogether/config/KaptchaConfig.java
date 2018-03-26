package hdfg159.chattogether.config;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.util.Config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.util.Properties;

/**
 * Project:ChatTogether
 * Package:hdfg159.chattogether.config
 * Created by hdfg159 on 18-3-26 上午9:37.
 */
@Configuration
@PropertySource(value = {"classpath:kaptchaconfig.properties"})
public class KaptchaConfig {
	@Value("${kaptcha.border}")
	private String border;
	
	@Value("${kaptcha.border.color}")
	private String borderColor;
	
	@Value("${kaptcha.textproducer.font.color}")
	private String textproducerFontColor;
	
	@Value("${kaptcha.image.width}")
	private String imageWidth;
	
	@Value("${kaptcha.image.height}")
	private String imageHeight;
	
	@Value("${kaptcha.session.key}")
	private String sessionKey;
	
	@Value("${kaptcha.textproducer.char.length}")
	private String textproducerCharLength;
	
	@Value("${kaptcha.textproducer.font.names}")
	private String textproducerFontNames;
	
	@Bean(name = "kaptchaProducer")
	public DefaultKaptcha kaptchaBean() {
		DefaultKaptcha defaultKaptcha = new DefaultKaptcha();
		Properties properties = new Properties();
		properties.setProperty("kaptcha.border", border);
		properties.setProperty("kaptcha.border.color", borderColor);
		properties.setProperty("kaptcha.textproducer.font.color", textproducerFontColor);
		properties.setProperty("kaptcha.image.width", imageWidth);
		properties.setProperty("kaptcha.image.height", imageHeight);
		properties.setProperty("kaptcha.session.key", sessionKey);
		properties.setProperty("kaptcha.textproducer.char.length", textproducerCharLength);
		properties.setProperty("kaptcha.textproducer.font.names", textproducerFontNames);
		defaultKaptcha.setConfig(new Config(properties));
		return defaultKaptcha;
	}
}
