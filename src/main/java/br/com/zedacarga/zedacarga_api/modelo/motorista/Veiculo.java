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

    @Column(nullable = false, length = 20)
    private String placa;

    @Column(nullable = false, length = 30)
    private String modelo;

    @Column(nullable = false, length = 11)
    private String renavam;

    @Column(nullable = false, length = 20)
    private String cor;

    @Column(nullable = false)
    private String fotoVeiculo;

    @Column(nullable = false, length = 10)
    private String ano;

}