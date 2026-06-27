package CaseJava.ApiCliente.controller;


import CaseJava.ApiCliente.dto.CidadeRequestDTO;
import CaseJava.ApiCliente.dto.CidadeResponseDTO;
import CaseJava.ApiCliente.service.CidadeService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cidades")
public class CidadeController {

    private final CidadeService service;

    public CidadeController(CidadeService service){
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<CidadeResponseDTO> salvar(
            @RequestBody @Valid CidadeRequestDTO dto) {

        return ResponseEntity.ok(service.salvar(dto));
    }

    @GetMapping
    public ResponseEntity<List<CidadeResponseDTO>> listar() {
        return ResponseEntity.ok(service.listar());
    }

    @PutMapping("/{id}")
    public ResponseEntity<CidadeResponseDTO> atualizar (
            @PathVariable Long id,
            @RequestBody @Valid CidadeRequestDTO dto){
        return ResponseEntity.ok(service.atualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id){
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
