import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { MatSnackBar } from '@angular/material/snack-bar';

import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatError } from '@angular/material/form-field';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule } from '@angular/forms';
import { NavbarComponent } from "../navbar/navbar.component";


@Component({
  selector: 'app-add-customer',
  standalone: true,
  imports: [MatFormFieldModule, 
    MatInputModule,
    MatError,
    CommonModule,
    ReactiveFormsModule,
    NavbarComponent
  ],
  templateUrl: './add-customer.component.html',
  styleUrls: ['./add-customer.component.scss']
})
export class AddCustomerComponent {
  customerForm: FormGroup;
  isLoading = false;
  errorMessage = '';

  constructor(
    private fb: FormBuilder,
    private router: Router,
    private snackBar: MatSnackBar
  ) {
    this.customerForm = this.fb.group({
      firstName: ['', [
        Validators.required,
        Validators.minLength(3),
        Validators.maxLength(10),
        Validators.pattern('^[a-zA-Z]+$')
      ]],
      lastName: ['', [
        Validators.required,
        Validators.minLength(3),
        Validators.maxLength(10),
        Validators.pattern('^[a-zA-Z]+$')
      ]],
      phone: ['', [
        Validators.required,
        Validators.pattern('^[0-9]{10}$')
      ]],
      email: ['', [
        Validators.required,
        Validators.email,
        Validators.pattern('^[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,4}$')
      ]],
      username: ['', [
        Validators.required,
        Validators.minLength(3),
        Validators.maxLength(10),
        Validators.pattern('^[a-zA-Z]+$')
      ]],
      password: ['', [
        Validators.required,
        Validators.minLength(6),
        Validators.maxLength(10),
        Validators.pattern('^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).+$')
      ]]
    });
  }

  handleSubmit(): void {
    if (this.customerForm.invalid) {
      return;
    }

    this.isLoading = true;
    this.errorMessage = '';

    const customerData = {
      ...this.customerForm.value,
      roleName: 'SHOPPER' // Force role to be SHOPPER
    };

    fetch('http://localhost:8080/auth/signup', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
        'Authorization': `Bearer ${localStorage.getItem('token')}`
      },
      body: JSON.stringify(customerData)
    })
      .then(response => {
        if (!response.ok) {
          throw new Error('Failed to create customer');
        }
        return response.json();
      })
      .then(data => {
        this.snackBar.open('Customer created successfully!', 'Close', {
          duration: 3000
        });
        this.router.navigate(['/manager-dashboard']);
      })
      .catch(error => {
        console.error('Error:', error);
        this.errorMessage = 'Failed to create customer. Please try again.';
      })
      .finally(() => {
        this.isLoading = false;
      });
  }
}