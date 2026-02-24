package CaseJava.ApiCliente.repository;


import CaseJava.ApiCliente.entity.Cidade;
import CaseJava.ApiCliente.entity.Pessoa;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CidadeRepository extends JpaRepository<Cidade, Long> {
    Optional<Cidade> findById(Long id);
}