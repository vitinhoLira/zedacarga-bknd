package br.com.zedacarga.zedacarga_api.api.motorista;

import br.com.zedacarga.zedacarga_api.modelo.motorista.Veiculo;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VeiculoRequest {

    @NotNull(message = "A placa do veículo é obrigatória")
    @NotEmpty(message = "A placa do veículo não pode estar em branco")
    @Pattern(regexp = "^[A-Z]{3}-\\d{4}$|^[A-Z]{3}\\d[A-Z]\\d{2}$", message = "A placa deve estar no formato 'AAA-9999' ou 'AAA9A99'")
    private String placa;

    @NotNull(message = "O Modelo do Veículo é obrigatório")
    @NotEmpty(message = "O Modelo do Veículo não pode estar em branco")
    private String modelo;

    @NotNull(message = "O RENAVAM é obrigatório")
    @NotEmpty(message = "O RENAVAM não pode estar em branco")
    @Pattern(regexp = "\\d{11}", message = "O RENAVAM deve conter exatamente 11 dígitos numéricos")
    private String renavam;

    @NotNull(message = "A cor do veículo é obrigatória")
    @NotEmpty(message = "A cor do veículo não pode estar em branco")
    @Size(max = 20, message = "A cor do veículo deve ter no máximo {max} caracteres")
    private String cor;

    @NotNull(message = "A foto é de preenchimento obrigatório")
    @NotEmpty(message = "A foto é de preenchimento obrigatório")
    private String fotoVeiculo;

    @NotNull(message = "O ano do veículo é obrigatório")
    @NotEmpty(message = "O ano do veículo não pode estar em branco")
    @Pattern(regexp = "^(19[0-9]{2}|20[0-9]{2})$", message = "O ano do veículo deve estar entre 1900 e o ano atual")
    private String ano;

    public Veiculo build() {

        return Veiculo.builder()
                .placa(placa)
                .modelo(modelo)
                .renavam(renavam)
                .cor(cor)
                .fotoVeiculo(fotoVeiculo)
                .ano(ano)
                .build();
    }

}
