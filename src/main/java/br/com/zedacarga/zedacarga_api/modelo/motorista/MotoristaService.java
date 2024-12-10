package br.com.zedacarga.zedacarga_api.modelo.motorista;

import jakarta.transaction.Transactional;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MotoristaService {

    @Autowired
    private MotoristaRepository repository;

    @Transactional
    public Motorista save(Motorista motorista) {
        motorista.setHabilitado(Boolean.TRUE);
        motorista.setVersao(1L);
        motorista.setDataCriacao(LocalDate.now());
        return repository.save(motorista);
    }
}
