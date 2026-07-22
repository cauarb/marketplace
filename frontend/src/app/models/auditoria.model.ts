export interface AuditoriaEstoque {
  produtoId: number;
  nomeProduto: string;
  saldoAtual: number;
  movimentacoes: string[];
}

export interface EntradaEstoqueDTO {
  produtoId: number;
  quantidade: number;
  motivo: string;
}