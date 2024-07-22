import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { MatTableModule } from '@angular/material/table';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { MatNativeDateModule } from '@angular/material/core'; 
import { FormsModule } from '@angular/forms';
import { ReservaService } from '../../service/reserva.service';
import { Reserva } from '../../model/reserva.model';
import { MatCheckboxModule } from '@angular/material/checkbox';
import { ActivatedRoute } from '@angular/router';

@Component({
    selector: 'app-reserva',
    templateUrl: './reserva.component.html',
    styleUrls: ['./reserva.component.scss'],
    standalone: true,
    imports: [
        CommonModule,
        MatFormFieldModule,
        MatInputModule,
        MatButtonModule,
        MatTableModule,
        MatDatepickerModule, 
        MatNativeDateModule, 
        FormsModule
    ],
})
export class ReservaComponent {
    reserva: Reserva = {
        idHospede: 0,
        dataCheckin: '',
        taxaGaragem: false
    };

    reservaList: Reserva[] = [];
    errorMessage: string | null = null;

    displayedColumns: string[] = ['idHospede', 'dataCheckin'];

    constructor(private reservaService: ReservaService, private route: ActivatedRoute) { }
    ngOnInit(): void {
        this.route.queryParams.subscribe(params => {
          const idHospede = params['idHospede'];
          if (idHospede) {
            this.reserva.idHospede = +idHospede; // Converter para número
          }
        });
      }
    salvar(): void {
        this.reservaService.criarReserva(this.reserva).subscribe(
            response => {
                this.reserva = response;
                this.errorMessage = null;
                this.reservaList = [this.reserva];
                console.log('Reserva salva com sucesso:', this.reserva);
            },
            error => {
                if (error.status === 400 || error.status < 500) {
                    this.errorMessage = JSON.stringify(error.error);
                    console.log(error)
                } else {
                    this.errorMessage = 'Erro ao salvar reserva. Tente novamente.';
                }
                this.reservaList = [];
                console.error('Erro ao salvar reserva:', error);
            }
        );
    }
}
