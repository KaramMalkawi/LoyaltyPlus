import { Component } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.scss'],
})
export class NavbarComponent {
  token: string | null = null;

  constructor(private router: Router) {
    this.token = localStorage.getItem('token'); // Get token from local storage
  }

  // Navigate to Login page
  redirectToLogin() {
    this.router.navigate(['/']);
  }

  redirectToManagerItems(): void {
    this.router.navigate(['/manager-item']);
  }

  submitLogoutForm() {
    fetch('http://localhost:8080/auth/logout', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
        Authorization: `Bearer ${this.token}`,
      },
    })
      .then((response) => {
        if (!response.ok) {
          throw new Error(`Logout failed: ${response.statusText}`);
        }
        localStorage.clear();
        this.redirectToLogin();
      })
      .catch((error) => {
        console.error('Logout error:', error.message);
      });
  }
}
