package br.com.zedacarga.zedacarga_api.modelo.viagem;

import org.hibernate.annotations.SQLRestriction;

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
@Table(name="viagem")
@SQLRestriction("habilitado = true")
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Viagem extends EntidadeAuditavel {

    @Column
    private String origem;

    @Column
    private String destino;

    @Column
    private double valor;

    @Column
    private String status;

}