import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Produto, ProdutoDTO } from '../models/produto.model';

@Injectable({
  providedIn: 'root'
})
export class ProdutoService {

  private apiUrl = 'http://localhost:8080/produtos';

  constructor(private http: HttpClient) {}

  listar(): Observable<Produto[]> {
    return this.http.get<Produto[]>(this.apiUrl);
  }

  buscarPorId(id: number): Observable<Produto> {
    return this.http.get<Produto>(`${this.apiUrl}/${id}`);
  }

  cadastrar(dto: ProdutoDTO): Observable<void> {
    return this.http.post<void>(this.apiUrl, dto);
  }

  atualizar(id: number, dto: ProdutoDTO): Observable<void> {
    return this.http.put<void>(`${this.apiUrl}/${id}`, dto);
  }

  deletar(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }

  importarEletronicos(): Observable<Produto[]> {
  return this.http.post<Produto[]>(
    'http://localhost:8080/importacao/eletronicos',
    {}
  );
}

}