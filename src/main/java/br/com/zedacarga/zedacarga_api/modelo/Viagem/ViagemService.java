package br.com.zedacarga.zedacarga_api.modelo.viagem;

import jakarta.transaction.Transactional;

import java.time.LocalDate;
import java.util.List;

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

    public List<Viagem> listarTodos() {

        return repository.findAll();
    }

    public Viagem obterPorID(Long id) {
        return repository.findById(id).get();
    }

    @Transactional
    public void update(Long id, Viagem viagemAlterado) {
        Viagem viagem = repository.findById(id).get();
        viagem.setOrigem(viagemAlterado.getOrigem());
        viagem.setDestino(viagemAlterado.getDestino());
        viagem.setValor(viagemAlterado.getValor());
        viagem.setStatus(viagemAlterado.getStatus());

        viagem.setVersao(viagem.getVersao() + 1);
        repository.save(viagem);
    }

}