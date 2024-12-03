package br.com.zedacarga.zedacarga_api.modelo.motorista;

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
@Table(name = "SaldoMotorista")
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SaldoMotorista extends EntidadeAuditavel {

    @Column
    private Long idMotorista;

    @Column
    private double saldo;

}