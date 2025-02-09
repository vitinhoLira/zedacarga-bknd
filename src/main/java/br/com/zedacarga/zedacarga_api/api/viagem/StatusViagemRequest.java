package br.com.zedacarga.zedacarga_api.api.viagem;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StatusViagemRequest {

  @Schema(description = "Novo status da viagem", example = "ACEITO", allowableValues = { "ACEITO",
      "RECUSADO", "CONCLUIDO" })
  @NotBlank(message = "O campo 'statusViagem' é obrigatório.")
  private String statusViagem;

}