package br.com.zedacarga.zedacarga_api.api.motorista;

import br.com.zedacarga.zedacarga_api.modelo.motorista.ContaBancariaMotorista;
import jakarta.persistence.Column;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ContaBancariaMotoristaRequest {

    @Column
    private String numeroConta;

    @Column
    private String agencia;

        public ContaBancariaMotorista build() {
     
            return ContaBancariaMotorista.builder()
                    .numeroConta(numeroConta)
                    .agencia(agencia)
                    .build();
        }
    
}
