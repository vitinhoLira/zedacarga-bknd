package br.com.zedacarga.zedacarga_api.api.motorista;

import java.time.LocalDate;

import br.com.zedacarga.zedacarga_api.modelo.motorista.Veiculo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VeiculoRequest {

    
    private String placa;

    private String modelo;

    private String renavam;

    private String cor;

    private String fotoVeiculo;

    private String ano;

    public Veiculo build() {
     
        return Veiculo.builder()
                .placa(placa)
                .modelo(modelo)
                .renavam(renavam)
                .cor(cor)
                .fotoVeiculo(fotoVeiculo)
                .ano(ano)
                .build();
    }
    
}
