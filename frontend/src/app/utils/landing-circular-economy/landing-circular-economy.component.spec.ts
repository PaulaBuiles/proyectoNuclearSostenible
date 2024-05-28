import { ComponentFixture, TestBed } from '@angular/core/testing';

import { LandingCircularEconomyComponent } from './landing-circular-economy.component';

describe('LandingCircularEconomyComponent', () => {
  let component: LandingCircularEconomyComponent;
  let fixture: ComponentFixture<LandingCircularEconomyComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [LandingCircularEconomyComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(LandingCircularEconomyComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
