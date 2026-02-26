package CaseJava.ApiCliente.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "Pessoa")
public class Pessoa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false, unique = true)
    private String cpfCnpj;

    private String endereco;
    private String numero;
    private String bairro;
    private String cep;
    private String telefone;
    private String email;

    @ManyToOne
    @JoinColumn(name = "cidade_id", nullable = false)
    private Cidade cidade;
}
