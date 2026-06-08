import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { MsalService } from '@azure/msal-angular';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './login.html',
  styleUrl: './login.scss'
})
export class LoginComponent implements OnInit {
  checkingSession = true;

  constructor(
    private msalService: MsalService,
    private router: Router
  ) {}

  ngOnInit() {
    const activeAccount = this.msalService.instance.getActiveAccount();
    const accounts = this.msalService.instance.getAllAccounts();

    if (activeAccount) {
      this.router.navigate(['/dashboard'], { replaceUrl: true });
      return;
    }

    if (accounts.length > 0) {
      this.msalService.instance.setActiveAccount(accounts[0]);
      this.router.navigate(['/dashboard'], { replaceUrl: true });
      return;
    }

    this.checkingSession = false;
  }

  login() {
    this.msalService.loginRedirect();
  }
}