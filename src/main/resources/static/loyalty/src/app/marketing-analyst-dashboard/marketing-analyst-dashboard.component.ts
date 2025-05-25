import { Component, OnInit, ViewChild, AfterViewInit } from '@angular/core';
import { Router } from '@angular/router';
import { MatTableDataSource } from '@angular/material/table';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import * as XLSX from 'xlsx';
import { DatePipe, CommonModule } from '@angular/common';
import { MatTableModule } from '@angular/material/table';
import { MatSortModule } from '@angular/material/sort';
import { MatPaginatorModule } from '@angular/material/paginator';
import { MatButtonModule } from '@angular/material/button';
import { FormsModule } from '@angular/forms';

import { MarketingAnalystNavbarComponent } from '../marketing-analyst-navbar/marketing-analyst-navbar.component';


interface User {
  id: number;
  firstName: string;
  lastName: string;
  email: string;
  phone: string;
  currentPoints: number;
  createdAt: Date;
}

@Component({
  selector: 'app-marketing-analyst-dashboard',
  imports: [
    CommonModule,
    FormsModule,
    MatTableModule,
    MatSortModule,
    MatPaginatorModule,
    MatButtonModule,
    DatePipe,
    MarketingAnalystNavbarComponent
  ],
  templateUrl: './marketing-analyst-dashboard.component.html',
  styleUrl: './marketing-analyst-dashboard.component.scss'
})
export class MarketingAnalystDashboardComponent implements OnInit, AfterViewInit {
  dataSource = new MatTableDataSource<User>();
  displayedColumns: string[] = ['addPoints', 'name', 'email', 'phone', 'currentPoints', 'createdAt', 'rewards'];
  token: string | null = null;

  @ViewChild(MatPaginator) paginator!: MatPaginator;
  @ViewChild(MatSort) sort!: MatSort;

  showPopup: boolean = false;
  selectedUserId: number | null = null;
  points: number = 0;

  constructor(private router: Router) {
    this.token = localStorage.getItem('token');
    if (!this.token) {
      this.redirectToLogin();
    }
  }

  redirectToLogin(): void {
    this.router.navigate(['/']);
  }

  ngOnInit(): void {
    this.fetchUsers();
  }

  ngAfterViewInit(): void {
    if (this.paginator) {
      this.dataSource.paginator = this.paginator;
    }
    if (this.sort) {
      this.dataSource.sort = this.sort;
    }
  }

  fetchUsers(): void {
    fetch('http://localhost:8080/api/users/shoppers', {
      headers: {
        'Content-Type': 'application/json',
        Authorization: `Bearer ${this.token}`,
      },
    })
      .then((res) => res.json())
      .then((users: User[]) => {
        const processedUsers = users.map(user => ({
          ...user,
          createdAt: new Date(user.createdAt)
        }));
        this.dataSource = new MatTableDataSource(processedUsers);
        if (this.paginator) {
          this.dataSource.paginator = this.paginator;
        }
        if (this.sort) {
          this.dataSource.sort = this.sort;
        }
      })
      .catch((err) => {
        console.error('Error fetching users:', err);
        alert('Error fetching users.');
      });
  }

  navigateToAddCustomer(): void {
    this.router.navigate(['/add-customer']);
  }

  addPoints(userId: number): void {
    this.selectedUserId = userId;
    this.showPopup = true;
  }

  closePopup(): void {
    this.showPopup = false;
    this.points = 0;
    this.selectedUserId = null;
  }

  confirmAddPoints(event: { points: number; description: string }): void {
    const { points, description } = event;
    if (this.selectedUserId && points > 0) {
      const user = this.dataSource.data.find(u => u.id === this.selectedUserId);
      if (!user) return;

      const totalPoints = user.currentPoints + points;

      fetch(`http://localhost:8080/api/users/update/points/${this.selectedUserId}`, {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
          Authorization: `Bearer ${this.token}`,
        },
        body: JSON.stringify({ currentPoints: totalPoints }),
      })
        .then(res => {
          if (!res.ok) throw new Error('User point update failed');
          return res.text();
        })
        .then(() => {
          return fetch('http://localhost:8080/api/point-transactions/create', {
            method: 'POST',
            headers: {
              'Content-Type': 'application/json',
              Authorization: `Bearer ${this.token}`,
            },
            body: JSON.stringify({
              userId: this.selectedUserId,
              pointsChanged: points,
              description: description,
              createdAt: new Date().toISOString(),
            }),
          });
        })
        .then(res => {
          if (!res.ok) throw new Error('Transaction logging failed');
          return res.text();
        })
        .then(() => {
          this.fetchUsers();
          this.closePopup();
        })
        .catch((err) => {
          console.error(err);
          alert('Error while adding points or logging transaction.');
        });
    }
  }

  navigateToRewards(userId: number): void {
    this.router.navigate(['/rewards', userId]);
  }
  

  exportToExcel(): void {
    const worksheet = XLSX.utils.json_to_sheet(this.dataSource.data);
    const workbook = XLSX.utils.book_new();
    XLSX.utils.book_append_sheet(workbook, worksheet, 'Customers');
    XLSX.writeFile(workbook, 'customers.xlsx');
  }
}
