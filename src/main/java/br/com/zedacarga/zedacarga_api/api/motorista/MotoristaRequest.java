package br.com.zedacarga.zedacarga_api.api.motorista;

import java.time.LocalDate;

import org.springframework.cglib.core.Local;

import com.fasterxml.jackson.annotation.JsonFormat;

import br.com.zedacarga.zedacarga_api.modelo.motorista.Motorista;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MotoristaRequest {

    private String nome;

    private String email;

    @JsonFormat(pattern = "dd/MM/yyyy")
    @Schema(example = "08/08/1996", description = "Data de nascimento.")
    private LocalDate dataNascimento;

    private String numeroTelefone;

    private String cpf;

    private String numeroCnh;

    private String foto;

    private String endereco;

    private String cidade;

    private String estado;

    private String numero;

    private String bairro;

    private String cep;

    private String complemento;

    private double rendaMensal;
    

    public Motorista build() {
        
        return Motorista.builder()
                .nome(nome)
                .email(email)
                .dataNascimento(dataNascimento)
                .numeroTelefone(numeroTelefone)
                .cpf(cpf)
                .foto(foto)
                .endereco(endereco)
                .cidade(cidade)
                .estado(estado)
                .numero(numero)
                .bairro(bairro)
                .cep(cep)
                .complemento(complemento)
                .rendaMensal(rendaMensal)
                .build();
    }
}
