package br.com.zedacarga.zedacarga_api.api.cliente;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

import br.com.zedacarga.zedacarga_api.modelo.cliente.Cliente;
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
     private LocalDate dataNascimento;

    private String cpf;

    private String telefone;

    private String foto;

    private String email;

    private String rua;

    private String cidade;

    private String estado;

    private String numero;

    private String bairro;

    private String cep;

    private String complemento;

    private String asaasId;
    

    public Cliente build() {
        
        return Cliente.builder()
                .nome(nome)
                .dataNascimento(dataNascimento)
                .cpf(cpf)
                .telefone(telefone)
                .foto(foto)
                .email(email)
                .asaasId(asaasId)
                .build();
    }
}