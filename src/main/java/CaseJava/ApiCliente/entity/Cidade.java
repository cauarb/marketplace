package CaseJava.ApiCliente.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "cidade")
public class Cidade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long id;

    @Column(nullable = false, length = 100)
    private String nome;

    @Column(nullable = false, length =  2)
    private String uf;
}
