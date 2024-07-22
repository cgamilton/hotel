import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Reserva } from '../model/reserva.model';
import { Hospede } from '../model/hospede.model';

@Injectable({
  providedIn: 'root'
})
export class ReservaService {
  private apiUrl = 'http://localhost:8080/api/reserva';

  constructor(private http: HttpClient) { }

  getReservas(): Observable<Reserva[]> {
    return this.http.get<Reserva[]>(`${this.apiUrl}/listar`);
  }

  criarReserva(reserva: Reserva): Observable<Reserva> {
    return this.http.post<Reserva>(`${this.apiUrl}/criar`, reserva);
  }

  checkin(hospede: Hospede){
    return this.http.put<Reserva>(`${this.apiUrl}/checkin`, hospede)
  }

  checkout(idReserva: any) {
    return this.http.put<Reserva>(`${this.apiUrl}/${idReserva}/checkout`, {});
  }
  
}
