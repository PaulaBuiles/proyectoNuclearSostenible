import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CircularEconomyComponent } from './circular-economy.component';

describe('CircularEconomyComponent', () => {
  let component: CircularEconomyComponent;
  let fixture: ComponentFixture<CircularEconomyComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [CircularEconomyComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(CircularEconomyComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
