package br.com.zedacarga.zedacarga_api.modelo.motorista;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

@Service
public class MotoristaService {

    @Autowired
    private MotoristaRepository repository;

    @Autowired
    private ContaBancariaMotoristaRepository contaMotoristaRepository;

    private VeiculoRepository veiculoRepository;

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

    @Transactional
    public void delete(Long id) {
        Motorista motorista = repository.findById(id).get();
        motorista.setHabilitado(Boolean.FALSE);
        motorista.setVersao(motorista.getVersao() + 1);
        repository.save(motorista);
    }

    // ContaBancariaMotorista

    @Transactional
    public ContaBancariaMotorista adicionarContaMotorista(Long motoristaId, ContaBancariaMotorista conta) {

        Motorista motorista = this.obterPorID(motoristaId);

        // Primeiro salva o EnderecoCliente:

        conta.setMotorista(motorista);
        conta.setHabilitado(Boolean.TRUE);
        contaMotoristaRepository.save(conta);

        List<ContaBancariaMotorista> listaContasMotorista = motorista.getContas();

        if (listaContasMotorista == null) {
            listaContasMotorista = new ArrayList<ContaBancariaMotorista>();
        }

        listaContasMotorista.add(conta);
        motorista.setContas(listaContasMotorista);
        repository.save(motorista);

        return conta;
    }

    @Transactional
    public ContaBancariaMotorista atualizarContaMotorista(Long id, ContaBancariaMotorista contaAlterada) {

        ContaBancariaMotorista conta = contaMotoristaRepository.findById(id).get();
        conta.setNumeroConta(contaAlterada.getNumeroConta());
        conta.setAgencia(contaAlterada.getAgencia());

        return contaMotoristaRepository.save(conta);
    }

    @Transactional
    public void removerContaMotorista(Long idConta) {

        ContaBancariaMotorista conta = contaMotoristaRepository.findById(idConta).get();
        conta.setHabilitado(Boolean.FALSE);
        contaMotoristaRepository.save(conta);

        Motorista motorista = this.obterPorID(conta.getMotorista().getId());
        motorista.getContas().remove(conta);
        repository.save(motorista);
    }

    //Veiculo

    @Transactional
    public Veiculo adicionarVeiculoMotorista(Long motoristaId, Veiculo veiculo) {
        Motorista motorista = this.obterPorID(motoristaId);
        if (motorista.getVeiculo() != null) {
            throw new RuntimeException("Motorista já possui um veículo cadastrado.");
        }
        veiculo.setMotorista(motorista);
        return veiculoRepository.save(veiculo);
    }

    @Transactional

    public Veiculo atualizarVeiculoMotorista(Long idVeiculo, Veiculo veiculoAlterado) {
        Veiculo veiculo = veiculoRepository.findById(idVeiculo).get();
        veiculo.setPlaca(veiculoAlterado.getPlaca());
        veiculo.setModelo(veiculoAlterado.getModelo());
        veiculo.setAno(veiculoAlterado.getAno());
        return veiculoRepository.save(veiculo);
    }

    @Transactional

    public void removerVeiculoMotorista(Long motoristaId) {

        Motorista motorista = this.obterPorID(motoristaId);
        Veiculo veiculo = motorista.getVeiculo();

        if (veiculo != null) {
            veiculoRepository.delete(veiculo);
            motorista.setVeiculo(null);
            repository.save(motorista);

        }
    }

}
