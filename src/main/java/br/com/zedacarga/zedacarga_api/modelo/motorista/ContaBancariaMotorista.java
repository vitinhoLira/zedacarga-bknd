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
    private Viagem viagem;  // Relacionamento inverso;
    
    @JsonIgnore
    @ManyToOne
    private Motorista motorista;

    @Column
    private String nomeBanco;

    @Column
    private String numeroConta;

    @Column
    private String digitoConta;

    @Column
    private String agencia;

}