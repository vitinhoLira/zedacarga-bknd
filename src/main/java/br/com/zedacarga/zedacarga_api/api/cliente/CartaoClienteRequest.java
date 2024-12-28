package br.com.zedacarga.zedacarga_api.api.cliente;

import com.fasterxml.jackson.annotation.JsonFormat;

import br.com.zedacarga.zedacarga_api.modelo.cliente.CartaoCliente;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CartaoClienteRequest {
     
        private String numeroCartao;
    
        private String tipoCartao;
    
        @JsonFormat(pattern = "dd/MM/yyyy")
        private String dataVencimento;
    
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
