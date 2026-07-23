package com.marketplace.api.controller;

import com.marketplace.api.dto.PainelDTO;
import com.marketplace.api.service.PainelService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/painel")
@RequiredArgsConstructor
public class PainelController {

    private final PainelService service;

    @GetMapping
    public ResponseEntity<PainelDTO> gerarPainel() {
        return ResponseEntity.ok(service.gerarPainel());
    }
}
