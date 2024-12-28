package br.com.zedacarga.zedacarga_api.modelo.motorista;

import com.fasterxml.jackson.annotation.JsonIgnore;

import br.com.zedacarga.zedacarga_api.util.entity.EntidadeAuditavel;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "contaBancariaMotorista")
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ContaBancariaMotorista extends EntidadeAuditavel {

    @JsonIgnore
    @ManyToOne
    private Motorista motorista;

    @Column
    private String numeroConta;

    @Column
    private String agencia;

}