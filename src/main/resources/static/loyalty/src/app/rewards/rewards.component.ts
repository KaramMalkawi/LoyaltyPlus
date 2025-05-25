import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { MatTableDataSource } from '@angular/material/table';
import { CommonModule, DatePipe } from '@angular/common';
import { MatTableModule } from '@angular/material/table';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatCardModule } from '@angular/material/card';
import { MatTooltipModule } from '@angular/material/tooltip';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import * as XLSX from 'xlsx';
import { MarketingAnalystNavbarComponent } from "../marketing-analyst-navbar/marketing-analyst-navbar.component";

interface Reward {
  id: number;
  title: string;
  description: string;
  pointsRequired: number;
  isActive: boolean;
  createdAt: string;
  updatedAt?: string;
}

@Component({
  selector: 'app-rewards',
  standalone: true,
  imports: [
    CommonModule,
    MatTableModule,
    MatButtonModule,
    MatIconModule,
    MatCardModule,
    MatTooltipModule,
    MatProgressSpinnerModule,
    DatePipe,
    MarketingAnalystNavbarComponent
  ],
  templateUrl: './rewards.component.html',
  styleUrls: ['./rewards.component.scss'],
})
export class RewardsComponent implements OnInit {
  dataSource = new MatTableDataSource<Reward>();
  displayedColumns: string[] = [
    'title',
    'description',
    'points',
    'status',
    'created',
  ];
  isLoading = true;
  error: string | null = null;

  constructor(private http: HttpClient) {}

  ngOnInit(): void {
    this.fetchAllRewards();
  }

  fetchAllRewards(): void {
    this.isLoading = true;
    this.error = null;

    this.http.get<Reward[]>('http://localhost:8080/rewards/all').subscribe({
      next: (rewards) => {
        this.dataSource.data = rewards;
        this.isLoading = false;
      },
      error: (err) => {
        console.error('Error fetching rewards:', err);
        this.error = 'Failed to load rewards. Please try again later.';
        this.isLoading = false;
      },
    });
  }

  getStatusBadge(isActive: boolean): string {
    return isActive ? 'Active' : 'Inactive';
  }

  getStatusClass(isActive: boolean): string {
    return isActive ? 'active-badge' : 'inactive-badge';
  }

  truncate(text: string, limit: number): string {
    if (!text) return '';
    if (text.length <= limit) return text;
    return text.substring(0, limit) + '...';
  }

  exportToExcel(): void {
    const exportData = this.dataSource.data.map(reward => ({
      Title: reward.title,
      Description: reward.description,
      PointsRequired: reward.pointsRequired,
      Status: reward.isActive ? 'Active' : 'Inactive',
      CreatedAt: reward.createdAt,
      UpdatedAt: reward.updatedAt || ''
    }));
    const worksheet = XLSX.utils.json_to_sheet(exportData);
    const workbook = XLSX.utils.book_new();
    XLSX.utils.book_append_sheet(workbook, worksheet, 'Rewards');
    XLSX.writeFile(workbook, 'rewards.xlsx');
  }
}
