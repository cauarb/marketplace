import { CommonModule } from '@angular/common';
import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { RouterLink } from '@angular/router';
import { Painel } from '../../models/painel.model';
import { PainelService } from '../../services/painel.service';

@Component({
  selector: 'app-painel',
  standalone: true,
  imports: [CommonModule, RouterLink],
  templateUrl: './painel.component.html'
})
export class PainelComponent implements OnInit {

  painel: Painel | null = null;
  carregando = true;
  erro = '';

  constructor(
    private painelService: PainelService,
    private cdr: ChangeDetectorRef
  ) {}

  ngOnInit(): void {
    this.carregarPainel();
  }

  carregarPainel(): void {
    this.carregando = true;
    this.painelService.buscar().subscribe({
      next: (dados) => {
        this.painel = dados;
        this.carregando = false;
        this.cdr.detectChanges();
      },
      error: () => {
        this.erro = 'Erro ao carregar o painel';
        this.carregando = false;
        this.cdr.detectChanges();
      }
    });
  }


  getCardsPedidos() {
    if (!this.painel) return [];
    return [
      { titulo: 'Hoje', valor: this.painel.pedidosHoje, valorMoeda: this.painel.faturamentoHoje },
      { titulo: 'Últimos 7 dias', valor: this.painel.pedidosSemana, valorMoeda: this.painel.faturamentoSemana },
      { titulo: 'Últimos 30 dias', valor: this.painel.pedidosMes, valorMoeda: this.painel.faturamentoMes }
    ];
  }

  
  getCardsStatus() {
    if (!this.painel) return [];
    return [
      { titulo: 'Pendentes', valor: this.painel.pedidosPendentes, cor: 'text-warning' },
      { titulo: 'Confirmados', valor: this.painel.pedidosConfirmados, cor: 'text-success' },
      { titulo: 'Cancelados', valor: this.painel.pedidosCancelados, cor: 'text-danger' }
    ];
  }
}