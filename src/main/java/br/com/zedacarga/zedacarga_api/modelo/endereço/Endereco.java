package br.com.zedacarga.zedacarga_api.modelo.ende;

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
@Table(name = "Endereco")
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Endereco extends EntidadeAuditavel {

    @Column
    private String rua;

    @Column
    private String cidade;

    @Column
    private String estado;

    @Column
    private String numero;

    @Column
    private String complemento;

}
