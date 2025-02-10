package br.com.zedacarga.zedacarga_api.api.viagem;

import br.com.zedacarga.zedacarga_api.modelo.viagem.Viagem;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ViagemRequest {

    @NotNull(message = "A origem é obrigatória.")
    @NotEmpty(message = "A origem não pode estar vazia.")
    @Size(max = 100, message = "A origem deve ter no máximo {max} caracteres.")
    private String origem;

    @NotNull(message = "A origem é obrigatória.")
    @NotEmpty(message = "A origem não pode estar vazia.")
    @Size(max = 100, message = "A origem deve ter no máximo {max} caracteres.")
    private String destino;

    @NotNull(message = "O valor da viagem é obrigatório.")
    @DecimalMin(value = "0.01", message = "O valor da viagem deve ser no mínimo R$ 0,01.")
    @Digits(integer = 10, fraction = 2, message = "O valor deve ter no máximo 10 dígitos inteiros e 2 casas decimais.")
    @Schema(example = "30.00", description = "Valor da viagem.")
    private double valor;

    public Viagem build() {

        return Viagem.builder()

                .origem(origem)
                .destino(destino)
                .valor(valor)
                .build();
    }

}
