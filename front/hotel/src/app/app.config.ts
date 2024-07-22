import { ApplicationConfig } from '@angular/core';
import { provideRouter } from '@angular/router';
import { provideHttpClient } from '@angular/common/http';
import { provideAnimationsAsync } from '@angular/platform-browser/animations/async';
import { provideZoneChangeDetection } from '@angular/core';
import { routes } from './app.routes'; // Certifique-se de que este arquivo existe e está configurado corretamente
import { MatNativeDateModule, provideNativeDateAdapter } from '@angular/material/core'; // Importando MatNativeDateModule

export const appConfig: ApplicationConfig = {
  providers: [
    provideZoneChangeDetection({ eventCoalescing: true }), // Configuração opcional para gerenciamento de mudanças de zona
    provideRouter(routes), // Configuração do roteamento
    provideAnimationsAsync(), // Configuração das animações
    provideHttpClient(),
    provideNativeDateAdapter()
  ]
};
