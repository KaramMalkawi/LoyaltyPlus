import { ActivatedRoute, Router } from '@angular/router';
import { MatSnackBar } from '@angular/material/snack-bar';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { CommonModule } from '@angular/common';
import { MatTableModule } from '@angular/material/table';
import { MatCardModule } from '@angular/material/card';
import { MatButtonModule } from '@angular/material/button';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { Component, inject, OnInit } from '@angular/core';
import { NavbarComponent } from "../navbar/navbar.component";

interface Reward {
  id: number;
  title: string;
  description: string;
  pointsRequired: number;
}
@Component({
  selector: 'app-add-rewards',
  standalone: true,
  imports: [
    CommonModule,
    MatTableModule,
    MatCardModule,
    MatButtonModule,
    MatProgressSpinnerModule,
    NavbarComponent
],
  templateUrl: './add-rewards.component.html',
  styleUrls: ['./add-rewards.component.scss'],
})
export class AddRewardsComponent implements OnInit {
  private route = inject(ActivatedRoute);
  private router = inject(Router);
  private snackBar = inject(MatSnackBar);
  private http = inject(HttpClient);

  rewards: Reward[] = [];
  isLoading = true;
  customerId: number = Number(this.route.snapshot.paramMap.get('id'));
  currentPoints: number | null = null;
  displayedColumns: string[] = [
    'title',
    'description',
    'pointsRequired',
    'action',
  ];

  ngOnInit(): void {
    if (!this.customerId) {
      this.snackBar.open('Invalid customer ID', 'Close', { duration: 3000 });
      this.router.navigate(['/manager-dashboard']);
      return;
    }

    this.loadRewards();
    this.loadCustomerPoints();
  }

  loadRewards(): void {
    const headers = this.getHeaders();
    console.log('Attempting to load rewards with headers:', headers); // Debug log

    this.http
      .get<Reward[]>('http://localhost:8080/rewards/active', { headers })
      .subscribe({
        next: (data) => {
          console.log('Rewards loaded successfully:', data); // Debug log
          this.rewards = data;
          this.isLoading = false;
        },
        error: (error) => {
          console.error('Error loading rewards:', error); // Debug log
          this.snackBar.open(
            error.error?.message || 'Failed to load rewards',
            'Close',
            { duration: 3000 }
          );
          this.isLoading = false;
        },
      });
  }

  private getHeaders(): HttpHeaders {
    const token = localStorage.getItem('token');
    if (!token) {
      console.warn('No token found in localStorage'); // Debug log
      this.router.navigate(['/login']);
      throw new Error('Authentication required');
    }

    return new HttpHeaders({
      'Content-Type': 'application/json',
      Authorization: `Bearer ${token}`,
    });
  }

  loadCustomerPoints(): void {
    this.http
      .get<any>(`http://localhost:8080/api/users/find/${this.customerId}`, {
        headers: this.getHeaders(),
      })
      .subscribe({
        next: (user) => (this.currentPoints = user.currentPoints),
        error: () =>
          this.snackBar.open('Failed to load customer points', 'Close', {
            duration: 3000,
          }),
      });
  }

  // redeemReward(reward: Reward): void {
  //   if (this.currentPoints === null || this.currentPoints < reward.pointsRequired) {
  //     this.snackBar.open('Not enough points', 'Close', { duration: 3000 });
  //     return;
  //   }

  //   const body = {
  //     userId: this.customerId,
  //     rewardId: reward.id
  //   };

  //   this.http.post('http://localhost:8080/reward-redemptions/create', body, {
  //     headers: this.getHeaders()
  //   }).subscribe({
  //     next: () => {
  //       this.snackBar.open('Reward redeemed successfully!', 'Close', { duration: 3000 });
  //       this.router.navigate(['/view-customer', this.customerId]);
  //     },
  //     error: (error) => {
  //       this.snackBar.open(error.error?.message || 'Redemption failed', 'Close', { duration: 3000 });
  //     }
  //   });
  // }

  redeemReward(reward: Reward): void {
    if (
      this.currentPoints === null ||
      this.currentPoints < reward.pointsRequired
    ) {
      this.snackBar.open('Not enough points', 'Close', { duration: 3000 });
      return;
    }

    const body = {
      userId: this.customerId,
      rewardId: reward.id,
    };

    this.http
      .post('http://localhost:8080/reward-redemptions/create', body, {
        headers: this.getHeaders(),
      })
      .subscribe({
        next: () => {
          // Update points locally first for immediate UI feedback
          if (this.currentPoints !== null) {
            this.currentPoints -= reward.pointsRequired;
          }

          // Update points on the backend
          this.updateCustomerPoints(this.currentPoints || 0);

          this.snackBar.open('Reward redeemed successfully!', 'Close', {
            duration: 3000,
          });
          this.router.navigate(['/view-customer', this.customerId]);
        },
        error: (error) => {
          this.snackBar.open(
            error.error?.message || 'Redemption failed',
            'Close',
            { duration: 3000 }
          );
        },
      });
  }

  private updateCustomerPoints(newPoints: number): void {
    this.http
      .post(
        `http://localhost:8080/api/users/update/points/${this.customerId}`,
        { currentPoints: newPoints },
        { headers: this.getHeaders() }
      )
      .subscribe({
        next: () => console.log('Points updated successfully'),
        error: (error) => console.error('Failed to update points:', error),
      });
  }

  goBack(): void {
    this.router.navigate(['/view-customer', this.customerId]);
  }
}
