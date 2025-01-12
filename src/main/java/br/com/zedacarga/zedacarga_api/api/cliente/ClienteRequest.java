package br.com.zedacarga.zedacarga_api.api.cliente;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

import br.com.zedacarga.zedacarga_api.modelo.cliente.Cliente;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClienteRequest {

    private String nome;

    @JsonFormat(pattern = "dd/MM/yyyy")
    @Schema(example = "08/08/1996", description = "Data de nascimento.")
    private LocalDate dataNascimento;

    private String cpf;

    private String telefone;

    private String foto;

    private String email;

    private String cep;

    private String residenciaNumero;
    

    public Cliente build() {
        
        return Cliente.builder()
                .nome(nome)
                .dataNascimento(dataNascimento)
                .cpf(cpf)
                .telefone(telefone)
                .foto(foto)
                .email(email)
                .cep(cep)
                .residenciaNumero(residenciaNumero)
                .build();
    }
}