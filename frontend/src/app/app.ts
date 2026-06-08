import { Component, OnInit } from '@angular/core';
import { Router, RouterOutlet } from '@angular/router';
import { MsalService, MsalBroadcastService } from '@azure/msal-angular';
import { InteractionStatus, EventType } from '@azure/msal-browser';
import { filter } from 'rxjs/operators';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet, CommonModule],
  template: `<router-outlet></router-outlet>`
})
export class AppComponent implements OnInit {
  constructor(
    private msalService: MsalService,
    private msalBroadcastService: MsalBroadcastService,
    private router: Router
  ) {}

  ngOnInit() {
    this.msalService.instance.initialize().then(() => {
      this.msalService.instance.handleRedirectPromise().then((result) => {
        if (result) {
          this.msalService.instance.setActiveAccount(result.account);
          this.router.navigate(['/dashboard']);
        } else {
          const accounts = this.msalService.instance.getAllAccounts();
          if (accounts.length > 0) {
            this.msalService.instance.setActiveAccount(accounts[0]);
            this.router.navigate(['/dashboard']);
          }
        }
      });
    });
  }
}