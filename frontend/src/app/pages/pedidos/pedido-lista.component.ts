import { CommonModule } from '@angular/common';
import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { RouterLink } from '@angular/router';
import { Pedido } from '../../models/pedido.model';
import { PedidoService } from '../../services/pedido.service';

@Component({
  selector: 'app-pedido-lista',
  standalone: true,
  imports: [CommonModule, RouterLink],
  templateUrl: './pedido-lista.component.html',
  styleUrl: './pedido-lista.component.css'
})
export class PedidoListaComponent implements OnInit {

  pedidos: Pedido[] = [];
  carregando = true;
  erro = '';

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

  cancelar(id: number): void {
    if (!confirm('Tem certeza que deseja cancelar este pedido?')) return;

    this.pedidoService.cancelar(id).subscribe({
      next: () => {
        this.carregarPedidos();
      },
      error: (err: any) => {
        alert(err.error?.mensagem || 'Erro ao cancelar pedido');
      }
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
}