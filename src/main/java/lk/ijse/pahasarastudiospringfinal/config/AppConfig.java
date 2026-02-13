package lk.ijse.pahasarastudiospringfinal.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class AppConfig {

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    /* If you add ModelMapper to pom.xml, uncomment this:
    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
    */
}