import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, FormGroup, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { Router } from '@angular/router';
import { MatOptionModule } from '@angular/material/core';
import { MatSelectModule } from '@angular/material/select'; 

@Component({
  selector: 'app-signup',
  standalone: true,
  imports: [
    CommonModule,
    FormsModule,
    MatFormFieldModule,
    ReactiveFormsModule,
    MatInputModule,
    MatButtonModule,
    MatOptionModule,
    MatSelectModule
  ],
  templateUrl: './signup.component.html',
  styleUrls: ['./signup.component.scss']
})
export class SignupComponent implements OnInit {
  signupForm!: FormGroup;

  constructor(private fb: FormBuilder, private router: Router) {}

  ngOnInit(): void {
    this.signupForm = this.fb.group({
      firstName: ['', [Validators.required, Validators.minLength(3), Validators.maxLength(25), Validators.pattern('[a-zA-Z ]*')]],
      lastName: ['', [Validators.required, Validators.minLength(3), Validators.maxLength(25), Validators.pattern('[a-zA-Z ]*')]],
      username: ['', [Validators.required, Validators.minLength(3), Validators.maxLength(25), Validators.pattern('[a-zA-Z ]*')]],
      phone: ['', [Validators.required, Validators.pattern('^[0-9]{10}$')]], // Example: 10-digit phone number
      email: ['', [Validators.required, Validators.email]],
      password: ['', [Validators.required, Validators.minLength(8), Validators.pattern('(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[$@$!%*?&])[A-Za-z\\d$@$!%*?&].{8,}')]],
      roleName: ['', Validators.required]
    });
  }

  navigateToLogin(): void {
    this.router.navigate(['/']);
  }

  handleSubmit(): void {
    if (this.signupForm.valid) {
      const payload = this.signupForm.value;

      const jsonData = JSON.stringify(payload);

      fetch('http://localhost:8080/auth/signup', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: jsonData,
      })
        .then((response) => {
          if (!response.ok) {
            throw new Error(
              'Failed to create user. Status: ' + response.status
            );
          }
          return response.json();
        })
        .then((data) => {
          console.log('User created successfully:', data);
          this.signupForm.reset();
          this.router.navigate(['/']);
        })
        .catch((error) => {
          console.error('Error creating user:', error.message);
        });
    }
  }
}
