package hdfg159.chattogether.config.condition;

import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.type.AnnotatedTypeMetadata;

/**
 * Project:ChatTogether
 * Package:hdfg159.chattogether.config.condition
 * Created by hdfg159 on 2018-4-23 14:06.
 */
@Configuration
@PropertySource(value = {"classpath:appconfig.properties"})
public class DBServerCondition implements Condition {
	@Override
	public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
		return context.getEnvironment().getProperty("database.embedded").equals("false");
	}
}
