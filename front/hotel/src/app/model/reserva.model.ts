export enum StatusReserva {
    RESERVADO = 'RESERVADO',
    CHECKIN = 'CHECKIN',
    CHECKOUT = 'CHECKOUT',
  }
  
  export interface Reserva {
    id?: number;
    idHospede?: number;
    idDiaria?: number;
    statusReserva?: StatusReserva;
    dataCheckin?: string; 
    dataCheckout?: string; 
    taxaGaragem?: boolean;
    valorTotal?: number; 
    valorFinalCobrado?: number; 
    valorAcrescimoCheckout?: number; 
  }
  