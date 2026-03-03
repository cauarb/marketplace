package CaseJava.ApiCliente.service;

import CaseJava.ApiCliente.dto.CidadeRequestDTO;
import CaseJava.ApiCliente.dto.CidadeResponseDTO;
import CaseJava.ApiCliente.dto.PessoaRequestDTO;
import CaseJava.ApiCliente.dto.PessoaResponseDTO;
import CaseJava.ApiCliente.entity.Cidade;
import CaseJava.ApiCliente.repository.CidadeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
public class CidadeService {

    private final CidadeRepository repository;

    public CidadeService(CidadeRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public CidadeResponseDTO salvar(CidadeRequestDTO dto) {

        Cidade cidade = new Cidade();
        cidade.setNome(dto.getNome());
        cidade.setUf(dto.getUf());

        Cidade salva = repository.save(cidade);

        return new CidadeResponseDTO(
                salva.getId(),
                salva.getNome(),
                salva.getUf()
        );
    }

    @Transactional(readOnly = true)
    public List<CidadeResponseDTO> listar() {

        return repository.findAll()
                .stream()
                .map(c -> new CidadeResponseDTO(
                        c.getId(),
                        c.getNome(),
                        c.getUf()
                ))
                .toList();
    }

    @Transactional
    public CidadeResponseDTO atualizar(Long id, CidadeRequestDTO dto ){
        Cidade cidade = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cidade não encontrada"));

        cidade.setNome(dto.getNome());
        cidade.setUf(dto.getUf());

        Cidade atualizada = repository.save(cidade);

        return new CidadeResponseDTO(
                atualizada.getId(),
                atualizada.getNome(),
                atualizada.getUf()
        );
    }

    @Transactional
    public void deletar(Long id){
        if (!repository.existsById(id)) {
            throw new RuntimeException("Cidade não encontrada");
        }
        repository.deleteById(id);
    }
}
