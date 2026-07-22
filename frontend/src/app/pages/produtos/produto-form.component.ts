import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router, ActivatedRoute } from '@angular/router';
import { ProdutoService } from '../../services/produto.service';
import { ProdutoDTO } from '../../models/produto.model';

@Component({
  selector: 'app-produto-form',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './produto-form.component.html',
  styleUrl: './produto-form.component.css'
})
export class ProdutoFormComponent implements OnInit {

  form: ProdutoDTO = {
  nome: '',
  descricao: '',
  preco: 0,
  estoque: 0,
  estoqueMin: 0,
  desconto: 0,
  motivoAlteracao: ''
};

  estoqueOriginal = 0;

  editando = false;
  produtoId: number | null = null;
  erro = '';

  constructor(
    private produtoService: ProdutoService,
    private router: Router,
    private route: ActivatedRoute,
    public cdr: ChangeDetectorRef
  ) {}

  ngOnInit(): void {
    const id = this.route.snapshot.paramMap.get('id');
    if (id) {
      this.editando = true;
      this.produtoId = Number(id);
      this.carregarProduto(this.produtoId);
    }
  }

  carregarProduto(id: number): void {
    this.produtoService.buscarPorId(id).subscribe({
      next: (produto) => {
        this.estoqueOriginal = produto.estoque;
        this.form = {
          nome: produto.nome,
          descricao: produto.descricao,
          preco: produto.preco,
          estoque: produto.estoque,
          estoqueMin: produto.estoqueMin,
          desconto: produto.desconto,
          motivoAlteracao: ''
        };
        this.cdr.detectChanges();
      },
      error: () => {
        this.erro = 'Erro ao carregar produto';
        this.cdr.detectChanges();
      }
    });
  }

  salvar(): void {
    this.erro = '';
    if (!this.form.nome) {
      this.erro = 'O nome é obrigatório';
      return;
    }
    if (this.editando && this.produtoId) {
      this.produtoService.atualizar(this.produtoId, this.form).subscribe({
        next: () => this.router.navigate(['/produtos']),
        error: (err: any) => this.erro = err.error?.mensagem || 'Erro ao atualizar'
      });
    } else {
      this.produtoService.cadastrar(this.form).subscribe({
        next: () => this.router.navigate(['/produtos']),
        error: (err: any) => this.erro = err.error?.mensagem || 'Erro ao cadastrar'
      });
    }
  }

  voltar(): void {
    this.router.navigate(['/produtos']);
  }
}