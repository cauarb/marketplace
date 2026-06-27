package com.marketplace.api.controller;

import com.marketplace.api.model.Produto;
import com.marketplace.api.service.ImportacaoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/importacao")
@RequiredArgsConstructor
public class ImportacaoController {

    private final ImportacaoService service;

    @PostMapping("/eletronicos")
    public ResponseEntity<List<Produto>> importarEletronicos() {
        List<Produto> importados = service.importarEletronicos();
        return ResponseEntity.ok(importados);
    }
}