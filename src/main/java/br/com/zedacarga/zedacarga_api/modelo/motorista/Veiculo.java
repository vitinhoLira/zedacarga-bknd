package br.com.zedacarga.zedacarga_api.modelo.motorista;

import br.com.zedacarga.zedacarga_api.util.entity.EntidadeAuditavel;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.hibernate.annotations.SQLRestriction;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "veiculo")
@SQLRestriction("habilitado = true")
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Veiculo extends EntidadeAuditavel {

    @OneToOne
    @JsonIgnore
    private Motorista motorista;

    @Column
    private String placa;

    @Column
    private String modelo;

    @Column
    private String renavam;

    @Column
    private String cor;

    @Column
    private String fotoVeiculo;

    @Column
    private String ano;

}