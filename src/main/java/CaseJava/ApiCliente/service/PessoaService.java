package CaseJava.ApiCliente.service;

import CaseJava.ApiCliente.entity.Pessoa;
import CaseJava.ApiCliente.repository.PessoaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public class PessoaService {

    private final PessoaRepository pessoaRepository;

    public PessoaService(PessoaRepository pessoaRepository) {
        this.pessoaRepository = pessoaRepository;
    }

    @Transactional
    public Pessoa salvar(Pessoa pessoa) {
        return pessoaRepository.save(pessoa);
    }

    @Transactional(readOnly = true)
    public List<Pessoa> listarTodos() {
        return pessoaRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<Pessoa> buscarPorId(Long id) {
        return pessoaRepository.findById(id);
    }

    @Transactional
    public void deletar(Long id) {
        pessoaRepository.deleteById(id);
    }
}
