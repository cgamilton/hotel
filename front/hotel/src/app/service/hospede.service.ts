// src/app/hospede.service.ts
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Hospede } from '../model/hospede.model';

@Injectable({
  providedIn: 'root'
})
export class HospedeService {
  private baseUrl = 'http://localhost:8080/api/hospede';

  constructor(private http: HttpClient) {}

  buscarHospede(hospede: Hospede): Observable<Hospede> {
    return this.http.post<Hospede>(`${this.baseUrl}/retornaHospede`, hospede);
  }

  salvarHospede(hospede: Hospede): Observable<Hospede> {
    return this.http.post<Hospede>(`${this.baseUrl}/novo`, hospede);
  }
}
