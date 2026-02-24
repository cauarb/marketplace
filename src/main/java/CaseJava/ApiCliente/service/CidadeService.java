package CaseJava.ApiCliente.service;

import CaseJava.ApiCliente.dto.CidadeRequestDTO;
import CaseJava.ApiCliente.dto.CidadeResponseDTO;
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
}
