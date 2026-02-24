package CaseJava.ApiCliente.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "Pessoa")
public class Pessoa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 150)
    private String nome;

    @Column(name = "cpf_cnpj", nullable = false, length = 14)
    private String cpfCnpj;

    private String endereco;
    private String numero;
    private String bairro;
    private String cep;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cidade_id")
    private  Cidade cidade;

    private String telefone;
    private String email;
}
