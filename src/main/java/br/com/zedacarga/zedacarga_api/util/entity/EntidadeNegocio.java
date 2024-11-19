package br.com.zedacarga.zedacarga_api.util.entity;

import java.io.Serializable;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode(of = { "id" })
public abstract class EntidadeNegocio implements Serializable {
    private Long id;
    private Boolean habilitado;
   
   }
