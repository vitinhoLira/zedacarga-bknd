package br.com.zedacarga.zedacarga_api.api.cliente;

import java.time.YearMonth;

import com.fasterxml.jackson.annotation.JsonFormat;

import br.com.zedacarga.zedacarga_api.modelo.cliente.CartaoCliente;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CartaoClienteRequest {
     
        @Schema(example = "1234 1234 1234 1234", description = "Número do cartão de pagamento.")
        private String numeroCartao;
    
        @Schema(example = "CREDIT_CARD", description = "Tipo do cartão de pagamento no formato CREDIT_CARD")
        private String tipoCartao;
    
        @JsonFormat(pattern = "MM/yyyy")
        @Schema(example = "02/2025", description = "Data de vencimento do cartão no formato MM/yyyy") // Define o formato no Swagger
        private YearMonth dataVencimento;
    
        @Schema(example = "123", description = "Código de segurança do cartão.")
        private String cvv;
     
        public CartaoCliente build() {
     
            return CartaoCliente.builder()
                    .numeroCartao(numeroCartao)
                    .tipoCartao(tipoCartao)
                    .dataVencimento(dataVencimento)
                    .cvv(cvv)
                    .build();
        }

    
}
