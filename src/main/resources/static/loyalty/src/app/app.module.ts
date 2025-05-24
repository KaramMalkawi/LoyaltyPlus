import { Component } from '@angular/core';
import { CommonModule } from '@angular/common'; // Needed for common directives like ngIf, ngFor
import { RouterOutlet } from '@angular/router'; // Import RouterOutlet directly

@Component({
  selector: 'app-root',
  standalone: true, // Set to true
  imports: [
    CommonModule,
    RouterOutlet // Import RouterOutlet here
  ],
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'loyalty';
}