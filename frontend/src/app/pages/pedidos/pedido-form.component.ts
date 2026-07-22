import { CommonModule } from "@angular/common";
import { Component, OnInit, ChangeDetectorRef } from "@angular/core";
import { FormsModule } from "@angular/forms";
import { Produto } from "../../models/produto.model";
import { ItemPedidoDTO, PedidoDTO } from "../../models/pedido.model";
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
        private router: Router,
        private cdr: ChangeDetectorRef
    ){}

    ngOnInit(): void {
        this.produtoService.listar().subscribe({
            next: (dados) => {
                this.produtos = dados;
                this.cdr.detectChanges();
            },
            error: () => {
                this.erro = 'Erro ao carregar produtos';
                this.cdr.detectChanges();
            }
        });
    }

    adicionarItem(): void {
        this.erro = '';

        if (!this.produtoSelecionado) {
            this.erro = 'Selecione um produto';
            this.cdr.detectChanges();
            return;
        }

        if (this.quantidadeSelecionada < 1) {
            this.erro = 'A quantidade deve ser maior que zero';
            this.cdr.detectChanges();
            return;
        }

        const jaAdicionado = this.itens.find(
            i => i.produtoId === this.produtoSelecionado!.id
        );

        if (jaAdicionado) {
            this.erro = 'Produto já adicionado ao pedido';
            this.cdr.detectChanges();
            return;
        }

        this.itens.push({
            produtoId: this.produtoSelecionado.id,
            quantidade: this.quantidadeSelecionada
        });

        this.produtoSelecionado = null;
        this.quantidadeSelecionada = 1;
        this.cdr.detectChanges();
    }

    removerItem(index: number): void {
        this.itens.splice(index, 1);
        this.erro = '';
        this.cdr.detectChanges();
    }

    getNomeProduto(id: number): string {
        const produto = this.produtos.find(p => p.id === id);
        return produto ? produto.nome : '';
    }

    salvar(): void {
        this.erro = '';
        const dto: PedidoDTO = { itens: this.itens };
        this.pedidoService.criar(dto).subscribe({
            next: () => {
                this.sucesso = true;
                this.cdr.detectChanges();
                setTimeout(() => this.router.navigate(['/pedidos']), 1500);
            },
            error: (err: any) => {
                this.erro = err.error?.mensagem || 'Erro ao criar pedido';
                this.cdr.detectChanges();
            }
        });
    }

    voltar(): void {
        this.router.navigate(['/pedidos']);
    }
}