import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CreateRewardsComponent } from './create-rewards.component';

describe('CreateRewardsComponent', () => {
  let component: CreateRewardsComponent;
  let fixture: ComponentFixture<CreateRewardsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CreateRewardsComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CreateRewardsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
