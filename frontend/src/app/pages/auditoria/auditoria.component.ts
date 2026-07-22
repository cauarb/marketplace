import { CommonModule } from '@angular/common';
import { Component, OnInit, ChangeDetectorRef, inject } from '@angular/core';
import { AuditoriaEstoque } from '../../models/auditoria.model';
import { AuditoriaService } from '../../services/auditoria.service';

@Component({
  selector: 'app-auditoria',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './auditoria.component.html'
})
export class AuditoriaComponent implements OnInit {

  private auditoriaService = inject(AuditoriaService);
  private cdr = inject(ChangeDetectorRef);

  auditorias: AuditoriaEstoque[] = [];
  carregando = true;
  erro = '';
  produtoExpandido: number | null = null;

  ngOnInit(): void {
    this.carregar();
  }

  carregar(): void {
    this.carregando = true;
    this.auditoriaService.auditarTodos().subscribe({
      next: (dados) => {
        this.auditorias = dados;
        this.carregando = false;
        this.cdr.detectChanges();
      },
      error: () => {
        this.erro = 'Erro ao carregar auditoria';
        this.carregando = false;
        this.cdr.detectChanges();
      }
    });
  }

  toggleExtrato(produtoId: number): void {
    this.produtoExpandido = this.produtoExpandido === produtoId ? null : produtoId;
    this.cdr.detectChanges();
  }
}