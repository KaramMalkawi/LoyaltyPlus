import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { FormBuilder, FormGroup, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [
    CommonModule,
    FormsModule,
    MatFormFieldModule,
    ReactiveFormsModule,
    MatInputModule,
    MatButtonModule
  ],
  templateUrl: './login.component.html',
  styleUrl: './login.component.scss'
})
export class LoginComponent implements OnInit {
  loginForm: FormGroup;

  constructor(private fb: FormBuilder, private router: Router) {
    this.loginForm = this.fb.group({
      username: ['', [Validators.required, Validators.minLength(2)]],
      password: ['', [Validators.required, Validators.minLength(3)]],
    });
  }

  ngOnInit(): void {
    // Any initialization logic can go here
  }

  // Navigate to Signup page
  navigateToSignup(): void {
    this.router.navigate(['/signup']);
  }

  // Handle form submission
  handleSubmit(): void {
    if (this.loginForm.valid) {
      const payload = this.loginForm.value;

      const jsonData = JSON.stringify(payload);

      // Make the login API call
      fetch('http://localhost:8080/auth/login', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: jsonData,
      })
        .then((response) => {
          if (!response.ok) {
            throw new Error('Failed to login. Status: ' + response.status);
          }
          return response.json();
        })
        .then((data) => {
          console.log('Login successful:', data);

          // Store token and username
          localStorage.setItem('token', data.token);
          localStorage.setItem('username', payload.username);
          localStorage.setItem('userId', data.userId);

          // Fetch user details to get userType
          return fetch('http://localhost:8080/api/users/details', {
            method: 'GET',
            headers: {
              Authorization: `Bearer ${data.token}`,
              'Content-Type': 'application/json',
            },
          });
        })
        .then((response) => {
          if (!response.ok) {
            throw new Error(
              'Failed to fetch user details. Status: ' + response.status
            );
          }
          return response.json();
        })
        .then((userDetails) => {
          // Check if userDetails exists and has the expected properties
          if (!userDetails || !userDetails.role || !userDetails.role.name) {
            throw new Error('Invalid user details response');
          }

          const userRole = userDetails.role.name.toUpperCase();
          const userId = userDetails.id;
          console.log('User role:', userRole);
          console.log('User ID:', userId);

          localStorage.setItem('userRole', userRole);
          localStorage.setItem('userId', userId);

          if (userRole === 'STORE_MANAGER') {
            this.router.navigate(['/manager-dashboard']);
          } else if (userRole === 'MARKETING_ANALYST') {
            this.router.navigate(['/marketing-analyst-dashboard']);
          } else {
            alert('Invalid user type');
          }
        })
        .catch((error) => {
          console.error('Error:', error);
          alert('Login failed: Check your username and password');
        });
    }
  }
}
