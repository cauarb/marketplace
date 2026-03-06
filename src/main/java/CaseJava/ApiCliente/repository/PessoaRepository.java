package CaseJava.ApiCliente.repository;

import CaseJava.ApiCliente.entity.Pessoa;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.Optional;


public interface PessoaRepository extends JpaRepository<Pessoa, Long> {
    Optional<Pessoa> findByCpfCnpj(String cpfCnpj);
}