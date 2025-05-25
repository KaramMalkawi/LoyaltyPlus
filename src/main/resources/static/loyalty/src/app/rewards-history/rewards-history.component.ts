import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpClient } from '@angular/common/http';
import { MatTableDataSource } from '@angular/material/table';
import { CommonModule, DatePipe } from '@angular/common';
import { MatTableModule } from '@angular/material/table';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import * as XLSX from 'xlsx';
import { MarketingAnalystNavbarComponent } from "../marketing-analyst-navbar/marketing-analyst-navbar.component";

interface RewardRedemption {
  id: number;
  userId: number;
  rewardId: number;
  createdAt: string;
  updatedAt?: string;
}

@Component({
  selector: 'app-rewards-history',
  standalone: true,
  imports: [
    CommonModule,
    MatTableModule,
    MatButtonModule,
    MatCardModule,
    MatProgressSpinnerModule,
    DatePipe,
],
  templateUrl: './rewards-history.component.html',
  styleUrls: ['./rewards-history.component.scss']
})
export class RewardsHistoryComponent implements OnInit {
  userId!: number;
  redemptions: RewardRedemption[] = [];
  dataSource = new MatTableDataSource<RewardRedemption>();
  isLoading = true;
  error: string | null = null;
  displayedColumns: string[] = ['rewardId', 'createdAt', 'updatedAt'];

  constructor(private route: ActivatedRoute, private http: HttpClient) {}

  ngOnInit(): void {
    this.userId = Number(this.route.snapshot.paramMap.get('userId'));
    this.fetchRewardHistory();
  }

  fetchRewardHistory(): void {
    this.isLoading = true;
    this.error = null;

    this.http.get<RewardRedemption[]>(`http://localhost:8080/reward-redemptions/user/${this.userId}`)
      .subscribe({
        next: (data) => {
          this.redemptions = data;
          this.dataSource = new MatTableDataSource(data);
          this.isLoading = false;
        },
        error: (err) => {
          console.error('Error fetching reward history:', err);
          this.error = 'Failed to load reward history. Please try again later.';
          this.isLoading = false;
        }
      });
  }

  exportToExcel(): void {
    const exportData = this.redemptions.map(item => ({
      RewardID: item.rewardId,
      RedeemedOn: item.createdAt,
      LastUpdated: item.updatedAt
    }));
    const worksheet = XLSX.utils.json_to_sheet(exportData);
    const workbook = XLSX.utils.book_new();
    XLSX.utils.book_append_sheet(workbook, worksheet, 'RewardHistory');
    XLSX.writeFile(workbook, `reward_history_user_${this.userId}.xlsx`);
  }
}
