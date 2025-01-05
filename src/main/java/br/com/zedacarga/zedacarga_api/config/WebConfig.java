package br.com.zedacarga.zedacarga_api.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.lang.NonNull;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(@NonNull CorsRegistry registry) {
        // Permitir CORS para todas as rotas e permitir origens específicas
        registry.addMapping("/**")
                .allowedOrigins( "*")  // Altere para os domínios corretos
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")  // Permite os métodos HTTP
                .allowedHeaders("*")
                .allowCredentials(false);  // Permite todos os cabeçalhos
    }
}