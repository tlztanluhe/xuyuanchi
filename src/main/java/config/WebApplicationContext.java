package config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.GsonHttpMessageConverter;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.handler.AbstractHandlerMapping;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;
import org.springframework.web.servlet.view.JstlView;
import org.springframework.web.servlet.view.UrlBasedViewResolver;

import ml.rugal.sshcommon.springmvc.method.annotation.FormModelMethodArgumentResolver;
import rugal.sample.interceptor.LoginInterceptor;

/**
 * Java based Web context configuration class.
 *
 * @author Rugal Bernstein
 * @since 0.2
 */
@Configuration
@EnableWebMvc
@ComponentScan(basePackages =
{
    "rugal.sample.controller"
})
public class WebApplicationContext extends WebMvcConfigurerAdapter
{
	   //<mvc:resources/> tags
//    @Override
//    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//    	System.out.println("setting up resource handlers");
//    	registry.addResourceHandler("/frozen/**").addResourceLocations("/frozen/").setCachePeriod(31556926);;
//        registry.addResourceHandler("/css/**").addResourceLocations("/css/");
////        registry.addResourceHandler("/img/**").addResourceLocations("/img/");
////        registry.addResourceHandler("/js/**").addResourceLocations("/js/");
//    }
    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer)
    {
        configurer.enable();
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers)
    {
        argumentResolvers.add(new FormModelMethodArgumentResolver());
    }

    @Override
    public void configureContentNegotiation(ContentNegotiationConfigurer configurer)
    {
        configurer.favorPathExtension(false).favorParameter(false);
        configurer.defaultContentType(MediaType.APPLICATION_JSON);
        configurer.mediaType("json", MediaType.APPLICATION_JSON);
    }

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters)
    {
        GsonHttpMessageConverter messageConverter = new GsonHttpMessageConverter();
        List<MediaType> supportedMediaTypes = new ArrayList<>();
        supportedMediaTypes.add(MediaType.APPLICATION_JSON);
        messageConverter.setSupportedMediaTypes(supportedMediaTypes);
        converters.add(messageConverter);
    }

    /***
     * jsp视图解析器
     * @return
     */
    @Bean
    public ViewResolver resolver()
    {
//        InternalResourceViewResolver resolver = new InternalResourceViewResolver();
//        resolver.setPrefix("/WEB-INF/views/");
//        resolver.setSuffix(".jsp");
//        return resolver;
        UrlBasedViewResolver url = new UrlBasedViewResolver();
        url.setPrefix("/WEB-INF/views/");
        url.setViewClass(JstlView.class);
        url.setSuffix(".jsp");
        return url;
    }
    @Bean
    public AbstractHandlerMapping defaultAnnotationHandlerMapping()
    {
        RequestMappingHandlerMapping mapping = new RequestMappingHandlerMapping();
        mapping.setUseSuffixPatternMatch(false);
        return mapping;
    }
    
    
    /****
     * 配置拦截器
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
         //registry.addInterceptor(new LocaleInterceptor(someService));
    	//用户登录拦截器
    	registry.addInterceptor(new LoginInterceptor());	
//    	  registry.addInterceptor(new MyCustomInterceptor())
//          .addPathPatterns("/**")
//          .excludePathPatterns("/foo/**");
    }
    
 
}
