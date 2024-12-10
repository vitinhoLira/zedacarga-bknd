package br.com.zedacarga.zedacarga_api.modelo.motorista;

import jakarta.transaction.Transactional;

import java.time.LocalDate;
import java.util.List;

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

    public List<Motorista> listarTodos() {

        return repository.findAll();
    }

    public Motorista obterPorID(Long id) {
        return repository.findById(id).get();
    }

    @Transactional
    public void update(Long id, Motorista motoristaAlterado) {
        Motorista motorista = repository.findById(id).get();
        motorista.setNome(motoristaAlterado.getNome());
        motorista.setEmail(motoristaAlterado.getEmail());
        motorista.setCpf(motoristaAlterado.getCpf());
        motorista.setNumeroTelefone(motoristaAlterado.getNumeroTelefone());
        motorista.setCpf(motoristaAlterado.getCpf());
        motorista.setNumeroCnh(motoristaAlterado.getNumeroCnh());

        motorista.setVersao(motorista.getVersao() + 1);
        repository.save(motorista);
    }

}
