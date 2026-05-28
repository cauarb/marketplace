package CaseJava.ApiCliente.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PessoaRequestDTO {

    @NotBlank
    private String nome;

    @NotBlank
    private String cpfCnpj;

    private String endereco;
    private String numero;
    private String bairro;
    private String cep;
    private String telefone;
    private String email;

    @NotNull
    private Long cidadeId;
}
