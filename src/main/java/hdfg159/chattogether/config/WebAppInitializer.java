package hdfg159.chattogether.config;

import org.springframework.context.annotation.Bean;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import javax.servlet.MultipartConfigElement;
import javax.servlet.ServletRegistration;

/**
 * Project:ChatTogether
 * Package:hdfg159.chattogether.config
 * Created by hdfg159 on 2017/6/29 21:31.
 */
public class WebAppInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {
	@Override
	protected Class<?>[] getRootConfigClasses() {
		return new Class<?>[]{RootConfig.class};
	}
	
	@Override
	protected Class<?>[] getServletConfigClasses() {
		return new Class<?>[]{WebConfig.class};
	}
	
	@Override
	protected String[] getServletMappings() {
		return new String[]{"/"};
	}
	
	@Override
	protected void customizeRegistration(ServletRegistration.Dynamic registration) {
		//set location empty
		registration.setMultipartConfig(new MultipartConfigElement("", 10485760, 15728640, 0));
	}
	
	/**
	 * multipart request bean
	 *
	 * @return MultipartResolver
	 *
	 * @throws Exception
	 */
	@Bean
	public MultipartResolver multipartResolver() throws Exception {
		return new StandardServletMultipartResolver();
	}

//	@Override
//	protected FilterRegistration.Dynamic registerServletFilter(ServletContext servletContext, Filter filter) {
//		FilterRegistration.Dynamic filterRegistration = servletContext.addFilter("OpenEntityManagerInViewFilter", OpenEntityManagerInViewFilter.class);
//		filterRegistration.setInitParameter("entityManagerFactoryBeanName", "entityManagerFactory");
//		filterRegistration.setInitParameter("singleSession", "false");
//		filterRegistration.addMappingForUrlPatterns(null, true, "/*");
//		return filterRegistration;
//	}
}
