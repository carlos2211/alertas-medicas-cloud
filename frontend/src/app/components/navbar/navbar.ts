import { Component, OnInit } from '@angular/core';
import { Router, RouterLink, RouterLinkActive } from '@angular/router';
import { MsalService } from '@azure/msal-angular';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-navbar',
  standalone: true,
  imports: [CommonModule, RouterLink, RouterLinkActive],
  templateUrl: './navbar.html',
  styleUrl: './navbar.scss'
})
export class NavbarComponent implements OnInit {
  userName: string = '';

  constructor(private msalService: MsalService, private router: Router) {}

  ngOnInit() {
    const account = this.msalService.instance.getActiveAccount();
    if (account) {
      this.userName = account.name || account.username;
    }
  }

  logout() {
    this.msalService.logoutRedirect();
  }
}