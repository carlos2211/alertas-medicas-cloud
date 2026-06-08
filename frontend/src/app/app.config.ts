import { ApplicationConfig, provideZoneChangeDetection } from '@angular/core';
import { provideRouter } from '@angular/router';
import { provideHttpClient } from '@angular/common/http';
import { provideAnimationsAsync } from '@angular/platform-browser/animations/async';

import {
  MSAL_INSTANCE,
  MSAL_GUARD_CONFIG,
  MsalService,
  MsalGuard,
  MsalBroadcastService,
  MsalGuardConfiguration,
} from '@azure/msal-angular';

import {
  PublicClientApplication,
  InteractionType,
} from '@azure/msal-browser';

import { msalConfig } from './auth-config';
import { routes } from './app.routes';

export function MSALInstanceFactory() {
  return new PublicClientApplication(msalConfig);
}

export function MSALGuardConfigFactory(): MsalGuardConfiguration {
  return {
    interactionType: InteractionType.Redirect,
  };
}

export const appConfig: ApplicationConfig = {
  providers: [
    provideZoneChangeDetection({ eventCoalescing: true }),
    provideRouter(routes),
    provideHttpClient(),
    provideAnimationsAsync(),

    {
      provide: MSAL_INSTANCE,
      useFactory: MSALInstanceFactory,
    },
    {
      provide: MSAL_GUARD_CONFIG,
      useFactory: MSALGuardConfigFactory,
    },

    MsalService,
    MsalGuard,
    MsalBroadcastService,
  ],
};