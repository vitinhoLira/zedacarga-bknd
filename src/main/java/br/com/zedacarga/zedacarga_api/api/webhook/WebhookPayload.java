package br.com.zedacarga.zedacarga_api.api.webhook;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WebhookPayload {
    private String event;
    private TransferData data;

    // Getters e setters
    @Getter
    @Setter
    public static class TransferData {
        private String id;
        private String status;
        // Outros campos relevantes

        // Getters e setters
    }
}
