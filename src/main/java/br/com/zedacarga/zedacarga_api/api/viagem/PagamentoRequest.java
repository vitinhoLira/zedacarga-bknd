package br.com.zedacarga.zedacarga_api.api.viagem;

import br.com.zedacarga.zedacarga_api.modelo.viagem.Pagamento;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PagamentoRequest {

    @Schema(hidden = true)
    private String protocoloId;

    @Schema(hidden = true)
    private String comprovante;

    public Pagamento build() {

        return Pagamento.builder()
                .protocoloId(protocoloId)
                .comprovante(comprovante)
                .build();
    }

}
