package br.com.zedacarga.zedacarga_api.api.cliente;

import java.time.YearMonth;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonFormat;

import br.com.zedacarga.zedacarga_api.modelo.cliente.CartaoCliente;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CartaoClienteRequest {

        @NotNull(message = "O Número do Cartão é de preenchimento obrigatório")
        @NotEmpty(message = "O Número do Cartão é de preenchimento obrigatório")
        @Length(max = 19, message = "O Número deverá ter no máximo {max} caracteres")
        @Schema(example = "1234 1234 1234 1234", description = "Número do cartão de pagamento.")
        private String numeroCartao;

        @NotNull(message = "O Tipo do Cartão é de preenchimento obrigatório")
        @NotEmpty(message = "O Tipo do Cartão é de preenchimento obrigatório")
        @Length(max = 11, message = "O Número deverá ter no máximo {max} caracteres")
        @Schema(example = "CREDIT_CARD", description = "Tipo do cartão de pagamento no formato CREDIT_CARD")
        private String tipoCartao;

        @NotNull(message = "A Data de Validade do Cartão é de preenchimento obrigatório")
        @JsonFormat(pattern = "MM/yyyy")
        @Schema(example = "02/2025", description = "Data de vencimento do cartão no formato MM/yyyy") // Swagger
        private YearMonth dataVencimento;

        @NotNull(message = "O Código de Segurança é de preenchimento obrigatório")
        @NotEmpty(message = "O Código de Segurança é de preenchimento obrigatório")
        @Length(max = 3, message = "O Número deverá ter no máximo {max} caracteres")
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
