package com.sxkl.project.cloudnote.config;

import com.google.common.collect.Sets;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Set;

@Configuration
public class WebSecurityConfig implements WebMvcConfigurer {

    /**
     * 登录session key
     */
    public final static String SESSION_KEY = "user";
    private static final Set<String> RESOURCE_PATH = Sets.newHashSet("/css/", "/fonts/", "/images/",
            "/js/", "pages/", "/plugins/", "/scss/");

    @Bean
    public SecurityInterceptor getSecurityInterceptor() {
        return new SecurityInterceptor();
    }

//    @Override
//    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//        registry.addResourceHandler("/css/**").addResourceLocations("classpath:/resources/static/css/");
//        registry.addResourceHandler("/fonts/**").addResourceLocations("classpath:/resources/static/fonts/");
//        registry.addResourceHandler("/images/**").addResourceLocations("classpath:/resources/static/images/");
//        registry.addResourceHandler("/js/**").addResourceLocations("classpath:/static/js/");
//        registry.addResourceHandler("/pages/**").addResourceLocations("classpath:/resources/static/pages/");
//        registry.addResourceHandler("/plugins/**").addResourceLocations("classpath:/resources/static/plugins/");
//        registry.addResourceHandler("/scss/**").addResourceLocations("classpath:/resources/static/scss/");
//    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        InterceptorRegistration addInterceptor = registry.addInterceptor(getSecurityInterceptor());

        addInterceptor.excludePathPatterns("/error");
        addInterceptor.excludePathPatterns("/login**");
        addInterceptor.excludePathPatterns("/es");

        addInterceptor.addPathPatterns("/**");
    }

    private class SecurityInterceptor extends HandlerInterceptorAdapter {

        @Override
        public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
                throws Exception {
//            String servletPath = request.getServletPath();
//            boolean match = RESOURCE_PATH.stream().anyMatch(path -> servletPath.startsWith(path));
//            if(match) {
//                return true;
//            }
            HttpSession session = request.getSession();
            if (session.getAttribute(SESSION_KEY) != null){
                return true;
            }
            String url = "login";
            response.sendRedirect(url);
            return false;
        }
    }
}
