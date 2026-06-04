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
}