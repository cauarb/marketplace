package CaseJava.ApiCliente.repository;

import CaseJava.ApiCliente.entity.Pessoa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


public interface PessoaRepository extends JpaRepository<Pessoa, Long> {
    Optional<Pessoa> findByCpfCnpj(String cpfCnpj);
}