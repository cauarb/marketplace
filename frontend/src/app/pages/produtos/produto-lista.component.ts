import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterLink } from '@angular/router';
import { ProdutoService } from '../../services/produto.service';
import { Produto } from '../../models/produto.model';

@Component({
  selector: 'app-produto-lista',
  standalone: true,
  imports: [CommonModule, RouterLink],
  template: `
    <div class="container mt-4">

      <div class="d-flex justify-content-between align-items-center mb-4">
        <h2>Produtos</h2>
        <a routerLink="/produtos/novo" class="btn btn-primary">
          + Novo Produto
        </a>
      </div>

      <div *ngIf="carregando" class="text-center py-5 text-muted">
        Carregando...
      </div>

      <div *ngIf="erro" class="alert alert-danger">
        {{ erro }}
      </div>

      <div *ngIf="!carregando && !erro">
        <table class="table table-bordered table-hover">
          <thead class="table-light">
            <tr>
              <th>Nome</th>
              <th>Preço</th>
              <th>Estoque</th>
              <th>Mínimo</th>
              <th>Ações</th>
            </tr>
          </thead>
          <tbody>
            <tr *ngFor="let produto of produtos">
              <td>{{ produto.nome }}</td>
              <td>{{ produto.preco | currency:'BRL' }}</td>
              <td>
                <span [class]="produto.estoque <= produto.estoqueMin
                  ? 'badge bg-danger'
                  : 'badge bg-success'">
                  {{ produto.estoque }}
                </span>
              </td>
              <td>{{ produto.estoqueMin }}</td>
              <td>
                <a [routerLink]="['/produtos/editar', produto.id]"
                   class="btn btn-sm btn-outline-primary me-2">
                  Editar
                </a>
                <button (click)="deletar(produto.id)"
                        class="btn btn-sm btn-outline-danger">
                  Excluir
                </button>
              </td>
            </tr>
            <tr *ngIf="produtos.length === 0">
              <td colspan="5" class="text-center text-muted py-4">
                Nenhum produto cadastrado.
              </td>
            </tr>
          </tbody>
        </table>
      </div>

    </div>
  `
})
export class ProdutoListaComponent implements OnInit {

  produtos: Produto[] = [];
  carregando = true;
  erro = '';

  constructor(private produtoService: ProdutoService) {}

  ngOnInit(): void {
    this.carregarProdutos();
  }

  carregarProdutos(): void {
    this.carregando = true;
    this.produtoService.listar().subscribe({
      next: (dados) => {
        this.produtos = dados;
        this.carregando = false;
      },
      error: () => {
        this.erro = 'Erro ao carregar produtos';
        this.carregando = false;
      }
    });
  }

  deletar(id: number): void {
    if (!confirm('Tem certeza que deseja excluir?')) return;
    this.produtoService.deletar(id).subscribe({
      next: () => this.carregarProdutos(),
      error: () => alert('Erro ao deletar produto')
    });
  }
}