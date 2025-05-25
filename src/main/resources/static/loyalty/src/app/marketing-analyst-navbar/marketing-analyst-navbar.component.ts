import { Component } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-marketing-analyst-navbar',
  imports: [],
  templateUrl: './marketing-analyst-navbar.component.html',
  styleUrl: './marketing-analyst-navbar.component.scss'
})
export class MarketingAnalystNavbarComponent {
  token: string | null = null;

/*************  ✨ Windsurf Command ⭐  *************/
/*******  485dc031-1957-48d8-88ce-761689f972ef  *******/
  constructor(private router: Router) {
    this.token = localStorage.getItem('token'); // Get token from local storage
  }

  // Navigate to Login page
  redirectToLogin() {
    this.router.navigate(['/']);
  }

  navigateToReward() {
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
