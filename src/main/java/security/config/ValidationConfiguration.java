package security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import security.service.model.validation.UserValidation;

@Configuration
public class ValidationConfiguration {

    @Bean
    public UserValidation userValidation() {
        return new UserValidation();
    }
}
