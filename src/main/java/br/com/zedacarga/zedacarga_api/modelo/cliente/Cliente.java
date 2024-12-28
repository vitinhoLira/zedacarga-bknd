package br.com.zedacarga.zedacarga_api.modelo.cliente;

import java.time.LocalDate;
import java.util.List;

import org.hibernate.annotations.SQLRestriction;

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
@Table(name = "Cliente")
@SQLRestriction("habilitado = true")
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class Cliente extends EntidadeAuditavel {

    @OneToMany(mappedBy = "cliente", orphanRemoval = true, fetch = FetchType.LAZY)
    private List<CartaoCliente> cartoes;

    @OneToMany(mappedBy = "cliente") 
    private List<Viagem> viagens;

    @Column
    private String nome;
    
    @Column
    private LocalDate dataNascimento;

    @Column
    private String telefone;

    @Column
    private String email;

    @Column
    private String cpf;
    
    @Column
    private String foto;

    @Column
    private String rua;

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
   
