package br.com.zedacarga.zedacarga_api.modelo.motorista;

import com.fasterxml.jackson.annotation.JsonIgnore;

import br.com.zedacarga.zedacarga_api.modelo.viagem.Viagem;
import br.com.zedacarga.zedacarga_api.util.entity.EntidadeAuditavel;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.hibernate.annotations.SQLRestriction;

@Entity
@Table(name = "contaBancariaMotorista")
@SQLRestriction("habilitado = true")
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ContaBancariaMotorista extends EntidadeAuditavel {

    @OneToOne(mappedBy = "contaBancariaMotorista")
    @JsonIgnore
    private Viagem viagem; // Relacionamento inverso;

    @JsonIgnore
    @ManyToOne
    private Motorista motorista;

    @Column(nullable = false, length = 25)
    private String nomeBanco;

    @Column(nullable = false, length = 20)
    private String numeroConta;

    @Column(nullable = false, length = 2)
    private String digitoConta;

    @Column(nullable = false, length = 4)
    private String agencia;

}