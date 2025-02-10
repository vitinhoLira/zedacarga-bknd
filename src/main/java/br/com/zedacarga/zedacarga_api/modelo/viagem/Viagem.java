package br.com.zedacarga.zedacarga_api.modelo.viagem;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import br.com.zedacarga.zedacarga_api.modelo.cliente.CartaoCliente;
import br.com.zedacarga.zedacarga_api.modelo.cliente.Cliente;
import br.com.zedacarga.zedacarga_api.modelo.motorista.ContaBancariaMotorista;
import br.com.zedacarga.zedacarga_api.modelo.motorista.Motorista;
import br.com.zedacarga.zedacarga_api.modelo.viagem.ViagemStatusEnum.StatusViagem;
import br.com.zedacarga.zedacarga_api.util.entity.EntidadeAuditavel;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.hibernate.annotations.SQLRestriction;

@Entity
@Table(name = "viagem")
@SQLRestriction("habilitado = true")
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Viagem extends EntidadeAuditavel {

    // Relacionamento com a Conta Bancária do Motorista
    @OneToOne
    @JsonIgnore // Para evitar que o relacionamento seja serializado
    @JoinColumn(name = "pagamento_viagem_id")
    private Pagamento pagamento;

    // Relacionamento com a Conta Bancária do Motorista
    @ManyToOne
    @JsonIgnore // Para evitar que o relacionamento seja serializado
    @JoinColumn(name = "conta_bancaria_motorista_id")
    private ContaBancariaMotorista contaBancariaMotorista;

    // Relacionamento com o Cartão do Cliente
    @ManyToOne
    @JsonIgnore // Para evitar que o relacionamento seja serializado
    @JoinColumn(name = "cartao_cliente_id")
    private CartaoCliente cartaoCliente;

    @ManyToOne
    @JsonIgnore // Ignora a serialização recursiva do cliente dentro de viagem
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;

    // Relacionamento com o Motorista
    @ManyToOne
    @JsonBackReference // Relacionamento bidirecional, evita recursão infinita
    @JoinColumn(name = "motorista_id")
    private Motorista motorista;

    // Detalhes da Viagem
    @Column(nullable = false, length = 100)
    private String origem;

    @Column(nullable = false, length = 100)
    private String destino;

    @Column(nullable = false, precision = 12)
    private double valor;

    // Status do pagamento
    @Column
    private String pgtoStatus;

    @Column
    @Builder.Default
    @Enumerated(EnumType.STRING)
    private StatusViagem statusViagem = StatusViagem.PENDENTE;

    @Column
    private String numeroProtocolo;

    // Data de Vencimento da Cobrança, com valor padrão
    @Column
    @Builder.Default
    private String dataVencimentoCobranca = LocalDate.now()
            .plusDays(1)
            .format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

    @Column
    private String viagemComprovante;
}
