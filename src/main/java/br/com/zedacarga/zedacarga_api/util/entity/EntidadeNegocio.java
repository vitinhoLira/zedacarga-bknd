package br.com.zedacarga.zedacarga_api.util.entity;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
<<<<<<< HEAD
import jakarta.persistence.Id;
=======
>>>>>>> 0d5817df2b2113da19c9880ab87ebff7dde7c2e9
import jakarta.persistence.MappedSuperclass;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@SuppressWarnings("serial")
@Getter
@Setter
@EqualsAndHashCode(of = { "id" })
@MappedSuperclass
public abstract class EntidadeNegocio implements Serializable {
<<<<<<< HEAD

=======
>>>>>>> 0d5817df2b2113da19c9880ab87ebff7dde7c2e9
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    @JsonIgnore
    @Column
    private Boolean habilitado;

<<<<<<< HEAD
}
=======
}
>>>>>>> 0d5817df2b2113da19c9880ab87ebff7dde7c2e9
