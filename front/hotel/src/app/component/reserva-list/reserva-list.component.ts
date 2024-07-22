import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatTableModule } from '@angular/material/table';  // Importando o módulo da tabela
import { MatButtonModule } from '@angular/material/button'; // Importando o módulo do botão
import { ReservaService } from '../../service/reserva.service';
import { Reserva } from '../../model/reserva.model'; // Atualize o caminho conforme necessário
import { RouterModule } from '@angular/router';

@Component({
  selector: 'app-reserva-list',
  templateUrl: './reserva-list.component.html',
  styleUrls: ['./reserva-list.component.scss'],
  standalone: true,
  imports: [
    CommonModule,
    MatTableModule,  
    MatButtonModule,
    RouterModule
  ],
})
export class ReservaListComponent implements OnInit {
  reservas: Reserva[] = [];
  error: string | null = null;

  displayedColumns: string[] = ['id', 'statusReserva', 'dataCheckin', 'dataCheckout', 'taxaGaragem', 'valorTotal', 'valorFinalCobrado', 'valorAcrescimoCheckout'];

  constructor(private reservaService: ReservaService) {}

  ngOnInit(): void {
    this.reservaService.getReservas().subscribe(
      data => {
        this.reservas = data;
      },
      error => {
        this.error = 'Falha ao carregar reservas';
        console.error('Erro ao buscar reservas', error);
      }
    );
  }
}
