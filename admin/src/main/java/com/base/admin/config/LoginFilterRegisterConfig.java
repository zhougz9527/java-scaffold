package com.base.admin.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LoginFilterRegisterConfig {

    @Bean
    public FilterRegistrationBean commonFilterRegistration() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(new LoginFilter());
        registration.setName("loginFilter");
        registration.addUrlPatterns("/admin/*");
        registration.addInitParameter("excludeUrls", "/admin/test/getUser2");
        registration.setOrder(1);
        return registration;
    }

}
