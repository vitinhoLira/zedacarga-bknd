package br.com.zedacarga.zedacarga_api.modelo.cliente;

import java.time.LocalDate;
import java.util.List;
import org.hibernate.annotations.SQLRestriction;
import com.fasterxml.jackson.annotation.JsonIgnore;

import br.com.zedacarga.zedacarga_api.modelo.viagem.Viagem;
import br.com.zedacarga.zedacarga_api.util.entity.EntidadeAuditavel;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "cliente")
@SQLRestriction("habilitado = true")
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class Cliente extends EntidadeAuditavel {

    @OneToMany(mappedBy = "cliente", orphanRemoval = true, fetch = FetchType.EAGER)
    private List<CartaoCliente> cartoes;

    @OneToMany(mappedBy = "cliente")
    @JsonIgnore // Ignora a serialização recursiva de viagens dentro de cliente
    private List<Viagem> viagens;

    @Column(nullable = false, length = 100)
    private String nome;

    @Column(nullable = false)
    private LocalDate dataNascimento;

    @Column(nullable = false, length = 20)
    private String telefone;

    @Column(nullable = false)
    private String email;

    @Column(unique = true, nullable = false)
    private String cpf;

    @Column
    private String foto;

    @Column
    private String asaasId;

    @Column(nullable = false, length = 20)
    private String cep;

    @Column(nullable = false, length = 20)
    private String residenciaNumero;

}
