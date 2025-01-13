package br.com.zedacarga.zedacarga_api.api.webhook;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class WebhookService {

    public ResponseEntity<String> processWebhook(WebhookPayload payload) {
        // Verifique se o payload está completo
        if (payload == null || payload.getData() == null || 
            payload.getData().getId() == null || payload.getData().getStatus() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                                 .body("Dados de transferência incompletos.");
        }
    
        // Se os dados estiverem corretos, continue o processamento
        if ("TRANSFER_CREATED".equals(payload.getEvent())) {
            // Lógica para processar a transferência
            return ResponseEntity.ok("Transferência confirmada");
        }
    
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Evento não suportado");
    }
    
}

