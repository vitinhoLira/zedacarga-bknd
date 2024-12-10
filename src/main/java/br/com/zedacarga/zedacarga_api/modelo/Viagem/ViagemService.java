package br.com.zedacarga.zedacarga_api.modelo.viagem;

import jakarta.transaction.Transactional;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ViagemService {
    @Autowired
    private ViagemRepository repository;

    @Transactional
    public Viagem save(Viagem viagem) {
        viagem.setHabilitado(Boolean.TRUE);
        viagem.setVersao(1L);
        viagem.setDataCriacao(LocalDate.now());
        return repository.save(viagem);
    }
}