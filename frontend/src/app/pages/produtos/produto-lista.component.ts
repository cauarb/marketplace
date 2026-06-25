import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterLink } from '@angular/router';
import { ProdutoService } from '../../services/produto.service';
import { Produto } from '../../models/produto.model';

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

  importando = false;
  mensagemImportacao = '';

  constructor(
    private produtoService: ProdutoService,
    private cdr: ChangeDetectorRef
  ) {}

  ngOnInit(): void {
    this.carregarProdutos();
  }

  carregarProdutos(): void {
    this.carregando = true;
    this.produtoService.listar().subscribe({
      next: (dados) => {
        this.produtos = dados;
        this.carregando = false;
        this.cdr.detectChanges();
      },
      error: () => {
        this.erro = 'Erro ao carregar produtos';
        this.carregando = false;
        this.cdr.detectChanges();
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

  importar(): void {
  if (!confirm('Deseja importar eletrônicos da Fakestore API?')) return;

  this.importando = true;
  this.mensagemImportacao = '';

  this.produtoService.importarEletronicos().subscribe({
    next: (produtos) => {
      this.importando = false;
      if (produtos.length === 0) {
        this.mensagemImportacao = 'Todos os produtos já foram importados anteriormente.';
      } else {
        this.mensagemImportacao = `${produtos.length} produto(s) importado(s) com sucesso!`;
      }
      this.carregarProdutos();
      this.cdr.detectChanges();
    },
    error: (err: any) => {
      this.importando = false;
      this.mensagemImportacao = err.error?.mensagem || 'Erro ao importar produtos';
      this.cdr.detectChanges();
    }
  });
}
}