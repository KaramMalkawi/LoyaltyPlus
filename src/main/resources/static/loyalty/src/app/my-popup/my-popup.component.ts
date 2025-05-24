import { Component, EventEmitter, Input, Output } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-my-popup',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './my-popup.component.html',
  styleUrls: ['./my-popup.component.scss'],
})
export class MyPopupComponent {
  @Input() points: number = 0;
  @Input() description: string = '';
  @Output() confirmAdd = new EventEmitter<{ points: number; description: string }>();
  @Output() cancelAdd = new EventEmitter<void>();

  confirm(): void {
    this.confirmAdd.emit({ points: this.points, description: this.description });
  }

  cancel(): void {
    this.cancelAdd.emit();
  }
}
