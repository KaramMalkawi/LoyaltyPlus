import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MarketingAnalystNavbarComponent } from './marketing-analyst-navbar.component';

describe('MarketingAnalystNavbarComponent', () => {
  let component: MarketingAnalystNavbarComponent;
  let fixture: ComponentFixture<MarketingAnalystNavbarComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [MarketingAnalystNavbarComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(MarketingAnalystNavbarComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
