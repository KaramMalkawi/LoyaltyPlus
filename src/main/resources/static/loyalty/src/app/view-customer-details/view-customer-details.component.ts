import { Component, OnInit } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';
import { ActivatedRoute, Router } from '@angular/router';

import { CommonModule } from '@angular/common';
import { MatCardModule } from '@angular/material/card';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';

import { NavbarComponent } from '../navbar/navbar.component';

interface Customer {
  id: number;
  firstName: string;
  lastName: string;
  username: string;
  phone: string;
  email: string;
  currentPoints: number;
  createdAt: string;
  role: {
    name: string;
  };
}

@Component({
  selector: 'app-view-customer-details',
  standalone: true, // Required when using `imports` array in @Component
  imports: [
    CommonModule,
    MatCardModule,
    MatProgressSpinnerModule,
    NavbarComponent
  ],
  templateUrl: './view-customer-details.component.html',
  styleUrls: ['./view-customer-details.component.scss']
})
export class ViewCustomerDetailsComponent implements OnInit {
  customer: Customer | null = null;
  isLoading = true;
  customerId: number | null = null;

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private snackBar: MatSnackBar
  ) {}

  ngOnInit(): void {
    const idParam = this.route.snapshot.paramMap.get('id');
    this.customerId = idParam ? +idParam : null;

    if (this.customerId !== null) {
      this.fetchCustomerDetails();
    } else {
      this.snackBar.open('Invalid customer ID', 'Close', { duration: 3000 });
      this.router.navigate(['/manager-dashboard']);
    }
  }

  fetchCustomerDetails(): void {
    const token = localStorage.getItem('token');

    if(!token) {
      this.router.navigate(['']);
    }
    fetch(`http://localhost:8080/api/users/find/${this.customerId}`, {
      method: 'GET',
      headers: {
        'Content-Type': 'application/json',
        'Authorization': `Bearer ${token}`
      }
    })
      .then(response => {
        if (!response.ok) {
          throw new Error('Failed to fetch customer details');
        }
        return response.json();
      })
      .then((data: Customer) => {
        this.customer = data;
        this.isLoading = false;
      })
      .catch(error => {
        console.error('Error:', error);
        this.snackBar.open('Failed to load customer details', 'Close', {
          duration: 3000
        });
        this.isLoading = false;
      });
  }

  formatDate(dateString: string): string {
    const date = new Date(dateString);
    return date.toLocaleDateString() + ' ' + date.toLocaleTimeString();
  }

  navigateToEditCustomer(): void {
    if (this.customerId) {
      this.router.navigate(['/edit-customer', this.customerId]);
    }
  }

  deleteCustomer(): void {
    const token = localStorage.getItem('token');

    if (!token) {
      this.router.navigate(['']);
      alert('Authentication token not found.');
      return;
    }

    fetch(`http://localhost:8080/api/users/delete/${this.customerId}`, {
      method: 'DELETE',
      headers: {
        'Content-Type': 'application/json',
        'Authorization': `Bearer ${token}`
      }
    })
      .then(response => {
        if (!response.ok) {
          throw new Error('Failed to delete customer');
        }
        this.snackBar.open('Customer deleted successfully', 'Close', {
          duration: 3000
        });
        this.router.navigate(['/manager-dashboard']);
        // If you need to remove the deleted user from a list:
        // this.dataSource.data = this.dataSource.data.filter(user => user.id !== customerId);
      })
      .catch(error => {
        console.error('Delete user error:', error.message);
        alert('Error deleting user. See console for details.');
      });
  }

  addRewards(): void {
    if (this.customerId) {
      this.router.navigate(['/add-rewards', this.customerId]);
    }
  }
}