import { AddRewardsComponent } from './add-rewards/add-rewards.component';
import { Routes } from '@angular/router';
import { LoginComponent } from './login/login.component';
import { SignupComponent } from './signup/signup.component';
import { ManagerDashboardComponent } from './manager-dashboard/manager-dashboard.component';
import { EditCustomerInfoComponent } from './edit-customer-info/edit-customer-info.component';
import { ViewCustomerDetailsComponent } from './view-customer-details/view-customer-details.component';
import { AddCustomerComponent } from './add-customer/add-customer.component'
import { CreateRewardsComponent } from './create-rewards/create-rewards.component';
import { MarketingAnalystDashboardComponent } from './marketing-analyst-dashboard/marketing-analyst-dashboard.component';
import { RewardsComponent } from './rewards/rewards.component';
import { RewardsHistoryComponent } from './rewards-history/rewards-history.component';

export const routes: Routes = [
  { path: '', component: LoginComponent },
  { path: 'signup', component: SignupComponent },
  { path: 'manager-dashboard', component: ManagerDashboardComponent },
  { path: 'add-customer', component: AddCustomerComponent },

  { path: 'edit-customer/:id', component: EditCustomerInfoComponent },
  { path: 'view-customer/:id', component: ViewCustomerDetailsComponent },
  { path: 'add-rewards/:id', component: AddRewardsComponent },
  { path: 'create-reward', component: CreateRewardsComponent },

  { path: 'marketing-analyst-dashboard', component: MarketingAnalystDashboardComponent },

  { path: 'rewards', component: RewardsComponent },
  { path: 'rewards-history', component: RewardsHistoryComponent }
];
