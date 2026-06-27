export interface Produto {
  id: number;
  nome: string;
  descricao: string;
  preco: number;
  estoque: number;
  estoqueMin: number;
  criadoEm: string;
}

export interface ProdutoDTO {
  nome: string;
  descricao: string;
  preco: number;
  estoque: number;
  estoqueMin: number;
}