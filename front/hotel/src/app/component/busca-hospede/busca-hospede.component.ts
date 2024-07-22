// src/app/busca-hospede/busca-hospede.component.ts
import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { FormsModule } from '@angular/forms';
import { HospedeService } from '../../service/hospede.service';
import { Hospede } from '../../model/hospede.model';
import { MatTableModule } from '@angular/material/table';
import { Router } from '@angular/router';
import { ReservaService } from '../../service/reserva.service';

@Component({
  selector: 'app-busca-hospede',
  templateUrl: './busca-hospede.component.html',
  styleUrls: ['./busca-hospede.component.scss'],
  standalone: true,
  imports: [
    CommonModule,
    MatFormFieldModule,
    MatInputModule,
    MatButtonModule,
    FormsModule,
    MatTableModule,  
  ],
})
export class BuscaHospedeComponent {
  hospede: Hospede = {
    id: 0,
    nome: '',
    telefone: 0,
    documento: 0,
    idReserva: 0
  };

  hospedeList: Hospede[] = [];  
  errorMessage: string | null = null;
  displayedColumns: string[] = ['id', 'nome', 'telefone', 'documento', 'actions']; 

  constructor(private hospedeService: HospedeService, private router: Router, private reservaService: ReservaService) {}

  buscar(): void {
    this.hospedeService.buscarHospede(this.hospede).subscribe(
      response => {
        this.hospede = {
          id: response.id,
          nome: response.nome,
          telefone: response.telefone,
          documento: response.documento
        };
        this.hospedeList = [this.hospede];
        this.errorMessage = null; 
      },
      error => {
        if (error.status === 404) {
          this.errorMessage = error.error;
        } else {
          this.errorMessage = 'Erro ao buscar hospede. Tente novamente.';
        }
        this.hospedeList = []; 
      }
    );
  }

  salvar(): void {
    this.hospedeService.salvarHospede(this.hospede).subscribe(
      response => {
        this.hospede = {
          id: response.id,
          nome: response.nome,
          telefone: response.telefone,
          documento: response.documento
        };
        this.hospedeList = [this.hospede];
        this.errorMessage = null; 
      },
      error => {
        if (error.status === 400) {
          this.errorMessage = error.error;
        } else {
          this.errorMessage = 'Erro ao salvar hospede. Tente novamente.';
        }
        this.hospedeList = []; 
      }
    );
  }
  checkin(): void {
    this.reservaService.checkin(this.hospede).subscribe(
      response => {
        this.router.navigate(['/reserva'])
      },
      error => {
        if (error.status === 400) {
          this.errorMessage = error.error;
        } else {
          this.errorMessage = 'Erro ao salvar hospede. Tente novamente.';
        }
        this.hospedeList = []; 
      }
    );
  }

  checkout(): void {
    this.reservaService.checkout(this.hospede.idReserva).subscribe(
      response => {
        this.router.navigate(['/reserva'])
      },
      error => {
        if (error.status === 400) {
          this.errorMessage = error.error;
        } else {
          this.errorMessage = 'Erro ao salvar hospede. Tente novamente.';
        }
        this.hospedeList = []; 
      }
    );
  }
  criarReserva(idHospede: number): void {
    this.router.navigate(['/criarreserva'], { queryParams: { idHospede } });
  }
}
