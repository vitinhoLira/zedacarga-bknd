package br.com.zedacarga.zedacarga_api.api.motorista;

import java.time.LocalDate;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.br.CPF;

import com.fasterxml.jackson.annotation.JsonFormat;

import br.com.zedacarga.zedacarga_api.config.validation.CNH;
import br.com.zedacarga.zedacarga_api.modelo.motorista.Motorista;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MotoristaRequest {

    @NotNull(message = "O Nome é de preenchimento obrigatório")
    @NotEmpty(message = "O Nome é de preenchimento obrigatório")
    @Length(max = 100, message = "O Número deverá ter no máximo {max} caracteres")
    private String nome;

    @Email(message = "O Email inserido não possui as caracteriscas de um email válido")
    private String email;

    @JsonFormat(pattern = "dd/MM/yyyy")
    @Schema(example = "08/08/1996", description = "Data de nascimento.")
    private LocalDate dataNascimento;

    @Length(min = 8, max = 20, message = "O campo Fone tem que ter entre {min} e {max} caracteres")
    private String numeroTelefone;

    @NotBlank(message = "O CPF é de preenchimento obrigatório")
    @CPF
    private String cpf;

    @CNH
    private String numeroCnh;

    @NotNull(message = "A foto é de preenchimento obrigatório")
    @NotEmpty(message = "A foto é de preenchimento obrigatório")
    private String foto;

    @NotNull(message = "O Endereço é de preenchimento obrigatório")
    @NotEmpty(message = "O Endereço é de preenchimento obrigatório")
    @Length(max = 100, message = "O Número deverá ter no máximo {max} caracteres")
    private String endereco;

    @NotNull(message = "A Cidade é de preenchimento obrigatório")
    @NotEmpty(message = "A Cidade é de preenchimento obrigatório")
    @Length(max = 30, message = "O Número deverá ter no máximo {max} caracteres")
    private String cidade;

    @NotNull(message = "O Estado é de preenchimento obrigatório")
    @NotEmpty(message = "O Estado é de preenchimento obrigatório")
    @Length(max = 30, message = "O Número deverá ter no máximo {max} caracteres")
    private String estado;

    @NotNull(message = "O Número da Residência é de preenchimento obrigatório")
    @NotEmpty(message = "O Número da Residência é de preenchimento obrigatório")
    @Length(max = 20, message = "O Número deverá ter no máximo {max} caracteres")
    private String numero;

    @NotNull(message = "O Bairro é de preenchimento obrigatório")
    @NotEmpty(message = "O Bairro é de preenchimento obrigatório")
    @Length(max = 30, message = "O Número deverá ter no máximo {max} caracteres")
    private String bairro;

    @NotNull(message = "O CEP é de preenchimento obrigatório")
    @NotEmpty(message = "O CEP é de preenchimento obrigatório")
    @Length(max = 20, message = "O Número deverá ter no máximo {max} caracteres")
    private String cep;

    @Length(max = 60, message = "O Número deverá ter no máximo {max} caracteres")
    private String complemento;

    public Motorista build() {

        return Motorista.builder()
                .nome(nome)
                .email(email)
                .dataNascimento(dataNascimento)
                .numeroTelefone(numeroTelefone)
                .cpf(cpf)
                .foto(foto)
                .numeroCnh(numeroCnh)
                .endereco(endereco)
                .cidade(cidade)
                .estado(estado)
                .numero(numero)
                .bairro(bairro)
                .cep(cep)
                .complemento(complemento)
                .build();
    }
}
