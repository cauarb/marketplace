import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router, ActivatedRoute } from '@angular/router';
import { ProdutoService } from '../../services/produto.service';
import { ProdutoDTO } from '../../models/produto.model';

@Component({
  selector: 'app-produto-form',
  standalone: true,
  imports: [CommonModule, FormsModule],
  template: `
    <div class="container mt-4">
      <div class="row justify-content-center">
        <div class="col-md-6">

          <div class="card shadow-sm">
            <div class="card-body p-4">

              <h4 class="card-title mb-4">
                {{ editando ? 'Editar Produto' : 'Novo Produto' }}
              </h4>

              <div class="mb-3">
                <label class="form-label">Nome</label>
                <input [(ngModel)]="form.nome"
                       type="text"
                       class="form-control"/>
              </div>

              <div class="mb-3">
                <label class="form-label">Descrição</label>
                <textarea [(ngModel)]="form.descricao"
                          rows="3"
                          class="form-control"></textarea>
              </div>

              <div class="mb-3">
                <label class="form-label">Preço</label>
                <input [(ngModel)]="form.preco"
                       type="number"
                       step="0.01"
                       class="form-control"/>
              </div>

              <div class="mb-3">
                <label class="form-label">Estoque</label>
                <input [(ngModel)]="form.estoque"
                       type="number"
                       class="form-control"/>
              </div>

              <div class="mb-3">
                <label class="form-label">Estoque Mínimo</label>
                <input [(ngModel)]="form.estoqueMin"
                       type="number"
                       class="form-control"/>
              </div>

              <div *ngIf="erro" class="alert alert-danger">
                {{ erro }}
              </div>

              <div class="d-flex gap-2">
                <button (click)="salvar()"
                        class="btn btn-primary flex-fill">
                  {{ editando ? 'Salvar alterações' : 'Cadastrar' }}
                </button>
                <button (click)="voltar()"
                        class="btn btn-secondary flex-fill">
                  Cancelar
                </button>
              </div>

            </div>
          </div>

        </div>
      </div>
    </div>
  `
})
export class ProdutoFormComponent implements OnInit {

  form: ProdutoDTO = {
    nome: '',
    descricao: '',
    preco: 0,
    estoque: 0,
    estoqueMin: 0
  };

  editando = false;
  produtoId: number | null = null;
  erro = '';

  constructor(
    private produtoService: ProdutoService,
    private router: Router,
    private route: ActivatedRoute
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
        this.form = {
          nome: produto.nome,
          descricao: produto.descricao,
          preco: produto.preco,
          estoque: produto.estoque,
          estoqueMin: produto.estoqueMin
        };
      },
      error: () => {
        this.erro = 'Erro ao carregar produto';
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
        error: (err) => this.erro = err.error?.mensagem || 'Erro ao atualizar'
      });
    } else {
      this.produtoService.cadastrar(this.form).subscribe({
        next: () => this.router.navigate(['/produtos']),
        error: (err) => this.erro = err.error?.mensagem || 'Erro ao cadastrar'
      });
    }
  }

  voltar(): void {
    this.router.navigate(['/produtos']);
  }
}