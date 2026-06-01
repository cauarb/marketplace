import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterLink } from '@angular/router';
import { ProdutoService } from '../../../services/produto.service';
import { Produto } from '../../produto.model';

@Component({
  selector: 'app-produto-lista',
  standalone: true,
  imports: [CommonModule, RouterLink],
  templateUrl: './produto-lista.component.html',
  styleUrl: './produto-lista.component.css'
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