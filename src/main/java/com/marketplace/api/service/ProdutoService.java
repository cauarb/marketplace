package com.marketplace.api.service;

import com.marketplace.api.dto.ProdutoDTO;
import com.marketplace.api.model.MovimentacaoEstoque;
import com.marketplace.api.model.Produto;
import com.marketplace.api.repository.MovimentacaoRepository;
import com.marketplace.api.repository.ProdutoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProdutoService {

    private final ProdutoRepository repository;
    private final MovimentacaoRepository movimentacaoRepository;

    public void cadastrar( ProdutoDTO dto) {
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
        produto.setDesconto(dto.getDesconto());

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

            int diferencaEstoque = dto.getEstoque() - produto.getEstoque();

            produto.setNome(dto.getNome());
            produto.setDescricao(dto.getDescricao());
            produto.setPreco(dto.getPreco());
            produto.setEstoque(dto.getEstoque());
            produto.setEstoqueMin(dto.getEstoqueMin());
            produto.setDesconto(dto.getDesconto());

            repository.atualizar(produto);

            if (diferencaEstoque != 0) {
                String tipo = diferencaEstoque > 0 ? "ENTRADA" : "AJUSTE";
                String motivo = dto.getMotivoAlteracao() != null && !dto.getMotivoAlteracao().isBlank()
                        ? dto.getMotivoAlteracao()
                        : "Alteração manual via edição do produto";

                MovimentacaoEstoque mov = new MovimentacaoEstoque();
                mov.setProdutoId(id);
                mov.setTipo(tipo);
                mov.setQuantidade(diferencaEstoque);
                mov.setMotivo(motivo);
                mov.setCriadoEm(java.time.LocalDateTime.now());
                movimentacaoRepository.salvar(mov);
            }
        }

        public void deletar(Long id) {
            buscarPorId(id);
            repository.deletar(id);
        }

    }
