package br.com.zedacarga.zedacarga_api.api.viagem;

import br.com.zedacarga.zedacarga_api.modelo.viagem.Viagem;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ViagemRequest {
    
    private String origem;

    private String destino;
    @Schema(example = "30.0", description = "Valor da viagem.")
    private double valor;
    @Schema(example = "pendente, andamento, concluido", description = "Valor da viagem.")
    private String statusViagem;

    public Viagem build() {
        
        return Viagem.builder()

                .origem(origem)
                .destino(destino)
                .valor(valor)
                .statusViagem(statusViagem)
                .build();
    }

}
