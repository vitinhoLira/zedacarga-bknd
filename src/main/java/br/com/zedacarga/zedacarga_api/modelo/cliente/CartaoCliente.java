package br.com.zedacarga.zedacarga_api.modelo.cliente;

import java.time.YearMonth;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import br.com.zedacarga.zedacarga_api.modelo.viagem.Viagem;
import br.com.zedacarga.zedacarga_api.util.entity.EntidadeAuditavel;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.hibernate.annotations.SQLRestriction;

@Entity
@Table(name = "CartaoCliente")
@SQLRestriction("habilitado = true")
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CartaoCliente extends EntidadeAuditavel {

    @OneToMany(mappedBy = "cartaoCliente", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Viagem> viagens;

    @ManyToOne
    @JsonIgnore
    private Cliente cliente;

    @Column
    private String numeroCartao;

    @Column
    private String tipoCartao;

    @Column
    private YearMonth dataVencimento;

    @Column
    private String cvv;

}
