package CaseJava.ApiCliente.dto;

public class PessoaResponseDTO {

    private Long id;
    private String nome;
    private String cidadeNome;
    private String cidadeuf;

    public PessoaResponseDTO( Long id, String nome, String cidadeNome, String cidadeuf){
        this.id = id;
        this.nome =  nome;
        this.cidadeNome = cidadeNome;
        this.cidadeuf = cidadeuf;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCidadeuf() {
        return cidadeuf;
    }

    public void setCidadeuf(String cidadeuf) {
        this.cidadeuf = cidadeuf;
    }

    public String getCidadeNome() {
        return cidadeNome;
    }

    public void setCidadeNome(String cidadeNome) {
        this.cidadeNome = cidadeNome;
    }
}
