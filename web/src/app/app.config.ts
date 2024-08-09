import { ApplicationConfig } from '@angular/core';
import { provideRouter, withComponentInputBinding } from '@angular/router';

import { routes } from './app.routes';
import { provideClientHydration } from '@angular/platform-browser';
import { provideHttpClient } from '@angular/common/http';
export const appConfig: ApplicationConfig = {
  //providers: [provideRouter(routes), provideClientHydration()]
  providers: [provideRouter(routes, withComponentInputBinding()),provideHttpClient(), provideClientHydration()]

};

