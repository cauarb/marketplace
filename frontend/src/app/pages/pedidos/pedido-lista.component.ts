import { CommonModule } from "@angular/common";
import { Component, OnInit } from "@angular/core";
import { RouterLink } from "@angular/router";
import { Pedido } from "../../models/pedido.model";
import { PedidoService } from "../../services/pedido.service";


@Component({
    selector : 'app-pedido-lista',
    standalone: true,
    imports: [CommonModule, RouterLink],
    templateUrl: './pedido-lista.component.html',
    styleUrl: './pedido-lista.component.css'
})
export class PedidoListaComponet implements OnInit {

    pedidos: Pedido[] = [];
    carregando = true;
    erro = '';

    constructor(private pedidoService: PedidoService) {}
        
    ngOnInit(): void {
    this.carregarPedidos();
    }

    carregarPedidos(): void {
        this.carregando = true;
        this.pedidoService.listar().subscribe({
            next: (dados) => {
                this.pedidos = dados;
                this.carregando = false;
            },

            error: () => {
                this.erro = 'Erro ao carregar pedidos';
                this.carregando = false;
            }            
        });
    }

     calcularTotal(pedido: Pedido): number {
    return pedido.itens.reduce((total, item) => {
      return total + (item.precoUnit * item.quantidade);
    }, 0);
  }
}