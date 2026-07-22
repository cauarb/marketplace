import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Painel } from '../models/painel.model';

@Injectable({
  providedIn: 'root'
})
export class PainelService {

  private apiUrl = 'http://localhost:8080/painel';

  constructor(private http: HttpClient) {}

  buscar(): Observable<Painel> {
    return this.http.get<Painel>(this.apiUrl);
  }
}