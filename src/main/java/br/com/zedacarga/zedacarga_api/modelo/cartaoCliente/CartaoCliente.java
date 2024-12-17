package br.com.zedacarga.zedacarga_api.modelo.cartaoCliente;

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
@Table(name = "cartaoCliente")
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CartaoCliente extends EntidadeAuditavel {

    @Column
    private String numeroCartao;

    @Column
    private String tipoCartao;

    @Column
    private String dataVencimento;

    @Column
    private String cvv;

}
