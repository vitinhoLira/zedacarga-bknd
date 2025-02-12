package br.com.zedacarga.zedacarga_api.api.cliente;

import java.time.LocalDate;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.br.CPF;

import com.fasterxml.jackson.annotation.JsonFormat;

import br.com.zedacarga.zedacarga_api.modelo.cliente.Cliente;
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
public class ClienteRequest {

    @NotNull(message = "O Nome é de preenchimento obrigatório")
    @NotEmpty(message = "O Nome é de preenchimento obrigatório")
    @Length(max = 100, message = "O Nome deverá ter no máximo {max} caracteres")
    private String nome;

    @NotNull(message = "A Data é de preenchimento obrigatório")
    @JsonFormat(pattern = "dd/MM/yyyy")
    @Schema(example = "08/08/1996", description = "Data de nascimento.")
    private LocalDate dataNascimento;

    @NotBlank(message = "O CPF é de preenchimento obrigatório")
    @CPF
    private String cpf;

    @NotNull(message = "O Telefone é de preenchimento obrigatório")
    @NotEmpty(message = "O Telefone é de preenchimento obrigatório")
    @Length(min = 8, max = 20, message = "O campo Fone tem que ter entre {min} e {max} caracteres")
    private String telefone;

    @NotNull(message = "O Foto é de preenchimento obrigatório")
    @NotEmpty(message = "O Foto é de preenchimento obrigatório")
    private String foto;

    @Email(message = "O Email inserido não possui as caracteriscas de um email válido")
    private String email;

    @NotNull(message = "O Cep é de preenchimento obrigatório")
    @NotEmpty(message = "O Cep é de preenchimento obrigatório")
    @Length(max = 20, message = "O Número deverá ter no máximo {max} caracteres")
    private String cep;

    @NotNull(message = "O Número da Residência é de preenchimento obrigatório")
    @NotEmpty(message = "O Número da Residência é de preenchimento obrigatório")
    @Length(max = 20, message = "O Número deverá ter no máximo {max} caracteres")
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