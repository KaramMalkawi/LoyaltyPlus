import { AddRewardsComponent } from './add-rewards/add-rewards.component';
import { Routes } from '@angular/router';
import { LoginComponent } from './login/login.component';
import { SignupComponent } from './signup/signup.component';
import { ManagerDashboardComponent } from './manager-dashboard/manager-dashboard.component';
import { EditCustomerInfoComponent } from './edit-customer-info/edit-customer-info.component';
import { ViewCustomerDetailsComponent } from './view-customer-details/view-customer-details.component';
import { AddCustomerComponent } from './add-customer/add-customer.component'

export const routes: Routes = [
  { path: '', component: LoginComponent },
  { path: 'signup', component: SignupComponent },
  { path: 'manager-dashboard', component: ManagerDashboardComponent },
  { path: 'add-customer', component: AddCustomerComponent },

  { path: 'edit-customer/:id', component: EditCustomerInfoComponent },
  { path: 'view-customer/:id', component: ViewCustomerDetailsComponent },
  { path: 'add-rewards/:id', component: AddRewardsComponent },
];
