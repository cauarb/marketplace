export interface ItemPedido {
  id: number;
  pedidoId: number;
  produtoId: number;
  quantidade: number;
  precoUnit: number;
}

export interface Pedido {
  id: number;
  status: string;
  criadoEm: string;
  itens: ItemPedido[];
}

export interface ItemPedidoDTO {
  produtoId: number;
  quantidade: number;
}

export interface PedidoDTO {
  itens: ItemPedidoDTO[];
}