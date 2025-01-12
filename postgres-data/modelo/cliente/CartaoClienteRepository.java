package br.com.zedacarga.zedacarga_api.modelo.cliente;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CartaoClienteRepository extends JpaRepository<CartaoCliente, Long> {
    
}
