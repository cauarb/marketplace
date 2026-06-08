import { CommonModule } from "@angular/common";
import { Component, OnInit } from "@angular/core";
import { FormsModule } from "@angular/forms";
import { Produto } from "../../models/produto.model";
import { ItemPedido, ItemPedidoDTO, PedidoDTO } from "../../models/pedido.model";
import { PedidoService } from "../../services/pedido.service";
import { ProdutoService } from "../../services/produto.service";
import { Router } from "@angular/router";


@Component({
  selector: 'app-pedido-form',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './pedido-form.component.html',
  styleUrl: './pedido-form.component.css'
})
export class PedidoFormComponent implements OnInit {


    produtos: Produto[] = [];
    itens: ItemPedidoDTO[] = [];
    produtoSelecionado: Produto | null = null;
    quantidadeSelecionada = 1;
    erro = '';
    sucesso = false;

    constructor(
        private pedidoService: PedidoService,
        private produtoService: ProdutoService,
        private router: Router
    ){}

    ngOnInit(): void {
        this.produtoService.listar().subscribe({
            next: (dados) => this.produtos = dados,
            error: () => this.erro = 'Errp ao carregar produtos'
        });
    }

    adicionarItem(): void {
        this.erro = '';

        if(!this.produtoSelecionado) {
            this.erro = 'Selecione um produto';
            return;
        }

        if(this.quantidadeSelecionada < 1){
            this.erro = 'A quantidade deve ser maior que zero';
            return;
        }

        const jaAdicionado = this.itens.find(
            i => i.produtoId === this.produtoSelecionado!.id
        );

        if(jaAdicionado) {
            this.erro = 'Produto já adicionado ao pedido';
            return;
        }

        this.itens.push({
            produtoId: this.produtoSelecionado.id,
            quantidade: this.quantidadeSelecionada
        });

        this.produtoSelecionado = null;
        this.quantidadeSelecionada = 1;
    }

    removerItem(index : number): void {
        this.itens.splice(index, 1);
    }

    getNomeProduto(id: number): string {
        const produto = this.produtos.find(p => p.id === id);
        return produto ? produto.nome : '';
    }

    salvar(): void {
        this.erro = '';
        const dto: PedidoDTO = { itens: this.itens};
        this.pedidoService.criar(dto).subscribe({
            next: () => {
                this.sucesso =  true;
                setTimeout(() => this.router.navigate(['/pedidos']), 1500);
            },
            error: (err: any) => {
                this.erro = err.erro?.mensagem || 'Erro ao criar pedido';
            }
        });
    }

    voltar(): void {
        this.router.navigate(['/pedidos']);
    }
}