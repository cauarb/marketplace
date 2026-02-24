package CaseJava.ApiCliente.controller;

import CaseJava.ApiCliente.dto.PessoaRequestDTO;
import CaseJava.ApiCliente.dto.PessoaResponseDTO;
import CaseJava.ApiCliente.service.PessoaService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pessoas")
public class PessoaController {

    private final PessoaService service;

    public PessoaController(PessoaService service){
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<PessoaResponseDTO> salvar(
            @RequestBody @Valid PessoaRequestDTO dto) {
        return ResponseEntity.ok(service.salvar(dto));
    }

    @GetMapping
    public ResponseEntity<List<PessoaResponseDTO>> listar() {
        return ResponseEntity.ok(service.listar());
    }

}
