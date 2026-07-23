package com.marketplace.api.controller;

import com.marketplace.api.dto.AuditoriaEstoqueDTO;
import com.marketplace.api.service.AuditoriaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/auditoria")
@RequiredArgsConstructor
public class AuditoriaController {

    private final AuditoriaService service;

    @GetMapping
    public ResponseEntity<List<AuditoriaEstoqueDTO>> auditarTodos() {
        return ResponseEntity.ok(service.auditarTodos());
    }

    @GetMapping("/{produtoId}")
    public ResponseEntity<AuditoriaEstoqueDTO> auditarPorId(
            @PathVariable Long produtoId) {
        return ResponseEntity.ok(service.auditarPorId(produtoId));
    }
}
