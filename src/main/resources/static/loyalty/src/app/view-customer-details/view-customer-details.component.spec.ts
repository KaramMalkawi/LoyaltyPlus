import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ViewCustomerDetailsComponent } from './view-customer-details.component';

describe('ViewCustomerDetailsComponent', () => {
  let component: ViewCustomerDetailsComponent;
  let fixture: ComponentFixture<ViewCustomerDetailsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ViewCustomerDetailsComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ViewCustomerDetailsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
