import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AddRewardsComponent } from './add-rewards.component';

describe('AddRewardsComponent', () => {
  let component: AddRewardsComponent;
  let fixture: ComponentFixture<AddRewardsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AddRewardsComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AddRewardsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
