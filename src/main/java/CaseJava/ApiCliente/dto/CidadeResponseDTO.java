package CaseJava.ApiCliente.dto;

import lombok.Data;

@Data
public class CidadeResponseDTO {

    private Long id;
    private String nome;
    private String uf;

    public CidadeResponseDTO(Long id, String nome, String uf) {
        this.id = id;
        this.nome = nome;
        this.uf = uf;
    }
}
