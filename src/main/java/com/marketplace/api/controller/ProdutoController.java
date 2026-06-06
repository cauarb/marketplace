package com.marketplace.api.controller;

import com.marketplace.api.dto.ProdutoDTO;
import com.marketplace.api.model.Produto;
import com.marketplace.api.service.ProdutoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/produtos")
@RequiredArgsConstructor
public class ProdutoController {

    private final ProdutoService service;

    @PostMapping
    public ResponseEntity<Void> cadastrar(@RequestBody @Valid ProdutoDTO dto) {
        service.cadastrar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

   @GetMapping
   public ResponseEntity<List<Produto>> listar() {
        return ResponseEntity.ok(service.listarTodos());
   }

   @GetMapping("/{id}")
   public ResponseEntity<Produto> buscarPorId(@PathVariable Long id){
        return ResponseEntity.ok(service.buscarPorId(id));
   }

   @PutMapping("/{id}")
   public ResponseEntity<Produto> atualizar(@PathVariable Long id,
                                            @RequestBody @Valid ProdutoDTO dto) {
        service.atualizar(id, dto);
        return ResponseEntity.ok().build();
   }

   @DeleteMapping("/{id}")
   public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
   }

}
