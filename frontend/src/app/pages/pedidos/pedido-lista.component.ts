import { CommonModule } from '@angular/common';
import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { RouterLink } from '@angular/router';
import { Pedido } from '../../models/pedido.model';
import { PedidoService } from '../../services/pedido.service';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-pedido-lista',
  standalone: true,
  imports: [CommonModule, RouterLink, FormsModule],
  templateUrl: './pedido-lista.component.html',
  styleUrl: './pedido-lista.component.css'
})
export class PedidoListaComponent implements OnInit {

  pedidos: Pedido[] = [];
  carregando = true;
  erro = '';
  filtroStatus = 'TODOS';

  constructor(
    private pedidoService: PedidoService,
    private cdr: ChangeDetectorRef
  ) {}

  ngOnInit(): void {
    this.carregarPedidos();
  }

  carregarPedidos(): void {
    this.carregando = true;
    this.pedidoService.listar().subscribe({
      next: (dados) => {
        this.pedidos = dados;
        this.carregando = false;
        this.cdr.detectChanges();
      },
      error: () => {
        this.erro = 'Erro ao carregar pedidos';
        this.carregando = false;
        this.cdr.detectChanges();
      }
    });
  }

  
  pedidosFiltrados(): Pedido[] {
    if (this.filtroStatus === 'TODOS') {
      return this.pedidos;
    }
    return this.pedidos.filter(p => p.status === this.filtroStatus);
  }

  mudarFiltro(status: string): void {
    this.filtroStatus = status;
    this.cdr.detectChanges();
  }

  confirmar(id: number): void {
    if (!confirm('Confirmar este pedido? Depois de confirmado não poderá ser cancelado.')) return;
    this.pedidoService.confirmar(id).subscribe({
      next: () => this.carregarPedidos(),
      error: (err: any) => alert(err.error?.mensagem || 'Erro ao confirmar pedido')
    });
  }

  cancelar(id: number): void {
    if (!confirm('Tem certeza que deseja cancelar este pedido?')) return;
    this.pedidoService.cancelar(id).subscribe({
      next: () => this.carregarPedidos(),
      error: (err: any) => alert(err.error?.mensagem || 'Erro ao cancelar pedido')
    });
  }

  calcularTotal(pedido: Pedido): number {
    return pedido.itens.reduce((total, item) => {
      return total + (item.precoUnit * item.quantidade);
    }, 0);
  }

  isPendente(pedido: Pedido): boolean {
    return pedido.status === 'PENDENTE';
  }

  aplicarFiltro(): void {
  this.cdr.detectChanges();
}
}