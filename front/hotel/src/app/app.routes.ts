import { Routes } from '@angular/router';
import { ReservaListComponent } from './component/reserva-list/reserva-list.component';
import { BuscaHospedeComponent } from './component/busca-hospede/busca-hospede.component';
import { ReservaComponent } from './component/reserva/reserva.component';

// Definindo as rotas
export const routes: Routes = [
    { path: 'reserva', component: ReservaListComponent },
    { path: '', component: ReservaListComponent },
    { path: 'hospede', component: BuscaHospedeComponent },
    { path: 'criarreserva', component: ReservaComponent }
];
