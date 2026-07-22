import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { AuditoriaEstoque } from '../models/auditoria.model';

@Injectable({
  providedIn: 'root'
})
export class AuditoriaService {

  private apiUrl = 'http://localhost:8080/auditoria';
  private movimentacaoUrl = 'http://localhost:8080/movimentacoes';

  constructor(private http: HttpClient) {}

  auditarTodos(): Observable<AuditoriaEstoque[]> {
    return this.http.get<AuditoriaEstoque[]>(this.apiUrl);
  }

  auditarPorId(id: number): Observable<AuditoriaEstoque> {
    return this.http.get<AuditoriaEstoque>(`${this.apiUrl}/${id}`);
  }

  registrarEntrada(produtoId: number, quantidade: number, motivo: string): Observable<void> {
    return this.http.post<void>(this.movimentacaoUrl, {
      produtoId,
      tipo: 'ENTRADA',
      quantidade,
      motivo
    });
  }
}