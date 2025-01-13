package br.com.zedacarga.zedacarga_api.api.webhook;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/webhook")
@CrossOrigin
public class WebhookController {

    @Autowired
    private WebhookService webhookService;

    @Operation(summary = "Serviço webhook.", description = "Recebe eventos de webhook e processa com base no tipo de evento.")
    @PostMapping("/asaas")
    public ResponseEntity<Map<String, String>> handleAsaasWebhook(@RequestBody(required = false) WebhookPayload payload) {
        try {
            if (payload == null) {
                Map<String, String> errorResponse = new HashMap<>();
                errorResponse.put("error", "Payload não pode ser nulo.");
                return ResponseEntity.badRequest().header("Content-Type", "application/json").body(errorResponse);
            }

            ResponseEntity<String> serviceResponse = webhookService.processWebhook(payload);

            Map<String, String> response = new HashMap<>();
            response.put("message", serviceResponse.getBody());

            return ResponseEntity.status(serviceResponse.getStatusCode())
                    .header("Content-Type", "application/json")
                    .body(response);

        } catch (Exception ex) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "Erro ao processar o webhook: " + ex.getMessage());
            return ResponseEntity.internalServerError()
                    .header("Content-Type", "application/json")
                    .body(errorResponse);
        }
    }
}
