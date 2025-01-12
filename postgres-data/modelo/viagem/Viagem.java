package br.com.zedacarga.zedacarga_api.modelo.viagem;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

import br.com.zedacarga.zedacarga_api.modelo.cliente.CartaoCliente;
import br.com.zedacarga.zedacarga_api.modelo.cliente.Cliente;
import br.com.zedacarga.zedacarga_api.modelo.motorista.ContaBancariaMotorista;
import br.com.zedacarga.zedacarga_api.modelo.motorista.Motorista;
import br.com.zedacarga.zedacarga_api.util.entity.EntidadeAuditavel;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "viagem")
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
    @OneToOne
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
    @Column
    private String origem;

    @Column
    private String destino;

    @Column
    private double valor;

    // Status do pagamento
    @Column
    private String pgtoStatus;

    @Column
    private String statusViagem;

    @Column
    private String numeroProtocolo;

    // Data de Vencimento da Cobrança, com valor padrão
    @Builder.Default
    private String dataVencimentoCobranca = LocalDate.now()
            .plusDays(1)
            .format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
}
