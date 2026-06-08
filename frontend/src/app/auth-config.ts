import { Configuration, LogLevel } from '@azure/msal-browser';

export const msalConfig: Configuration = {
  auth: {
    clientId: '1e6bcd4f-9f18-493d-a730-e82ddaa4d2a2',
    authority: 'https://testazure2026.b2clogin.com/testazure2026.onmicrosoft.com/B2C_1_signin',
    knownAuthorities: ['testazure2026.b2clogin.com'],
    redirectUri: 'http://localhost:4200/auth-callback',
    postLogoutRedirectUri: 'http://localhost:4200',
  },
  cache: {
    cacheLocation: 'localStorage',
  },
  system: {
    loggerOptions: {
      loggerCallback: (level, message, containsPii) => {
        if (containsPii) return;

        if (level === LogLevel.Error) {
          console.error(message);
        }

        if (level === LogLevel.Warning) {
          console.warn(message);
        }
      },
    },
  },
};

export const apiConfig = {
  baseUrl: 'https://5vtcesu7r6.execute-api.us-east-1.amazonaws.com/prod',
};