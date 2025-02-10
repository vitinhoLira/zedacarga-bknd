package br.com.zedacarga.zedacarga_api.modelo.motorista;

import br.com.zedacarga.zedacarga_api.modelo.viagem.Viagem;
import br.com.zedacarga.zedacarga_api.util.entity.EntidadeAuditavel;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

import org.hibernate.annotations.SQLRestriction;

@Entity
@Table(name = "motorista")
@SQLRestriction("habilitado = true")
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Motorista extends EntidadeAuditavel {

    @OneToMany(mappedBy = "motorista", orphanRemoval = true, fetch = FetchType.LAZY)
    private List<ContaBancariaMotorista> contas;

    @OneToMany(mappedBy = "motorista", fetch = FetchType.LAZY)
    private List<Viagem> viagens;

    @OneToOne
    private Veiculo veiculo;

    @Column(nullable = false, length = 100)
    private String nome;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false, length = 10)
    private LocalDate dataNascimento;

    @Column(nullable = false, length = 20)
    private String numeroTelefone;

    @Column(nullable = false, unique = true)
    private String cpf;

    @Column(nullable = false, unique = true)
    private String numeroCnh;

    @Column(nullable = false)
    private String foto;

    @Column(nullable = false, length = 100)
    private String endereco;

    @Column(nullable = false, length = 30)
    private String cidade;

    @Column(nullable = false, length = 30)
    private String estado;

    @Column(nullable = false, length = 20)
    private String numero;

    @Column(nullable = false, length = 30)
    private String bairro;

    @Column(nullable = false, length = 20)
    private String cep;

    @Column(nullable = true, length = 60)
    private String complemento;

}
