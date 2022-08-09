package me.nanqic.raffle.config;

import me.nanqic.raffle.filter.LoginInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/*
 * @Description: 在web的配置文件中，实例化登陆的拦截器，并添加规则
 * 异步请求需要同时排除/login接口和login.html的拦截
 */
@Configuration
public class WebConfigurer implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginInterceptor())
                .addPathPatterns("/**").excludePathPatterns("/login")
                .excludePathPatterns("/api/list")
                .excludePathPatterns("/index.html")
                .excludePathPatterns("/login.html", "/static/**");
    }
}

