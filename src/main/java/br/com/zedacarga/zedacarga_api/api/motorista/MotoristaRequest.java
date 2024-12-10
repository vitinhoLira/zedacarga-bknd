package br.com.zedacarga.zedacarga_api.api.motorista;

import br.com.zedacarga.zedacarga_api.modelo.motorista.Motorista;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MotoristaRequest {

    @Column
    private String nome;

    @Column
    private String email;

    @Column
    private String numeroTelefone;

    @Column
    private String cpf;

    @Column
    private String numeroCnh;
    

    public Motorista build() {
        
        return Motorista.builder()
                .nome(nome)
                .email(email)
                .numeroTelefone(numeroTelefone)
                .numeroCnh(numeroCnh)
                .build();
    }
}
