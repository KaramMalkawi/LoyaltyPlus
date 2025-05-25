import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MarketingAnalystDashboardComponent } from './marketing-analyst-dashboard.component';

describe('MarketingAnalystDashboardComponent', () => {
  let component: MarketingAnalystDashboardComponent;
  let fixture: ComponentFixture<MarketingAnalystDashboardComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [MarketingAnalystDashboardComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(MarketingAnalystDashboardComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
