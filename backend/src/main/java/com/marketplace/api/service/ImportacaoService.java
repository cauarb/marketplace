package com.marketplace.api.service;

import com.marketplace.api.dto.FakestoreDTO;
import com.marketplace.api.model.Produto;
import com.marketplace.api.repository.ProdutoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ImportacaoService {

    private final ProdutoRepository produtoRepository;

    private final RestTemplate restTemplate = new RestTemplate();

    private static final String URL_FAKESTORE = "https://fakestoreapi.com/products";

    public List<Produto> importarEletronicos() {


        FakestoreDTO[] resposta = restTemplate.getForObject(
                URL_FAKESTORE,
                FakestoreDTO[].class
        );

        if (resposta == null) {
            throw new RuntimeException("Erro ao buscar produtos da API externa");
        }


        List<FakestoreDTO> eletronicos = Arrays.stream(resposta)
                .filter(p -> "electronics".equals(p.getCategory()))
                .toList();


        List<Produto> importados = new ArrayList<>();

        for (FakestoreDTO externo : eletronicos) {


            if (produtoRepository.existePorNome(externo.getTitle())) {
                continue;
            }

            Produto produto = new Produto();
            produto.setNome(externo.getTitle());
            produto.setDescricao(externo.getDescription());
            produto.setPreco(externo.getPrice());
            produto.setEstoque(10);
            produto.setEstoqueMin(2);

            produtoRepository.salvar(produto);
            importados.add(produto);
        }

        return importados;
    }
}