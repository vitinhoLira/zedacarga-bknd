package br.com.zedacarga.zedacarga_api.api.motorista;

import org.hibernate.validator.constraints.Length;

import br.com.zedacarga.zedacarga_api.modelo.motorista.ContaBancariaMotorista;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ContaBancariaMotoristaRequest {

    @NotNull(message = "O Nome é de preenchimento obrigatório")
    @NotEmpty(message = "O Nome é de preenchimento obrigatório")
    @Length(max = 25, message = "O Número deverá ter no máximo {max} caracteres")
    private String nomeBanco;

    @NotNull(message = "O número da conta bancária é obrigatório")
    @NotEmpty(message = "O número da conta bancária não pode estar em branco")
    @Pattern(regexp = "\\d{5,20}", message = "O número da conta bancária deve conter entre 5 e 20 dígitos numéricos")
    private String numeroConta;

    @NotNull(message = "O dígito da conta bancária é obrigatório")
    @NotEmpty(message = "O dígito da conta bancária não pode estar em branco")
    @Pattern(regexp = "[0-9A-Za-z]{1,2}", message = "O dígito da conta deve ter 1 ou 2 caracteres alfanuméricos")
    private String digitoConta;

    @NotNull(message = "O número da agência é obrigatório")
    @NotEmpty(message = "O número da agência não pode estar em branco")
    @Pattern(regexp = "\\d{4}", message = "O número da agência deve ter exatamente 4 dígitos")
    private String agencia;

    public ContaBancariaMotorista build() {

        return ContaBancariaMotorista.builder()
                .numeroConta(numeroConta)
                .agencia(agencia)
                .digitoConta(digitoConta)
                .nomeBanco(nomeBanco)
                .build();
    }

}
