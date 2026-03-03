package CaseJava.ApiCliente.service;


import CaseJava.ApiCliente.dto.PessoaRequestDTO;
import CaseJava.ApiCliente.dto.PessoaResponseDTO;
import CaseJava.ApiCliente.entity.Cidade;
import CaseJava.ApiCliente.entity.Pessoa;
import CaseJava.ApiCliente.repository.CidadeRepository;
import CaseJava.ApiCliente.repository.PessoaRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Service
public class PessoaService {

    private final PessoaRepository pessoaRepository;
    private final CidadeRepository cidadeRepository;

    public PessoaService (PessoaRepository pessoaRepository, CidadeRepository cidadeRepository){
        this.pessoaRepository = pessoaRepository;
        this.cidadeRepository = cidadeRepository;
    }


    @Transactional
    public PessoaResponseDTO salvar (PessoaRequestDTO dto){

        Cidade cidade = cidadeRepository.findById(dto.getCidadeId())
                .orElseThrow(() -> new RuntimeException("Cidade não encontrada"));

        Pessoa pessoa = new Pessoa();
        pessoa.setNome(dto.getNome());
        pessoa.setCidade(cidade);

        Pessoa salva = pessoaRepository.save(pessoa);

        return new PessoaResponseDTO(
                salva.getId(),
                salva.getNome(),
                salva.getCidade().getNome(),
                salva.getCidade().getUf()
        );
    }

    public List<PessoaResponseDTO> listar(){
        return pessoaRepository.findAll()
                .stream()
                .map(p -> new PessoaResponseDTO(
                        p.getId(),
                        p.getNome(),
                        p.getCidade().getNome(),
                        p.getCidade().getUf()
                ))
                .toList();
    }

    @Transactional
    public PessoaResponseDTO atualizar (Long id, PessoaRequestDTO dto){

        Pessoa pessoa = pessoaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pessoa não encontrada"));

        Cidade cidade =  cidadeRepository.findById(dto.getCidadeId())
                .orElseThrow(() -> new RuntimeException("Cidade não encontrada"));


        pessoa.setNome(dto.getNome());
        pessoa.setCpfCnpj(dto.getCpfCnpj());
        pessoa.setEndereco(dto.getEndereco());
        pessoa.setNumero(dto.getNumero());
        pessoa.setBairro(dto.getBairro());
        pessoa.setCep(dto.getCep());
        pessoa.setTelefone(dto.getTelefone());
        pessoa.setEmail(dto.getEmail());
        pessoa.setCidade(cidade);

        Pessoa atualizada = pessoaRepository.save(pessoa);

        return converterParaDTO(atualizada);
    }

    @Transactional
    public void deletar(Long id){
        if (!pessoaRepository.existsById(id)){
            throw new RuntimeException("Pessoa não encontrada");
        }
        pessoaRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public PessoaResponseDTO buscarPorCpfCnpj(String cpfCnpj) {

        Pessoa pessoa = pessoaRepository.findByCpfCnpj(cpfCnpj)
                .orElseThrow(() -> new RuntimeException("Pessoa não encontrada"));

        return converterParaDTO(pessoa);
    }

}
