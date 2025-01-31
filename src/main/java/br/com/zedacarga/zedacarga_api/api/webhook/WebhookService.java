package br.com.zedacarga.zedacarga_api.api.webhook;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Map;

@Service
public class WebhookService {

    public ResponseEntity<String> processWebhook(WebhookPayload payload) {
        // 1. Verifique se as informações básicas estão todas presentes
        if (payload == null || payload.getData() == null || 
            payload.getData().getId() == null || payload.getData().getStatus() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                                 .body("Dados de transferência incompletos.");
        }
    
        // 2. Verifique qual é o evento que chegou e tome a ação correta
        switch (payload.getEvent()) {
            case "TRANSFER_CREATED":
                // Responda que a transferência foi criada
                return ResponseEntity.ok("Transferência criada.");
            case "TRANSFER_PENDING":
                // Chame a função para confirmar a transferência pendente
                confirmPendingTransfer(payload.getData().getId());
                return ResponseEntity.ok("Transferência pendente confirmada.");
            default:
                // Qualquer outro evento não é suportado
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Evento não suportado");
        }
    }

    private void confirmPendingTransfer(String transferId) {
        // Função que confirma a transferência pendente
        System.out.println("Confirmando transferência pendente com ID: " + transferId);
        // Aqui você adiciona a lógica que realmente confirma a transferência
    }
}


