import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { MatSnackBar } from '@angular/material/snack-bar';

import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatCardModule } from '@angular/material/card';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { ManagerNavbarComponent } from "../manager-navbar/manager-navbar.component";
import { ManagerDashboardComponent } from "../manager-dashboard/manager-dashboard.component";

@Component({
  selector: 'app-edit-customer-info',
  standalone: true,
  imports: [
    CommonModule,
    FormsModule,
    ReactiveFormsModule,
    MatFormFieldModule,
    MatInputModule,
    MatCardModule,
    MatProgressSpinnerModule,
    ManagerNavbarComponent,
],
  templateUrl: './edit-customer-info.component.html',
  styleUrls: ['./edit-customer-info.component.scss']
})
export class EditCustomerInfoComponent implements OnInit {
  editForm: FormGroup;
  customerId: number = 0;
  isLoading = true;
  isUpdating = false;

  constructor(
    private fb: FormBuilder,
    private route: ActivatedRoute,
    public router: Router,
    private snackBar: MatSnackBar
  ) {
    this.editForm = this.fb.group({
      firstName: ['', [Validators.required]],
      lastName: ['', [Validators.required]],
      email: ['', [Validators.required, Validators.email]],
      phone: ['', [Validators.required]],
      currentPoints: ['', [Validators.required, Validators.min(0)]]
    });
  }

  ngOnInit(): void {
    const token = localStorage.getItem('token');
    if (!token) {
      this.redirectToLogin();
      return;
    }

    this.customerId = +this.route.snapshot.paramMap.get('id')!;
    this.fetchCustomerData();
  }

  private redirectToLogin(): void {
    localStorage.removeItem('token');
    this.router.navigate(['/login']);
  }

  private handleAuthError(error: any): void {
    console.error('Authentication error:', error);
    if (error.status === 401 || error.message.includes('401')) {
      this.snackBar.open('Session expired. Please login again.', 'Close', {
        duration: 3000
      });
      this.redirectToLogin();
    } else {
      this.snackBar.open('Failed to load customer data', 'Close', {
        duration: 3000
      });
      this.router.navigate(['/manager-dashboard']);
    }
  }

  fetchCustomerData(): void {
    const token = localStorage.getItem('token');
    if (!token) {
      this.redirectToLogin();
      return;
    }

    fetch(`http://localhost:8080/api/users/find/${this.customerId}`, {
      method: 'GET',
      headers: {
        'Content-Type': 'application/json',
        'Authorization': `Bearer ${token}`
      }
    })
      .then(response => {
        if (response.status === 401) {
          throw { status: 401, message: 'Unauthorized' };
        }
        if (!response.ok) {
          throw new Error('Failed to fetch customer data');
        }
        return response.json();
      })
      .then(customer => {
        this.editForm.patchValue({
          firstName: customer.firstName,
          lastName: customer.lastName,
          email: customer.email,
          phone: customer.phone,
          currentPoints: customer.currentPoints
        });
        this.isLoading = false;
      })
      .catch(error => {
        this.handleAuthError(error);
      });
  }

  onSubmit(): void {
    if (this.editForm.invalid) {
      return;
    }

    const token = localStorage.getItem('token');
    if (!token) {
      this.redirectToLogin();
      return;
    }

    this.isUpdating = true;
    const updatedData = this.editForm.value;

    fetch(`http://localhost:8080/api/users/update/${this.customerId}`, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
        'Authorization': `Bearer ${token}`
      },
      body: JSON.stringify(updatedData)
    })
      .then(response => {
        if (response.status === 401) {
          throw { status: 401, message: 'Unauthorized' };
        }
        if (!response.ok) {
          throw new Error('Failed to update customer');
        }
        return response.json();
      })
      .then(() => {
        this.snackBar.open('Customer updated successfully!', 'Close', {
          duration: 3000
        });
        this.router.navigate(['/view-customer', this.customerId]);
      })
      .catch(error => {
        this.handleAuthError(error);
      })
      .finally(() => {
        this.isUpdating = false;
      });
  }
}