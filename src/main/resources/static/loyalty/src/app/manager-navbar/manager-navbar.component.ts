import { Component } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-manager-navbar',
  templateUrl: './manager-navbar.component.html',
  styleUrls: ['./manager-navbar.component.scss'],
})
export class ManagerNavbarComponent {
  token: string | null = null;

  constructor(private router: Router) {
    this.token = localStorage.getItem('token'); // Get token from local storage
  }

  // Navigate to Login page
  redirectToLogin() {
    this.router.navigate(['/']);
  }

  navigateToCreateReward() {
    this.router.navigate(['/create-reward']);
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
