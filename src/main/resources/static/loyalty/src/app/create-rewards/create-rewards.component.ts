// create-reward.component.ts
import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms'; // Ensure Validators is imported
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Router } from '@angular/router';
import { MatSnackBar } from '@angular/material/snack-bar';
import { ReactiveFormsModule } from '@angular/forms';

import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { MatCheckboxModule } from '@angular/material/checkbox';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { CommonModule, NgIf } from '@angular/common';
import { ManagerNavbarComponent } from "../manager-navbar/manager-navbar.component";

@Component({
  selector: 'app-create-reward',
  standalone: true,
  imports: [
    CommonModule,
    MatCheckboxModule,
    ReactiveFormsModule,
    MatCardModule,
    MatFormFieldModule,
    MatInputModule,
    MatButtonModule,
    MatProgressSpinnerModule,
    NgIf,
    ManagerNavbarComponent
],
  templateUrl: './create-rewards.component.html',
  styleUrls: ['./create-rewards.component.scss']
})
export class CreateRewardsComponent {
  rewardForm: FormGroup;
  isLoading = false;

  constructor(
    private fb: FormBuilder,
    private http: HttpClient,
    private router: Router,
    private snackBar: MatSnackBar
  ) {
    this.rewardForm = this.fb.group({
      title: ['', [Validators.required, Validators.maxLength(100)]],
      description: ['', [Validators.maxLength(500)]],
      pointsRequired: ['', [Validators.required, Validators.min(1)]],
      // Change this line:
      isActive: [true, [Validators.requiredTrue]] // Add Validators.requiredTrue
    });
  }

  getHeaders(): HttpHeaders {
    const token = localStorage.getItem('token');
    return new HttpHeaders({
      'Content-Type': 'application/json',
      'Authorization': `Bearer ${token}`
    });
  }

  onSubmit(): void {
    if (this.rewardForm.invalid) {
      // Mark all fields as touched to show validation errors
      this.rewardForm.markAllAsTouched();
      return;
    }

    this.isLoading = true;
    const rewardData = this.rewardForm.value;

    this.http.post('http://localhost:8080/rewards/create', rewardData, {
      headers: this.getHeaders()
    }).subscribe({
      next: () => {
        this.snackBar.open('Reward created successfully!', 'Close', { duration: 3000 });
        this.router.navigate(['/manager-dashboard']);
      },
      error: (error) => {
        this.snackBar.open(error.error?.message || 'Failed to create reward', 'Close', { duration: 3000 });
        this.isLoading = false;
      }
    });
  }
}