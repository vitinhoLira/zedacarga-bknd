package br.com.zedacarga.zedacarga_api.modelo.cliente;

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
@Table(name = "CartaoCliente")
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CartaoCliente extends EntidadeAuditavel {

    @JsonIgnore
    @ManyToOne
    private Cliente cliente;

    @Column
    private String numeroCartao;

    @Column
    private String tipoCartao;

    @Column
    private String dataVencimento;

    @Column
    private String cvv;

}
