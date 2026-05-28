package CaseJava.ApiCliente.repository;


import CaseJava.ApiCliente.entity.Cidade;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CidadeRepository extends JpaRepository<Cidade, Long> {
}