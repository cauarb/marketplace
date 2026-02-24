package CaseJava.ApiCliente.service;


import CaseJava.ApiCliente.dto.PessoaRequestDTO;
import CaseJava.ApiCliente.dto.PessoaResponseDTO;
import CaseJava.ApiCliente.entity.Cidade;
import CaseJava.ApiCliente.entity.Pessoa;
import CaseJava.ApiCliente.repository.CidadeRepository;
import CaseJava.ApiCliente.repository.PessoaRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

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
}
