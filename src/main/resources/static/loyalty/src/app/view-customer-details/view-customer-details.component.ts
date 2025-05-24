import { Component, OnInit } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';
import { ActivatedRoute, Router } from '@angular/router';
import { CommonModule } from '@angular/common';
import { MatCardModule } from '@angular/material/card';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { MatTableModule } from '@angular/material/table';
import { MatButtonModule } from '@angular/material/button';

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

interface Reward {
  id: number;
  title: string;
  description: string;
  pointsRequired: number;
  isActive: boolean;
  createdAt: string;
  updatedAt: string;
}

@Component({
  selector: 'app-view-customer-details',
  standalone: true,
  imports: [
    CommonModule,
    MatCardModule,
    MatProgressSpinnerModule,
    MatTableModule,
    MatButtonModule
  ],
  templateUrl: './view-customer-details.component.html',
  styleUrls: ['./view-customer-details.component.scss']
})
export class ViewCustomerDetailsComponent implements OnInit {
  customer: Customer | null = null;
  isLoading = true;
  customerId: number | null = null;
  
  // Rewards properties
  rewards: Reward[] = [];
  displayedColumns: string[] = ['title', 'description', 'pointsRequired', 'action'];
  isRewardsLoading = false;

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
        this.fetchAvailableRewards();
      })
      .catch(error => {
        console.error('Error:', error);
        this.snackBar.open('Failed to load customer details', 'Close', {
          duration: 3000
        });
        this.isLoading = false;
      });
  }

  fetchAvailableRewards(): void {
    this.isRewardsLoading = true;
    const token = localStorage.getItem('token');

    fetch('http://localhost:8080/api/rewards/all', {
      method: 'GET',
      headers: {
        'Content-Type': 'application/json',
        'Authorization': `Bearer ${token}`
      }
    })
      .then(response => {
        if (!response.ok) {
          throw new Error('Failed to fetch rewards');
        }
        return response.json();
      })
      .then((data: Reward[]) => {
        this.rewards = data.filter(reward => 
          reward.isActive && reward.pointsRequired <= (this.customer?.currentPoints || 0)
        );
        this.isLoading = false;
        this.isRewardsLoading = false;
      })
      .catch(error => {
        console.error('Error:', error);
        this.snackBar.open('Failed to load rewards', 'Close', {
          duration: 3000
        });
        this.isRewardsLoading = false;
      });
  }

  redeemReward(rewardId: number): void {
    if (!this.customerId) return;
    
    const token = localStorage.getItem('token');
    this.isRewardsLoading = true;

    fetch(`http://localhost:8080/api/rewards/redeem`, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
        'Authorization': `Bearer ${token}`
      },
      body: JSON.stringify({
        userId: this.customerId,
        rewardId: rewardId
      })
    })
      .then(response => {
        if (!response.ok) {
          throw new Error('Failed to redeem reward');
        }
        return response.json();
      })
      .then(() => {
        this.snackBar.open('Reward redeemed successfully!', 'Close', {
          duration: 3000
        });
        this.fetchCustomerDetails();
      })
      .catch(error => {
        console.error('Error:', error);
        this.snackBar.open('Failed to redeem reward', 'Close', {
          duration: 3000
        });
        this.isRewardsLoading = false;
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
      })
      .catch(error => {
        console.error('Delete user error:', error.message);
        alert('Error deleting user. See console for details.');
      });
  }
}