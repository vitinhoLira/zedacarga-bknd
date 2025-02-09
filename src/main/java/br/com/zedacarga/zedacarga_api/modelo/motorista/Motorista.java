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

    @Column
    private String nome;

    @Column
    private String email;

    @Column
    private LocalDate dataNascimento;

    @Column
    private String numeroTelefone;

    @Column
    private String cpf;

    @Column
    private String numeroCnh;

    @Column
    private String foto;

    @Column
    private String endereco;

    @Column
    private String cidade;

    @Column
    private String estado;

    @Column
    private String numero;

    @Column
    private String bairro;

    @Column
    private String cep;

    @Column
    private String complemento;

}
