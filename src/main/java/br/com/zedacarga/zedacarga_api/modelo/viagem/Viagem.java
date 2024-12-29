package br.com.zedacarga.zedacarga_api.modelo.viagem;

import org.hibernate.annotations.SQLRestriction;

import br.com.zedacarga.zedacarga_api.modelo.cliente.Cliente;
import br.com.zedacarga.zedacarga_api.modelo.motorista.Motorista;
import br.com.zedacarga.zedacarga_api.util.entity.EntidadeAuditavel;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "viagem")
@SQLRestriction("habilitado = true")
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Viagem extends EntidadeAuditavel {

    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;

    @ManyToOne
    @JoinColumn(name = "motorista_id")
    private Motorista motorista;

    @Column
    private String origem;

    @Column
    private String destino;

    @Column
    private double valor;

    @Column
    private String status;

}