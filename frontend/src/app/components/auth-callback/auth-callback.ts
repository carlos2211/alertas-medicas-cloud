import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { MsalService } from '@azure/msal-angular';

@Component({
  selector: 'app-auth-callback',
  standalone: true,
  template: `<p>Procesando inicio de sesión...</p>`,
})
export class AuthCallbackComponent implements OnInit {
  constructor(
    private msalService: MsalService,
    private router: Router,
  ) {}

  ngOnInit(): void {
    this.msalService.handleRedirectObservable().subscribe({
      next: (result) => {
        if (result?.account) {
          this.msalService.instance.setActiveAccount(result.account);
        } else {
          const accounts = this.msalService.instance.getAllAccounts();

          if (accounts.length > 0) {
            this.msalService.instance.setActiveAccount(accounts[0]);
          }
        }

        this.router.navigate(['/dashboard'], { replaceUrl: true });
      },
      error: (error) => {
        console.error('Error procesando login:', error);
        this.router.navigate(['/login'], { replaceUrl: true });
      },
    });
  }
}
