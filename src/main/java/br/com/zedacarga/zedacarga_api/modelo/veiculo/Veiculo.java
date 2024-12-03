package br.com.zedacarga.zedacarga_api.modelo.veiculo;

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
@Table(name = "Veiculo")
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Veiculo extends EntidadeAuditavel {

    @Column
    private String placa;

    @Column
    private String modelo;

    @Column
    private String renavam;

    @Column
    private String cor;

}