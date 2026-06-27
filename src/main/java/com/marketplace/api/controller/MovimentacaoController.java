package com.marketplace.api.controller;

import com.marketplace.api.dto.MovimentacaoDTO;
import com.marketplace.api.model.MovimentacaoEstoque;
import com.marketplace.api.service.MovimentacaoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/movimentacoes")
@RequiredArgsConstructor
public class MovimentacaoController {

    private final MovimentacaoService service;

    @PostMapping
    public ResponseEntity<Void> registrar(@RequestBody @Valid MovimentacaoDTO dto) {
        service.registrar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping
    public ResponseEntity<List<MovimentacaoEstoque>> listarTodas() {
        return ResponseEntity.ok(service.listarTodas());
    }

    @GetMapping("/produto/{produtoId}")
    public ResponseEntity<List<MovimentacaoEstoque>> listarPorProduto(
            @PathVariable Long produtoId) {
        return ResponseEntity.ok(service.listarPorProduto(produtoId));
    }
}