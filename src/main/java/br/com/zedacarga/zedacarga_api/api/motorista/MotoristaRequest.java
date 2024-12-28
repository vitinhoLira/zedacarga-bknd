package br.com.zedacarga.zedacarga_api.api.motorista;

import br.com.zedacarga.zedacarga_api.modelo.motorista.Motorista;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MotoristaRequest {

    private Long idConta;

    private String nome;

    private String email;

    private String numeroTelefone;

    private String cpf;

    private String numeroCnh;

    private String foto;

    private String rua;

    private String cidade;

    private String estado;

    private String numero;

    private String bairro;

    private String cep;

    private String complemento;
    

    public Motorista build() {
        
        return Motorista.builder()
                .nome(nome)
                .email(email)
                .numeroTelefone(numeroTelefone)
                .cpf(cpf)
                .foto(foto)
                .rua(rua)
                .cidade(cidade)
                .estado(estado)
                .numero(numero)
                .bairro(bairro)
                .cep(cep)
                .complemento(complemento)
                .build();
    }
}
