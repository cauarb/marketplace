import { Produto } from './produto.model';

export interface Painel {
  pedidosHoje: number;
  pedidosSemana: number;
  pedidosMes: number;

  faturamentoHoje: number;
  faturamentoSemana: number;
  faturamentoMes: number;

  pedidosPendentes: number;
  pedidosConfirmados: number;
  pedidosCancelados: number;

  totalProdutos: number;
  valorTotalEstoque: number;
  produtoMaisvendido: string;

  produtosEmAlerta: Produto[];
}