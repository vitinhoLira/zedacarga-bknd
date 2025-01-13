package br.com.zedacarga.zedacarga_api.modelo.viagem;

import br.com.zedacarga.zedacarga_api.util.entity.EntidadeAuditavel;
import jakarta.persistence.CascadeType;
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
@Table(name = "pagamento")
@SQLRestriction("habilitado = true")
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Pagamento extends EntidadeAuditavel{

    @OneToOne(mappedBy = "pagamento", cascade = CascadeType.ALL)
    @JsonIgnore
    private Viagem viagem;
    
    @Column
    private String protocoloId;

    @Column
    private String comprovante;

    @Column
    @Builder.Default
    private String statusTransferenciaMotorista = "Ainda não houve transferência";


    
}
