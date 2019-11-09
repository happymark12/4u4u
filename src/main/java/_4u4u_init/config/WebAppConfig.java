package _4u4u_init.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

@Configuration
@EnableWebMvc
@ComponentScan({"_4u4u","_4u4u_init"})
public class WebAppConfig implements WebMvcConfigurer {
	@Bean
	public ViewResolver internalResourceViewResolver() {
		InternalResourceViewResolver resolver = new InternalResourceViewResolver();
		// resolver.setViewClass(JstlView.class); // 新版的Spring框架不需要這一行
		resolver.setPrefix("/WEB-INF/views/");
		resolver.setSuffix(".jsp");
		return resolver;
	}
	
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/css/**").addResourceLocations("/WEB-INF/views/css/");
		registry.addResourceHandler("/img/**").addResourceLocations("/WEB-INF/views/img/");
		registry.addResourceHandler("/js/**").addResourceLocations("/WEB-INF/views/js/");
		registry.addResourceHandler("/fonts/**").addResourceLocations("/WEB-INF/views/fonts/");

	}
	
	@Override
	public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
		configurer.enable();

	}

//	@Bean(name = "multipartResolver")
//	public StandardServletMultipartResolver resolver() {
//		return new StandardServletMultipartResolver();
//	}


//	@Bean
//	public MessageSource messageSource() {
//		ResourceBundleMessageSource resource = new ResourceBundleMessageSource();
//		resource.setBasename("messages");
//		return resource;
//	}
}
