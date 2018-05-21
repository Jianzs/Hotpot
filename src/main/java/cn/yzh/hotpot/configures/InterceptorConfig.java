package cn.yzh.hotpot.configures;

import cn.yzh.hotpot.web.interceptor.AuthorizationInterceptor;
import cn.yzh.hotpot.web.interceptor.ParseJWTInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class InterceptorConfig implements WebMvcConfigurer{
    private AuthorizationInterceptor authorizationInterceptor;
    private ParseJWTInterceptor parseJWTInterceptor;

    @Autowired
    public InterceptorConfig(AuthorizationInterceptor authorizationInterceptor,
                             ParseJWTInterceptor parseJWTInterceptor) {
        this.authorizationInterceptor = authorizationInterceptor;
        this.parseJWTInterceptor = parseJWTInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(parseJWTInterceptor)
                .addPathPatterns("/**");
        registry.addInterceptor(authorizationInterceptor)
                .addPathPatterns("/**").excludePathPatterns("/user/login");
    }
}
