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
                .allowedOrigins("*") // Altere para os domínios corretos
                .allowedMethods("*") // Permite os métodos HTTP
                .allowedHeaders("*")
                .allowedOriginPatterns("*") // Permite qualquer origem, mesmo com credentials
                .allowCredentials(false);

        // Permite CORS para a rota do WebSocket
        registry.addMapping("/ws/**")
                .allowedOrigins("*"); // Permite origens de qualquer domínio para WebSocket
    }

}
