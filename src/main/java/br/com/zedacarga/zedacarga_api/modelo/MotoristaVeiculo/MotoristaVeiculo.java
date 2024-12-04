package br.com.zedacarga.zedacarga_api.modelo.motoristaVeiculo;

import br.com.zedacarga.zedacarga_api.util.entity.EntidadeAuditavel;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "MotoristaVeiculo")
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MotoristaVeiculo extends EntidadeAuditavel {

    @Column
    private Long idMotorista;

    @Column
    private Long idVeiculo;

}