package com.marketplace.api.service;

import com.marketplace.api.dto.ProdutoDTO;
import com.marketplace.api.model.Produto;
import com.marketplace.api.repository.ProdutoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProdutoService {

    private final ProdutoRepository repository;

    public void cadastrar(ProdutoDTO dto) {
        if (dto.getEstoqueMin() > dto.getEstoque()) {
            throw new IllegalArgumentException(
                    "Estoque mínimo não pode ser maior que o estoque atual"
            );
        }
            Produto produto = new Produto();
            produto.setNome(dto.getNome());
            produto.setDescricao(dto.getDescricao());
            produto.setPreco(dto.getPreco());
            produto.setEstoque(dto.getEstoque());
            produto.setEstoqueMin(dto.getEstoqueMin());

            repository.salvar(produto);
        }

        public List<Produto> listarTodos() {
            return repository.listarTodos();
        }

        public Produto buscarPorId(Long id) {
            return repository.buscarPorId(id)
                    .orElseThrow(() -> new RuntimeException("Produto não encontrado"));
        }

        public void atualizar(Long id, ProdutoDTO dto) {
            Produto produto =  buscarPorId(id);

            if (dto.getEstoqueMin() > dto.getEstoque()) {
                throw new IllegalArgumentException(
                        "Estoque minimo não pode ser maior que estoque atual"
                );
            }

            produto.setNome(dto.getNome());
            produto.setDescricao(dto.getDescricao());
            produto.setPreco(dto.getPreco());
            produto.setEstoque(dto.getEstoque());
            produto.setEstoqueMin(dto.getEstoqueMin());

            repository.atualizar(produto);
        }

        public void deletar(Long id) {
            buscarPorId(id);
            repository.deletar(id);
        }

    }
