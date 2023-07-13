package ea.slartibartfast.walletservice.infrastructure.config;

import ea.slartibartfast.walletservice.infrastructure.interceptor.CorrelationIdInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(final InterceptorRegistry registry) {
        registry.addInterceptor(new CorrelationIdInterceptor());
    }
}
