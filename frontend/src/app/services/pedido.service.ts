import { HttpClient } from "@angular/common/http";
import { Inject, Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { Pedido, PedidoDTO } from "../models/pedido.model";


@Injectable({
    providedIn: 'root'
})
export class PedidoService {

    private apiUrl = 'http://localhost:8080/pedidos';

    constructor(private http : HttpClient) {}

    listar(): Observable<Pedido[]>{
        return this.http.get<Pedido[]>(this.apiUrl);
    }

    buscarPorId(id: number): Observable<Pedido> {
        return this.http.get<Pedido>(`${this.apiUrl}/${id}`)
    }

    criar(dto: PedidoDTO): Observable<number> {
    return this.http.post<number>(this.apiUrl, dto);
  }

    atualizar(id: number, dto: PedidoDTO): Observable<void> {
      return this.http.put<void>(`${this.apiUrl}/${id}`, dto);
  }

    cancelar(id: number): Observable<void> {
      return this.http.patch<void>(`${this.apiUrl}/${id}/cancelar`, {});
}

    confirmar(id: number): Observable<void> {
        return this.http.patch<void>(`${this.apiUrl}/${id}/confirmar`, {});
  }
}