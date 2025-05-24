import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EditCustomerInfoComponent } from './edit-customer-info.component';

describe('EditCustomerInfoComponent', () => {
  let component: EditCustomerInfoComponent;
  let fixture: ComponentFixture<EditCustomerInfoComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [EditCustomerInfoComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(EditCustomerInfoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
