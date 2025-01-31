package br.com.zedacarga.zedacarga_api.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.lang.NonNull;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(@NonNull CorsRegistry registry) {
        // Permitir CORS para todas as rotas
        registry.addMapping("/**")
                .allowedOriginPatterns("*") // Substitui allowedOrigins("*") corretamente
                .allowedMethods("*") // Permite todos os métodos HTTP
                .allowedHeaders("*")
                .allowCredentials(true); // Permite credenciais (cookies, headers auth)

        // Permite CORS para a rota do WebSocket
        registry.addMapping("/ws/**")
                .allowedOriginPatterns("*"); // Corrige para WebSocket
    }
}
