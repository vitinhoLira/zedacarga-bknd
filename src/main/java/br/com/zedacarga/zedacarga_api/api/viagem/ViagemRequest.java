package br.com.zedacarga.zedacarga_api.api.viagem;

import br.com.zedacarga.zedacarga_api.modelo.viagem.Viagem;
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

    private double valor;

    private String numeroProtocolo;
    
    private String status;

    private String dataVencimentoCobranca;

    private String pgtoStatus;

    public Viagem build() {
        
        return Viagem.builder()

                .origem(origem)
                .destino(destino)
                .valor(valor)
                .status(status)
                .numeroProtocolo(numeroProtocolo)
                .dataVencimentoCobranca(dataVencimentoCobranca)
                .pgtoStatus(pgtoStatus)
                .build();
    }

}
