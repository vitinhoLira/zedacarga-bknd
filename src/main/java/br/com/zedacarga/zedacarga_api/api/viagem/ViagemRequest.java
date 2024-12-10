package br.com.zedacarga.zedacarga_api.api.viagem;

import br.com.zedacarga.zedacarga_api.modelo.viagem.Viagem;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ViagemRequest {

    @Column
    private String origem;

    @Column
    private String destino;

    @Column
    private double valor;

    @Column
    private String status;

    public Viagem build() {
        
        return Viagem.builder()

                .origem(origem)
                .destino(destino)
                .valor(valor)
                .status(status)
                .build();
    }

}
